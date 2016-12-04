package app.models.dao;

import java.util.List;

import app.models.User;

/**
 * Interfejs zawierający metody obługi obiektu użytkownika 
 * @see User
 */
public interface UserDao {
	/**
	 * Metoda dodająca użytkownika
	 * @param user Użytkownik
	 * @return wartość logiczna operacji
	 */
	boolean addUser(User user);
	/**
	 * Metoda aktualizująca użytkownika
	 * @param user Użytkownik
	 * @return wartość logiczna operacji
	 */
	boolean updateUser(User user);
	/**
	 * Metoda zwracająca liste wszystkich użytkowników
	 * @return Lista użytkowników
	 */
	List<User> getAllUsers();
	/**
	 * Metoda zwracjąca użytkownika o określonym id
	 * @param id Id Użytkownika
	 * @return Użytkownik z podanym id
	 */
	User getUser(int id);
	/**
	 * Metoda zwraca użytkownika z podanym loginem
	 * @param username Nazwa użytkownika ( login )
	 * @return Użytkownik
	 */
	User getUserByUsername(String username);
	/**
	 * Metoda zwraca użytkownika z podanym loginem i hasłem
	 * @param username Nazwa użytkownika ( login )
	 * @param password Hasło użytkownika
	 * @return Użytkownik
	 */
	User getUserByUsernameAndPassword(String username,String password);
	/**
	 * Metoda zwracająca użytkownika z podanym loginem i odpowiedzą na sekretne pytanie
	 * @param username Nazwa użytkownika ( login )
	 * @param answer Sekretne pytanie
	 * @return Użytkownik
	 */
	User getUserByUsernameAndAnswer(String username,String answer);
	/**
	 * Metoda pozwalająca zmienić hasło użytkownikowi z podanym loginem
	 * @param username Nazwa użytkownika ( login )
	 * @param newpassword Nowe hasło użytkownika
	 * @return Wartość logiczna operacji
	 */
	boolean changePassword(String username,String newpassword);
}
