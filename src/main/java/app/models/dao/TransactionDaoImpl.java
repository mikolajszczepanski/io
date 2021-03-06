package app.models.dao;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Calendar;
import java.util.GregorianCalendar;
import app.models.Category;
import app.models.DatabaseContext;
import app.models.FrequencyType;
import app.models.Transaction;
import app.models.TransactionType;
import app.models.User;

/**
 * Klasa odpowiedzialna za obsługe obiektów Transaction przez zapytania SQL bazy danych
 * @see Transaction
 */
public class TransactionDaoImpl extends Dao implements TransactionDao {

	
	/**
	 * Konstruktor bezargumentowy.
	 */
	public TransactionDaoImpl(){
		super();
	}
	
	/**
	 * Konstruktor przyjmujący DatebaseContext
	 * @param databaseContext Obiekt zawierający połączenie z bazą danych
	 */
	public TransactionDaoImpl(DatabaseContext databaseContext) {
		super(databaseContext);
	}
	/**
	 * Metoda zwracająca wszystkie transakcje danego użytkownika z bazy danych
	 * @param user Użytkownik
	 * @return Lista transakcji użytkownika 
	 */
	@Override
	public List<Transaction> getAllByUser(User user) {
		if(user == null){
			return null;
		}
		int userId = user.getId();
		return convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM `transactions` "
				+ "WHERE `user_id` = " + userId + " "
				+ "ORDER BY date DESC"));
	}
	/**
	 * Metoda zwracająca wszystkie transakcje danego użytkownika i według określonego typu z bazy danych
	 * @param user Użytkownik
	 * @param type Typ transackji (jednorazowa,cotygodniowa,miesięczna)
	 * @return Lista transakcji
	 */
	@Override
	public List<Transaction> getTransactionByType(User user,TransactionType type ) {
		if(user == null){
			return null;
		}
		int userId = user.getId();
		String sql = "SELECT * FROM `transactions` "
				+ "WHERE `user_id` = " + userId + " "
				+ "AND `transaction_type` = " + type.getValue() + " "
				+ "ORDER BY date DESC";
		List<Transaction> array = convertResultToArray(executeSelectSqlQuery(sql));
		return array;
	}
	/**
	 * Metoda usuwajaca okreslona transakcje z bazy danych
	 * @param user Użytkownik do ktorego transakcja jest przypisana
	 * @param transactionId Id transakcji która ma zostać usunięta
	 */
	@Override
	public void removeTransactionByTransactionIdAndUserId(User user,int transactionId){
		if(user == null){
			return;
		}
		int userId = user.getId();
		String sql = "DELETE FROM `transactions` WHERE `transactions`.`id` = " 
				+ transactionId	
				+ " AND `user_id` = " + userId;
		executeSelectSqlQuery(sql);
	}
	/**
	 * Metoda dodająca nową transakcje do bazy danych
	 * @param transaction Obiekt do dodania typu Transaction
	 * @return Wartość logiczna operacji
	 */
	@Override
	public boolean addTransaction(Transaction transaction){
		String sql = "INSERT INTO `transactions` ("
				+ "`id`, "
				+ "`user_id`, "
				+ "`category_id`, "
				+ "`amount`, "
				+ "`transaction_type`, "
				+ "`frequency_type`,"
				+ " `date`) "
				+ "VALUES ("
				+ "NULL, "
				+ "'" + transaction.getUser().getId() + "', "
				+ "'" + transaction.getCategory().getId() + "', "
				+ "'" + transaction.getAmount() + "', "
				+ "'" + transaction.getTransactionType().getValue() + "', "
				+ "'" + transaction.getFrequencyType().getValue() + "', "
				+ "'" + transaction.getStringDate() + "');";
		return executeUpdateSqlQuery(sql);
	}
		
	private List<Transaction> convertResultToArray(Result result){
		List<Transaction> array = new ArrayList<>();
		ResultSet resultSet = result.getResultSet();
		try {
			while(resultSet.next()){
				String strDate = resultSet.getString("date");
				Date date = new SimpleDateFormat(Transaction.DATE_FORMAT,Locale.ENGLISH).parse(strDate);
				UserDao userDao = new UserDaoImpl(databaseContext);
				User user = userDao.getUser(resultSet.getInt("user_id"));
				CategoryDao categoryDao = new CategoryDaoImpl(databaseContext);
				Category category = categoryDao.getCategory(resultSet.getInt("category_id"));
				FrequencyType frequencyType = null;
				if(resultSet.getInt("frequency_type") == FrequencyType.ONCE.getValue()){
					frequencyType = FrequencyType.ONCE;
				}
				else if(resultSet.getInt("frequency_type") == FrequencyType.WEEKLY.getValue()){
					frequencyType = FrequencyType.WEEKLY;
				}
				else if(resultSet.getInt("frequency_type") == FrequencyType.MONTHLY.getValue()){
					frequencyType = FrequencyType.MONTHLY;
				}
				else{
					throw new Exception("Unknown FrequencyType");
				}
				array.add( new Transaction(
						resultSet.getInt("id"),
						user,
						category,
						resultSet.getDouble("amount"),
						frequencyType,
						resultSet.getInt("transaction_type") == TransactionType.REVENUE.getValue() ?  TransactionType.REVENUE : TransactionType.SPENDING ,
						date
						)
				);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
			
		return array;
	}
	/**
	 * Metoda zwracająca wszystkie transakcje miesięczne z bazy danych
	 * @return List transakcji miesięcznych
	 */
	@Override
	public List<Transaction> getAllMonthly() {
		String sql = "SELECT * "
				+ "FROM `transactions` "
				+ "WHERE `frequency_type` = " + FrequencyType.MONTHLY.getValue() + " "
				+ "ORDER BY date DESC";
		return convertResultToArray(executeSelectSqlQuery(sql));
	}
	/**
	 * Metoda zwracająca wszystkie transakcje cotygodniowe z bazy danych
	 * @return List transakcji cotygodniowych
	 */
	@Override
	public List<Transaction> getAllWeekly() {
		String sql = "SELECT * "
				+ "FROM `transactions` "
				+ "WHERE `frequency_type` = " + FrequencyType.WEEKLY.getValue() + " "
				+ "ORDER BY date DESC";
		return convertResultToArray(executeSelectSqlQuery(sql));
	}
	/**
	 * Metoda zwrająca sume kwot transakcji określonego typu z bazy danych
	 * @param user Użytkownik 
	 * @param type Typ transakcji (jednorazowa,cotygodniowa,miesięczna)
	 * @return Suma kwot
	 */
	@Override
	public double getAmountByUserAndType(User user, TransactionType type ){
		if(user == null){
			return 0.0;
		}
		int userId = user.getId();
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int month=now.get(Calendar.MONTH)+1;
		int day=now.get(Calendar.DATE);
		
		
		String sql = "SELECT SUM(amount) As SUMPERIOD FROM transactions WHERE `user_id` = " 
				 + userId 
				 + " AND `date` >= \"" 
				 + year 
				 + "-"+ month + "-" 
				 + "01"
				 + " 00:00:00\""
				 + " AND `date` < \"" 
				 + (month==12?(now.get(Calendar.YEAR)+1) : now.get(Calendar.YEAR))  + "-" 
				 + (month==12?1:(month+1))+ "-" 
				 + "01"
				 + " 00:00:00\""
				 + " AND `transaction_type` = " + type.getValue();
		return sumPeriod(executeSelectSqlQuery(sql));
	}
	/**
	 * Metoda pomocnicza zwracająca wykonywalny kod javascriptowy tworzony poprzez zapytania do bazy danych
	 * @param user Użytkownik
	 * @param Month Miesiąc 1-12
	 * @param type Typ transakcji (jednorazowa,cotygodniowa,miesięczna)
	 * @return Kod javascript
	 */
	@Override
	public String  getTransactionValueByUserAndMonthAndType(User user,int Month,TransactionType type ){
		if(user == null){
			return null;
		}
		int userId = user.getId();
		List<Double> arrayValue = new ArrayList<>();
		Calendar now = Calendar.getInstance(); 
		if(Month==0){
			for(int i=1;i<=12;++i){
				double sum=0;
				String sql = "SELECT SUM(amount) As SUMPERIOD FROM transactions WHERE `user_id` = " 
						 + userId 
						 + " AND `date` >= \"" 
						 + now.get(Calendar.YEAR) + "-"
						 + i 
						 + "-01 00:00:00\""
						 + " AND `date` < \"" 
						 + (i==12?now.get(Calendar.YEAR)+1 : now.get(Calendar.YEAR))  
						 + "-"+ ((i+1)==13?1:i+1)
						 + "-01 00:00:00\""
						 + " AND `transaction_type` = " + type.getValue();
				sum = sumPeriod(executeSelectSqlQuery(sql));
				arrayValue.add(sum);
			}
		}
		else{
			Calendar mycal = new GregorianCalendar(now.get(Calendar.YEAR),Month-1,1);
			int days =mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
			for(int i=1;i<=days;++i){
				double sum=0;
				String sql = "SELECT SUM(amount) As SUMPERIOD FROM transactions WHERE `user_id` = " 
						 + userId 
						 + " AND `date` >= \"" 
						 + now.get(Calendar.YEAR)  + "-"
						 + Month + "-" 
						 + i
						 + " 00:00:00\""
						 + " AND `date` < \"" 
						 + (Month==12&&i==31?(now.get(Calendar.YEAR)+1) : now.get(Calendar.YEAR))  + "-" 
						 + (i==days&&Month==12?1:(i==days?Month+1:Month))+ "-" 
						 + ((i+1)==days+1?1:i+1)
						 + " 00:00:00\""
						 + " AND `transaction_type` = " + type.getValue();
				sum = sumPeriod(executeSelectSqlQuery(sql));
				arrayValue.add(sum);
			}
		}
		StringBuilder transactionstring = new StringBuilder(new String("["));
        for (Double transaction :arrayValue) {
        	transactionstring.append(transaction);
			transactionstring.append(',');
		}
        transactionstring.setCharAt(transactionstring.length()-1, ']');
        return transactionstring.toString();
	}
	private double sumPeriod (Result result){
		double sum = 0;
		ResultSet resultSet = result.getResultSet();
		try {
			while(resultSet.next()){
				UserDao userDao = new UserDaoImpl(databaseContext);
				sum = resultSet.getInt("SUMPERIOD");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
			
		return sum;
	}
	
}
