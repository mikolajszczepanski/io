package app.controllers;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.models.Transaction;
import app.models.TransactionType;
import app.models.User;
import app.models.dao.CategoryDao;
import app.models.dao.CategoryDaoImpl;
import app.models.dao.TransactionDao;
import app.models.dao.TransactionDaoImpl;
import app.models.dao.UserDao;
import app.models.dao.UserDaoImpl;
import app.models.Category;
import app.models.DatabaseContext;
import app.models.FrequencyType;
import app.models.Constants;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * klasa obsługująca żądzania związanie z działaniem aplikacji
 */
public class ManagmentController extends Controller {
	
	/**
	 * żądzanie get dla /app/index
	 * odpowiada za rysowanie wykresu dla obecnego miesiąca
	 * i wypisywanie informacji na temat limitu miesięcznego
	 */
	public void getIndex() {
		get("/app/index", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			TransactionDao transactionsDao = new TransactionDaoImpl();
			List<String> info = new ArrayList<>();
			Calendar now = Calendar.getInstance(); 
			Calendar mycal = new GregorianCalendar(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE));
			double spendings = transactionsDao.getAmountByUserAndType(user,TransactionType.SPENDING );
			if(user != null){
				if(user.haveMonthyLimit()){
					if(spendings < user.getMonthlyLimit() && (user.getMonthlyLimit()-spendings)<=50 )
						attributes.put("warning","Uwaga! Zostało już tylko " +String.format("%.2f", (user.getMonthlyLimit()-spendings)) + "zł do uzyskania limitu! Oszczędzaj... ");
					else if(spendings == user.getMonthlyLimit()) attributes.put("warning","Uwaga! Osiągnięto limit miesięczny!");
					else if (spendings > user.getMonthlyLimit())
						attributes.put("alert","Przekroczono limit o " + String.format("%.2f", (spendings - user.getMonthlyLimit())) + "zł");
					else if (spendings < user.getMonthlyLimit()){
						info.add("Miesięczny limit: " + user.getMonthlyLimit() + "zł.");
						info.add(String.format("%.2f",spendings) + "zł zostało wydane.");
						info.add(String.format("%.2f", ((double)user.getMonthlyLimit()-spendings))+ "zł pozostało." );
						info.add("Wydawaj średnio "
								+ String.format("%.2f", ((double)user.getMonthlyLimit()-spendings)/(mycal.getActualMaximum(Calendar.DAY_OF_MONTH)-mycal.get(Calendar.DATE)))
								+ " zł dziennie  aby starczyło do końca miesiąca!");
					}
				}
			}
			
			if(info.size()>0) attributes.put("info",info);
			//wykres
            return new ModelAndView(attributes, "managment/index.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/index
	 * odpowiada za rysowanie wykresu dla zadanego okresu
	 * oraz wypisywanie informacji o limicie miesięcznym
	 */
	public void postIndex() {
		post("/app/index", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			TransactionDao transactionsDao = new TransactionDaoImpl();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			String month = request.queryParams("month");
			Calendar now = Calendar.getInstance(); 
			Calendar mycal = new GregorianCalendar(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DATE));
			List<String> info = new ArrayList<>();
			double spendings = transactionsDao.getAmountByUserAndType(user,TransactionType.SPENDING );
			if(user.haveMonthyLimit()){
				if(spendings < user.getMonthlyLimit() && (user.getMonthlyLimit()-spendings)<=50 )
					attributes.put("warning","Uwaga! Zostało już tylko " +String.format("%.2f", (user.getMonthlyLimit()-spendings)) + "zł do uzyskania limitu! Oszczędzaj... ");
				else if(spendings == user.getMonthlyLimit()) attributes.put("warning","Uwaga! Osiągnięto limit miesięczny!");
				else if (spendings > user.getMonthlyLimit())
					attributes.put("alert","Przekroczono limit o " + String.format("%.2f", (spendings - user.getMonthlyLimit())) + "zł");
				else if (spendings < user.getMonthlyLimit()){
					info.add("Miesięczny limit: " + user.getMonthlyLimit() + "zł.");
					info.add(String.format("%.2f",spendings) + "zł zostało wydane.");
					info.add(String.format("%.2f", ((double)user.getMonthlyLimit()-spendings))+ "zł pozostało." );
					info.add("Wydawaj średnio "
							+ String.format("%.2f", ((double)user.getMonthlyLimit()-spendings)/(mycal.getActualMaximum(Calendar.DAY_OF_MONTH)-mycal.get(Calendar.DATE)))
							+ " zł dziennie  aby starczyło do końca miesiąca!");
				}
				if(info.size()>0) attributes.put("info",info);
			}
            return new ModelAndView(attributes, "managment/index.ftl");
        }, new FreeMarkerEngine());
	}
	
	
	/**
	 * żądzanie get dla /app/balancesheet
	 * wypisuje transakcje
	 */
	public void getBalanceSheet(){
		get("/app/balancesheet", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			TransactionDao transactionsDao = new TransactionDaoImpl();
			List<Transaction> transactions = transactionsDao.getAllByUser(user);
			attributes.put("transactions", transactions);
            return new ModelAndView(attributes, "managment/balancesheet.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/balancesheet
	 * odpowiada za  usuwanie transakcji
	 */
	public void postBalanceSheet(){
		post("/app/balancesheet", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			TransactionDao transactionsDao = new TransactionDaoImpl();
			String transactionId = request.queryParams("ID");
			if(transactionId!=null)
				transactionsDao.removeTransactionByTransactionIdAndUserId(user,Integer.parseInt(transactionId));
			return new ModelAndView(attributes, "managment/balancesheet.ftl");
        }, new FreeMarkerEngine());
	}	
	
	/**
	 * Żądanie get dla /app/checkrevenues wypisuje przychody
	 */
	public void getRevenues() {
		get("/app/checkrevenues", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			TransactionDao transactionsDao = new TransactionDaoImpl();
			List<Transaction> transactions = transactionsDao.getTransactionByType(user, TransactionType.REVENUE);
			attributes.put("transactions", transactions);
			return new ModelAndView(attributes, "managment/checkrevenues.ftl");
		}, new FreeMarkerEngine());
	}

	/**
	 * Żądanie post dla /app/checkrevenues odpowiada za usuwanie przychodów
	 */
	public void postRevenues() {
		get("/app/checkrevenues", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			String transactionId = request.queryParams("ID");
			TransactionDao transactionsDao = new TransactionDaoImpl();
			if (transactionId != null)
				transactionsDao.removeTransactionByTransactionIdAndUserId(user, Integer.parseInt(transactionId));
			return new ModelAndView(attributes, "managment/checkrevenues.ftl");
		}, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie get dla /app/checkspendings
	 * wypisuje wydatki
	 */
	public void getSpendings(){
		get("/app/checkspendings", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			
			TransactionDao transactionsDao = new TransactionDaoImpl();
			List<Transaction> transactions = transactionsDao.getTransactionByType(user, TransactionType.SPENDING);
			attributes.put("transactions", transactions);
			
            return new ModelAndView(attributes, "managment/checkspendings.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/checkspendings
	 * odpowiada za usuwanie wydatków
	 */
	public void postSpendings(){
		post("/app/checkspendings", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			String transactionId = request.queryParams("ID");
			TransactionDao transactionsDao = new TransactionDaoImpl();
			if(transactionId!=null)
				transactionsDao.removeTransactionByTransactionIdAndUserId(user,Integer.parseInt(transactionId));
			return new ModelAndView(attributes, "managment/checkspendings.ftl");
        }, new FreeMarkerEngine());
	}

	/**
	 * sprawdzenie czy sesja użytkownika nie wygasła
	 */
	public void authentication() {
		before("/app/*", (request, response) -> {
			User user = request.session().attribute("user");
			if (user == null) {
				response.redirect("/login?error=auth");
			}
		});
	}

	/**
	 * udostępnia żądania dla ścieżek
	 */
	@Override
	public void addRoutes() {
		authentication();
		// Dodawac ponizej nowe scieżki:
		getIndex();
		postIndex();
		getRevenues();
		postRevenues();
		getSpendings();
		postSpendings();
		getBalanceSheet();
		postBalanceSheet();
	}
}