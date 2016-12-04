package app.models.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import app.models.Category;
import app.models.DatabaseContext;
import app.models.TransactionType;
import app.models.User;

/**
 * Klasa odpowiedzialna za obsługe obiektów Category przez zapytania SQL bazy danych
 * @see Category
 */
public class CategoryDaoImpl extends Dao implements CategoryDao {

	/**
	 * Konstruktor bezargumentowy.
	 */
	public CategoryDaoImpl(){
		super();
	}
	
	/**
	 * Konstruktor przyjmujący DatebaseContext
	 * @param databaseContext Obiekt zawierający połączenie z bazą danych
	 */
	public CategoryDaoImpl(DatabaseContext databaseContext) {
		super(databaseContext);
	}
	
	/**
	 * Metoda zwracająca wszystkie kategorie powiązane z danym użytkownikiem z bazy danych 
	 * @param user Użytkownik
	 * @return Lista kategorii
	 */
	@Override
	public List<Category> getAllByUser(User user) {
		if(user == null){
			return null;
		}
		int id = user.getId();
		return convertResultToArray(executeSelectSqlQuery("SELECT * FROM categories WHERE user_id = " + id));
	}
	/**
	 * Metoda zwracająca liste kategorii z bazy danych
	 * @return Lista kategorii
	 */
	@Override
	public List<Category> getAllCategories() {
		return convertResultToArray(executeSelectSqlQuery("SELECT * FROM categories"));
	}
	/**
	 * Metoda zwracająca wszystkie kategorie powiązane z użytkownikiem i określonego typu z bazy danych
	 * @param user Użytkownik
	 * @param type Typ transakcji
	 * @return Lista kategorii
	 */
	@Override
	public List<Category> getAllByUserAndType(User user, TransactionType type) {
		if(user == null || type == null){
			return null;
		}
		int id = user.getId();
		return convertResultToArray(executeSelectSqlQuery("SELECT * FROM categories WHERE user_id = " + id + " AND type = " + type.getValue()));
	}
	/**
	 * Metoda zwracająca kategorie z podanym id z bazy danych
	 * @param id Id kategorii
	 * @return Kategoria
	 */
	@Override
	public Category getCategory(int id) {
		List<Category> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM categories "
				+ "WHERE id = " + id));
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * Metoda pomocnicza zwracająca kod javascript'owy 
	 * @param user Użytkownik
	 * @param type Typ transakcji
	 * @return String reprezentujący wykonywalny kod javascript
	 */
	@Override
	public String getChartStringByUserAndCategory(User user, TransactionType type){
		if(user == null || type == null){
			return null;
		}
		int userId = user.getId();
		String sql = "SELECT * FROM categories WHERE `user_id` = " 
				 + userId
				 + " AND `type` = " + type.getValue();
		List<Category> array = convertResultToArray(executeSelectSqlQuery(sql));
		StringBuilder chartString = new StringBuilder(new String());
		Calendar now = Calendar.getInstance();
		int year=now.get(Calendar.YEAR);
		int month=now.get(Calendar.MONTH)+1;
		for (Category category : array) {
			String sqlSum = "SELECT SUM(amount) As SUMAMOUNT FROM transactions "
					 + "WHERE user_id = " 
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
					 + " AND category_id = "
					 + category.getId();
			double sum = sumOfAmount(executeSelectSqlQuery(sqlSum));
			if (sum==0) continue;
			StringBuilder transactionstring = new StringBuilder(new String("{ value: "));
			transactionstring.append(sum);
			transactionstring.append(", color: \"");
			transactionstring.append(category.getColor());
			transactionstring.append("\", highlight: \"lightskyblue\",label: \"");
			transactionstring.append(category.getName());
			transactionstring.append("\"}");
			chartString.append(transactionstring);
			chartString.append(',');
		}
		if(chartString.length()<1) return null;
		chartString.setCharAt(chartString.length()-1, ' ');
		return chartString.toString();
	}
	
	
	
	private List<Category> convertResultToArray(Result result){
		List<Category> array = new ArrayList<>();
		ResultSet resultSet = result.getResultSet();
		try {
			while(resultSet.next()){
				UserDao userDao = new UserDaoImpl(databaseContext);
				User user = userDao.getUser(resultSet.getInt("user_id"));
				array.add( new Category(
						resultSet.getInt("id"),
						resultSet.getString("name"),
						user,
						resultSet.getInt("type"),
						resultSet.getString("color")
						)
				);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
			
		return array;	
	}
	
	private double sumOfAmount(Result result){
		double Sum=0;
		ResultSet resultSet = result.getResultSet();
		try {
			while(resultSet.next()){
				UserDao userDao = new UserDaoImpl(databaseContext);
				Sum = resultSet.getInt("SUMAMOUNT");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
		return Sum;
	}
	/**
	 * Metoda dodająca nową kategorie do bazy danych
	 * @param category Kategoria do dodania
	 * @return wartość logiczna operacji
	 */
	@Override
	public boolean addCategory(Category category) {
		if(category == null){
			return false;
		}
		String sql = "INSERT INTO `categories` (`name`, `type`, `color`, `user_id`) "
				+ "VALUES (" +
				"'" + category.getName() + "'," +
				"'" + category.getType().getValue() + "'," +
				"'" + category.getColor() + "'," +
				"'" + category.getUser().getId() + "'" +
				");";
		return executeUpdateSqlQuery(sql);
	}
	/**
	 * Metoda zwracajaca kategorie przez nazwę i uzytkownika z bazy danych
	 * @param name Nazwa kategori
	 * @param user Użytkownik powiązany z kategorią
	 * @return Kategoria
	 */
	@Override
	public Category getCategoryByName(String name, User user) {
		if(name == null || user == null){
			return null;
		}
		List<Category> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM `categories` "
				+ "WHERE name = '" + name + "' "
				+ "AND user_id = " + user.getId()));
		return list.isEmpty() ? null : list.get(0);
	}

	

	
	
	
}
