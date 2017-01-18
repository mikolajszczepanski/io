package app.controllers;
import static spark.Spark.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import app.helpers.Constants;
import app.models.Category;
import app.models.DatabaseContext;
import app.models.FrequencyType;
import app.models.Pair;
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
			int CurrentMonth = mycal.get(Calendar.MONTH);
			int CurrentYear =mycal.get(Calendar.YEAR);
			int month=CurrentMonth+1;
			attributes.put("title", Constants.monthPl[CurrentMonth] + " " + CurrentYear);
			if(month==2){
				if(CurrentYear%4==0) attributes.put("timescale", Constants.days+",'29'");
				else attributes.put("timescale", Constants.days);
			}
			else if (month==4 || month==6|| month==9|| month==11)
				attributes.put("timescale", Constants.days+",'29','30'");
			else attributes.put("timescale", Constants.days+",'29','30','31'"); 
			String transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,CurrentMonth+1,TransactionType.SPENDING);
			attributes.put("SPENDING",transactionsString);
			transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,CurrentMonth+1,TransactionType.REVENUE);
			attributes.put("REVENUE",transactionsString);
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
			if(month.equals("Year")){
				attributes.put("title", "Zestawienie dla całego roku" + " " +  mycal.get(Calendar.YEAR));
				String transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,0,TransactionType.SPENDING);
				attributes.put("SPENDING",transactionsString);
				transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,0,TransactionType.REVENUE);
				attributes.put("REVENUE",transactionsString);
			}
			else{
				int digitMonth = Integer.parseInt(month);
				attributes.put("title",Constants.monthPl[digitMonth-1] + " " +  mycal.get(Calendar.YEAR));
				if(month.equals("2")){
					if( mycal.get(Calendar.YEAR)%4==0) attributes.put("timescale", Constants.days+",'29'");
					else attributes.put("timescale", Constants.days);
				}
				else if (digitMonth==4 || digitMonth==6|| digitMonth==9|| digitMonth==11)
					attributes.put("timescale", Constants.days+",'29','30'");
				else attributes.put("timescale", Constants.days+",'29','30','31'"); 
				

				double spendings = transactionsDao.getAmountByUserAndType(user,TransactionType.SPENDING );
				
				String transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,digitMonth,TransactionType.SPENDING);
				attributes.put("SPENDING",transactionsString);
				transactionsString = transactionsDao.getTransactionValueByUserAndMonthAndType(user,digitMonth,TransactionType.REVENUE);
				attributes.put("REVENUE",transactionsString);
			}
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
	 * żądanie get dla /app/categories/add
	 * dodawanie transakcji przez użytkownika
	 */
	public void getAddCategory(){
		get("/app/categories/add", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			String transactionTypeStr = request.queryParams("transactionType");
			System.out.println(transactionTypeStr);
			int type = TransactionType.REVENUE.getValue();
			try{
				if(TransactionType.SPENDING.getValue() == Integer.parseInt(transactionTypeStr)){
					type = TransactionType.SPENDING.getValue();
				}
			}catch(Exception ex){}
			
			attributes.put("user", user);
			String return_url = request.queryParams("return_url");
			if(return_url != null){
				attributes.put("return_url", return_url);
			}
			List<Pair<String,Integer>> typeList = new ArrayList<>();
			typeList.add(new Pair<String,Integer>("Wydatki",TransactionType.SPENDING.getValue()));
			typeList.add(new Pair<String,Integer>("Przychody",TransactionType.REVENUE.getValue()));
			attributes.put("types", typeList);	
			attributes.put("activeCategory", type);
            return new ModelAndView(attributes, "managment/addcategory.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/categories/add
	 * dodawanie wprowadzonej przez użytkownika  transakcji do bazy
	 */
	public void postAddCategory(){
		post("/app/categories/add", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			
			DatabaseContext databaseContext = new DatabaseContext();
			
			String categoryName = request.queryParams("categoryName");
			String categoryType = request.queryParams("categoryType");
			String categoryColor = request.queryParams("categoryColor");
			String returnUrl = request.queryParams("return_url");
			String transactionTypeStr = request.queryParams("transactionType");
			System.out.println(transactionTypeStr);
			
			
			System.out.println("categoryName=" + categoryName);
			
			boolean correctData = false;
			int type = Integer.parseInt(categoryType);
			
			List<String> errors = Category.validate(categoryName, type, categoryColor);
			for (String iterable_element : errors) {
				System.out.println(iterable_element);
			}
			if(errors.size() == 0){
				CategoryDao categoryDao = new CategoryDaoImpl(databaseContext);
				Category category = new Category(-1, categoryName, user, type, categoryColor);
				correctData = categoryDao.addCategory(category);
			}
			
			if(correctData){
				response.redirect(returnUrl + "?category=" + categoryName);
			}
			attributes.put("errors",errors);
			List<Pair<String,Integer>> typeList = new ArrayList<>();
			typeList.add(new Pair<String,Integer>("Wydatki",TransactionType.SPENDING.getValue()));
			typeList.add(new Pair<String,Integer>("Przychody",TransactionType.REVENUE.getValue()));
			attributes.put("types", typeList);
			attributes.put("return_url",returnUrl);
			attributes.put("activeCategory", type);
			return new ModelAndView(attributes, "managment/addcategory.ftl");
        }, new FreeMarkerEngine());
		
	}
	
	/**
	 * żądanie get dla /app/transaction/add/:type
	 * przekierowuje do odpowiedniej strony z dodawaniem transakcji
	 */
	public void getAddTransaction(){
		get("/app/transaction/add/:type", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			String return_url = request.queryParams("return_url");
			String type = request.params("type");
			attributes.put("transaction_type", type);
			String categoryStr = request.queryParams("category");
			if(return_url != null){
				attributes.put("return_url", return_url);
			}
			Category category = null;
			if(categoryStr != null){
				category = (new CategoryDaoImpl()).getCategoryByName(categoryStr,user);
			}
			
			CategoryDao categoryDao = new CategoryDaoImpl();
			List<Category> categories = null;
			List<String> errors = new ArrayList<>();
			
			if(type.equals(Constants.SPENDING)){
				categories = categoryDao.getAllByUserAndType(user,TransactionType.SPENDING);
				attributes.put("transaction_title", "Wydatek");
				attributes.put("transaction_type_name", Constants.SPENDING);
				attributes.put("transaction_type_id", TransactionType.SPENDING.getValue());
			}
			else if(type.equals(Constants.REVENUE)){
				categories = categoryDao.getAllByUserAndType(user,TransactionType.REVENUE);
				attributes.put("transaction_title", "Przychód");
				attributes.put("transaction_type_name", Constants.REVENUE);
				attributes.put("transaction_type_id", TransactionType.REVENUE.getValue());
			}
			else{
				errors.add("Nie właściwy typ.");
				categories = new ArrayList<>();
			}
			if(errors.size() > 0){
				attributes.put("errors", errors);
			}
			attributes.put("activeCategory", category);
			attributes.put("categories", categories);
            return new ModelAndView(attributes, "managment/addtransaction.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * Żądanie get dla /app/settings
	 */
	public void getSettings(){
		get("/app/settings", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			String monthlyLimit = "";
			if(user != null){
				monthlyLimit = user.getMonthlyLimitAsString() != null ? user.getMonthlyLimitAsString() : "0.0";
			}
			attributes.put("monthlyLimit", monthlyLimit);
            return new ModelAndView(attributes, "managment/settings.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/transaction/add/:type
	 * sprawdzenie parametrów transakcji oraz dodanie transakcji
	 */
	public void postAddTransaction(){
		post("/app/transaction/add/:type", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			
			DatabaseContext databaseContext = new DatabaseContext();
			String type = request.params("type");
			
			String transactionAmountAsString = request.queryParams("transactionAmount");
			String transactionCategoryIdAsString = request.queryParams("transactionCategoryId");
			String transactionFrequencyTypeAsString = request.queryParams("transactionFrequencyType");
			String transactionTypeAsString = request.queryParams("transactionType");
			String transactionDateAsString = request.queryParams("transactionDate");
			
			List<String> errors = new ArrayList<>();
			TransactionType transactionType = null;
			try{
				int tempTransationType = Integer.parseInt(transactionTypeAsString);
				if(tempTransationType == TransactionType.REVENUE.getValue()){
					transactionType = TransactionType.REVENUE;
					attributes.put("transaction_title", "Przychód");
					attributes.put("transaction_type_name", Constants.REVENUE);					
				}
				else if(tempTransationType == TransactionType.SPENDING.getValue()){
					transactionType = TransactionType.SPENDING;
					attributes.put("transaction_title", "Wydatek");
					attributes.put("transaction_type_name", Constants.SPENDING);
				}
				else{
					throw new Exception("Nieprawidłowy typ transakcji.");
				}
			}catch(NumberFormatException ex){
				transactionType = TransactionType.REVENUE;
			}
			try{
				if(transactionCategoryIdAsString == null || transactionCategoryIdAsString.length() == 0){
					throw new NumberFormatException("Podano nieprawidłową kategorię.");
				}
				double transactionAmount = Double.parseDouble(transactionAmountAsString);
				int transactionCategoryId = Integer.parseInt(transactionCategoryIdAsString);
				int tempFrequencyType = Integer.parseInt(transactionFrequencyTypeAsString); 

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date transactionDate = df.parse(transactionDateAsString);				
				
				if(transactionAmount <= 0){
					throw new Exception("Ilość nie może być ujemna bądź równa zero.");
				}
				
				FrequencyType transactionFrequencyType = null;
				
				if(tempFrequencyType == FrequencyType.ONCE.getValue()){
					transactionFrequencyType = FrequencyType.ONCE;
				}
				else if(tempFrequencyType == FrequencyType.WEEKLY.getValue()){
					transactionFrequencyType = FrequencyType.WEEKLY;
				}
				else if(tempFrequencyType == FrequencyType.MONTHLY.getValue()){
					transactionFrequencyType = FrequencyType.MONTHLY;
				}
				else{
					throw new Exception("Nieprawidłowy typ częstości.");
				}
				
				
				
				CategoryDao categoryDao = new CategoryDaoImpl(databaseContext);
				Category category = categoryDao.getCategory(transactionCategoryId);
				
				Transaction transaction = new Transaction(
						-1, 
						user, 
						category, 
						transactionAmount, 
						transactionFrequencyType, 
						transactionType, 
						transactionDate);
				
				TransactionDao transactionDao = new TransactionDaoImpl(databaseContext);
				if(transactionDao.addTransaction(transaction)){
					response.redirect("/app/balancesheet");
				}
				else{
					throw new Exception("Nie udało się dodać transakcji.");
				}
				
			}catch(Exception ex){
				errors.add(ex.getMessage());
			}
			CategoryDao categoryDao = new CategoryDaoImpl(databaseContext);
			List<Category> categories = categoryDao.getAllByUserAndType(user,transactionType);
			
			attributes.put("errors", errors);
			attributes.put("categories", categories);
			attributes.put("transaction_type_id", transactionType.getValue());
			attributes.put("transaction_type", type);
			
            return new ModelAndView(attributes, "managment/addtransaction.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie get dla /app/chart
	 * rysowanie diagramu (domyślnie dla wydatków)
	 */
	public void getChart() {
		get("/app/chart", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			attributes.put("SPENDING", "Wydatki");
			attributes.put("other", "Przychody");
			CategoryDao categoriesDao = new CategoryDaoImpl();
			attributes.put("SPENDING", "Wydatki");
			String chartString = categoriesDao.getChartStringByUserAndCategory(user,TransactionType.SPENDING);
			if(chartString!=null) attributes.put("chartdata", chartString);
			return new ModelAndView(attributes, "managment/chart.ftl");
		}, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie post dla /app/chart
	 * rysowanie diagramów dla przychodów i dochodów
	 */
	public void postChart() {
		post("/app/chart", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			CategoryDao categoriesDao = new CategoryDaoImpl();
			if(request.queryParams("SPENDING")!=null){
				attributes.put("SPENDING", "Wydatki");
				attributes.put("other", "Przychody");
				String chartString = categoriesDao.getChartStringByUserAndCategory(user,TransactionType.SPENDING);
				if(chartString!=null)attributes.put("chartdata", chartString);
			}
			else{
				attributes.put("other", "Wydatki");
				attributes.put("REVENUE", "Przychody");
				String chartString = categoriesDao.getChartStringByUserAndCategory(user,TransactionType.REVENUE);
				if(chartString!=null)attributes.put("chartdata", chartString);
			}
			return new ModelAndView(attributes, "managment/chart.ftl");
		}, new FreeMarkerEngine());
	}
	
	/**
	 * Żądanie post dla /app/settings
	 */
	public void postSettings(){
		post("/app/settings", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			User user = request.session().attribute("user");
			attributes.put("user", user);
			
			String monthlyLimitStr = request.queryParams("monthlyLimit");
			String isMonthyLimitActiveStr = request.queryParams("isMonthlyLimitActive");
			boolean isMonthyLimitActive = isMonthyLimitActiveStr != null ? true : false;
			try{
				if(isMonthyLimitActive){
					double monthlyLimit = Double.parseDouble(monthlyLimitStr);
					if(monthlyLimit <= 0){
						throw new Exception("Limit miesięczny nie może być mniejszy niż zero.");
					}
					user.setMonthlyLimit(monthlyLimit);
				}
				else{
					user.setMonthlyLimit(0);
				}
				UserDao userDao = new UserDaoImpl();
				if(!userDao.updateUser(user)){throw new Exception("SQL Error");}
			}
			catch(NumberFormatException | NullPointerException ex){
				List<String> errors = new ArrayList<String>();
				errors.add("Podano nieprawidłową wartość");
				attributes.put("errors", errors);
			}
			catch(Exception ex){
				List<String> errors = new ArrayList<String>();
				errors.add(ex.getMessage());
				attributes.put("errors", errors);
			}
			attributes.put("monthlyLimit", user.getMonthlyLimitAsString() != null ? user.getMonthlyLimitAsString() : "0.0");
			return new ModelAndView(attributes, "managment/settings.ftl");
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
		postAddCategory();
		getAddCategory();
		getSettings();
		postSettings();
		getAddTransaction();
		postAddTransaction();
		getChart();
		postChart();
	}
}