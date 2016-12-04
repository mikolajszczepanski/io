package app.models.dao;

import java.util.List;

import app.models.Category;
import app.models.TransactionType;
import app.models.User;

/**
 * Interfejs zawierający metody do obługi obiektów klasy Category
 * @see Category
 */
public interface CategoryDao {
	/**
	 * Metoda zwracająca liste kategorii
	 * @return Lista kategorii
	 */
	List<Category> getAllCategories();
	/**
	 * Metoda zwracająca kategorie z podanym id
	 * @param id Id Kategorii
	 * @return Kategoria
	 */
	Category getCategory(int id);
	/**
	 * Metoda zwracajaca kategorie przez nazwę i uzytkownika
	 * @param name Nazwa kategori
	 * @param user Użytkownik powiązany z kategorią
	 * @return Kategoria
	 */
	Category getCategoryByName(String name, User user);
	/**
	 * Metoda dodająca nową kategorie
	 * @param category Kategoria do dodania
	 * @return wartość logiczna operacji
	 */
	boolean addCategory(Category category);
	/**
	 * Metoda zwracająca wszystkie kategorie powiązane z danym użytkownikiem
	 * @param user Użytkownik
	 * @return Lista kategorii
	 */
	List<Category> getAllByUser(User user);
	/**
	 * Metoda zwracająca wszystkie kategorie powiązane z użytkownikiem i określonego typu
	 * @param user Użytkownik
	 * @param type Typ transakcji
	 * @return Lista kategorii
	 */
	List<Category> getAllByUserAndType(User user, TransactionType type);
	/**
	 * Metoda pomocnicza zwracająca kod javascript'owy
	 * @param user Użytkownik
	 * @param type Typ transakcji
	 * @return String reprezentujący wykonywalny kod javascript
	 */
	String getChartStringByUserAndCategory(User user, TransactionType type);
}
