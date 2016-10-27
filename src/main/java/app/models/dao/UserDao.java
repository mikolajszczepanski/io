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
	 * Metoda zwraca użytkownika z podanym loginem
	 * @param username Nazwa użytkownika ( login )
	 * @return Użytkownik
	 */
	User getUserByUsername(String username);
}
