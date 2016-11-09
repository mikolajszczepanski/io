package app.models.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.models.DatabaseContext;
import app.models.HelpQuestion;
import app.models.User;

/**
 * Klasa odpowiedzialna za obsługe obiektów User przez zapytania SQL bazy danych
 * @see User
 */
public class UserDaoImpl extends Dao implements UserDao {
	
	/**
	 * Konstruktor bezargumentowy.
	 */
	public UserDaoImpl(){
		super();
	}
	

	/**
	 * Konstruktor przyjmujący DatebaseContext
	 * @param databaseContext Obiekt zawierający połączenie z bazą danych
	 */
	public UserDaoImpl(DatabaseContext databaseContext) {
		super(databaseContext);
	}
	
	@Override
	/**
	 * Metoda dodająca użytkownika. Hasło zostaje hashowane podczas operacji SHA1 w bazie danych.
	 * @param user Użytkownik
	 * @return wartość logiczna operacji
	 */
	public boolean addUser(User user){
		String sql = "INSERT INTO `users` (`id`, `username`, `password`, `email`, `created_at`, `help_question_id`,`answer`) "
				+ "VALUES (NULL, '"
				+ user.getUsername() +"', SHA1('"
				+ user.getPassword() + "'), '" 
				+ user.getEmail() + "',"
				+ "CURRENT_TIMESTAMP,"
				+ user.getHelpQuestion().getId() + ",SHA1('"
				+ user.getAnswer() + "')"
				+ ");";
		return executeUpdateSqlQuery(sql);
	}
	/**
	 * Metoda pozwalająca zmienić hasło użytkownikowi z podanym loginem w bazie danych.
	 * @param username Nazwa użytkownika ( login )
	 * @param newpassword Nowe hasło użytkownika ( hasło zostanie zshashowane w zapytaniu )
	 * @return Wartość logiczna operacji
	 */
	@Override
	public boolean changePassword(String username,String newpassword){
		String sql = "UPDATE `users` "
			    + "SET `password` =  SHA1('"
				+ newpassword + "')"
				+ " WHERE `username` = '" + username+"'";
		return executeUpdateSqlQuery(sql);
	}
	
	/**
	 * Metoda zwraca użytkownika z podanym loginem z bazy danych
	 * @param username Nazwa użytkownika ( login )
	 * @return Użytkownik
	 */
	@Override
	public User getUserByUsername(String username) {
		List<User> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM users "
				+ "WHERE username = '" + username + "'"));
		return list.isEmpty() ? null : list.get(0);
	}
	
	private List<User> convertResultToArray(Result result){
		List<User> array = new ArrayList<>();
		ResultSet resultSet = result.getResultSet();
		
		try {
			while(resultSet.next()){
				HelpQuestionDao questionDao = new HelpQuestionDaoImpl(databaseContext);
				HelpQuestion question = questionDao.getHelpQuestion(result.getResultSet().getInt("help_question_id"));
				array.add( new User(
						resultSet.getInt("id"),
						resultSet.getString("username"),
						resultSet.getString("password"),
						resultSet.getString("email"),
						question,
						resultSet.getString("answer"),
						resultSet.getInt("attempts"),
						resultSet.getDouble("monthy_limit")
						)
				);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
			
		return array;	
	}
	
	@Override
	/**
	 * Metoda aktualizująca użytkownika w bazie danych. Metoda nie aktualizuje hasła ani sekretnej odpowiedzi z uwagi iż te dane zostają zhashowane
	 * @param user Użytkownik
	 * @return wartość logiczna operacji
	 */
	public boolean updateUser(User user){
		String sql = "UPDATE `users` "
			    + "SET `attempts` = '" + user.getAttempts() + "',"
			    + "`username` = '" + user.getUsername() + "',"
			    + "`email` = '" + user.getEmail() + "',"
			    + "`monthy_limit` = " + user.getMonthlyLimit() + ""
				+ " WHERE `users`.`id` = " + user.getId();
		
		return executeUpdateSqlQuery(sql);
	}
	@Override
	/**
	 * Metoda zwraca użytkownika z podanym loginem i hasłem z bazy danych
	 * @param username Nazwa użytkownika ( login )
	 * @param password Hasło użytkownika
	 * @return Użytkownik
	 */
	public User getUserByUsernameAndPassword(String username, String password) {
		List<User> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM users "
				+ "WHERE username = '" + username + "' "
				+ "AND password = SHA1('" + password + "')"));
		return list.isEmpty() ? null : list.get(0);
	}
	/**
	 * Metoda zwracająca użytkownika z podanym loginem i odpowiedzą na sekretne pytanie (hashowane podczas zapytania) z bazy danych
	 * @param username Nazwa użytkownika ( login )
	 * @param answer Sekretne pytanie
	 * @return Użytkownik
	 */
	@Override
	public User getUserByUsernameAndAnswer(String username, String answer){
		List<User> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM users "
				+ "WHERE username = '" + username + "' "
				+ "AND answer = SHA1('" + answer + "')"));
		return list.isEmpty() ? null : list.get(0);
	}
	
}