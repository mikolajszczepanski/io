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
		getRevenues();
		postRevenues();
	}
}