package app.models;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa definiująca kategorię transakcji, jej nazwę, kolor oraz rodzaj 
 * @see TransactionType
 */
public class Category {
	
	private int id;
	private String name;
	private User user;
	private TransactionType type;
	private String color;
	
	/**
	 * Konstruktor 
	 * @param id id transakcji
	 * @param name nazwa transakcji
	 * @param user użytkownik, który ją stworzył
	 * @param type rodzaj @see TransactionType
	 * @param color kolor transakcji
	 */
	public Category(int id, String name, User user, TransactionType type, String color) {
		this.id = id;
		this.name = name;
		this.user = user;
		this.color = color;
		
		this.type = type; //!
		
		/*if (type == TransactionType.REVENUE.getValue()) {
			this.type = TransactionType.REVENUE;
		} else if (type == TransactionType.SPENDING.getValue()) {
			this.type = TransactionType.SPENDING;
		} else{
			throw new InvalidParameterException();
		}*/
	}
	
	/**
	 * Zwraca id transakcji
	 * @return id 
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Zwraca nazwę transakcji
	 * @return name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Zwraca kolor transakcji
	 * @return color
	 */
	public String getColor() {
		return color;
	}
	
	/**
	 * Zwraca typ transakcji
	 * @return type
	 */
	public TransactionType getType() {
		return type;
	}
	
	/**
	 * Zwraca użytkownika, który stworzył transakcję
	 * @return user
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Dymyślna zamiana obiektu Category na String
	 */
	@Override
	public String toString() {
		return id + " " + name + " " + type + " " + color;
	}
	
	/**
	 * Walidacja nazwy kategorii, typu oraz koloru
	 * @param name nazwa kategorii
	 * @param type typ kategorii
	 * @param color kolor kategorii 
	 * @return lista błędów
	 */
	public static List<String> validate(String name, int type, String color) {
		List<String> errors = new ArrayList<>();
		if (name == null || name.length() < 1 || name.length() > 100){
			errors.add("Długość nazwy kategorii nie mieści się w przedziale [1, 100].");
		}
		if (type != TransactionType.REVENUE.getValue() && type != TransactionType.SPENDING.getValue()) {
			errors.add("Typ kategorii jest niepoprawny.");
		}
		if (color == null || !color.contains("#")) {
			errors.add("Kolor nie jest w prawidłowym formacie.");
		}
		return errors;
	}
}