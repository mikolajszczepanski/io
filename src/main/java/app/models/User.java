package app.models;

import java.util.ArrayList;
import java.util.List;

import app.helpers.Constants;

/**
 * Klasa użytkownik zawiera:
 * - nazwę użytkownika
 * - hasło
 * - e-mail
 * - pytanie pomocnicze
 * - odpowiedź na pytanie
 * - liczbę prób zalogowania
 */
public class User {
	
	final public static int MAX_ATTEMPTS = 3;
	
	/**
	 * Konstruktor
	 * @param id id użytkownika
	 * @param username nazwa użytkownika
	 * @param password hasło
	 * @param email e-mail
	 * @param question pytanie pomocnicze
	 * @param answer odpowiedź
	 * @param attempts próby logowania  
	 * @param monthlyLimit limit miesięczny
	 */
	public User(int id, String username, String password, String email, HelpQuestion question, String answer, int attempts, double monthlyLimit) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.question = question;
		this.answer = answer;
		this.setAttempts(attempts);
		this.monthlyLimit = monthlyLimit;
	}
	
	private int id;
	private String username;
	private String password;
	private String email;
	private HelpQuestion question;
	private String answer;
	private int attempts;
	private double monthlyLimit;
	
	/**
	 * Zwraca nazwę użytkownika
	 * @return username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Zwraca hasło
	 * @return password
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 * Zwraca e-mail
	 * @return email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * Zwraca id użytkownika
	 * @return id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Zwraca pytanie pomocnicze
	 * @return question
	 */
	public HelpQuestion getHelpQuestion() {
		return question;
	}
	
	/**
	 * Zwraca odpowiedź na pytanie pomocnicze
	 * @return answer
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * Zwraca limit miesięczny
	 * @return monthlyLimit
	 */
	public double getMonthlyLimit() {
		return monthlyLimit;
	}
	
	/**
	 * Zwraca true, jeżeli użytkownik ustawił limit miesięczny lub false, jeśli nie
	 * @param status miesięcznego limitu (aktywny lub nie)
	 */
	public boolean haveMonthyLimit() {
		return monthlyLimit > 0;
	}
	
	/**
	 * Ustawienie miesięcznego limitu
	 * @param monthlyLimit
	 */
	public void setMonthlyLimit(double monthlyLimit) {
		this.monthlyLimit = monthlyLimit;
	}
	
	/**
	 * Zwraca miesięczny limit w postaci obiektu klasy String
	 * @return limit miesięczny jako string
	 */
	public String getMonthlyLimitAsString() {
		return Double.toString(monthlyLimit).replace(",", ".");
	}
	
	/**
	 * Zamiana obiektu User na obiekt klasy String
	 */
	@Override
	public String toString(){
		return id + " " + username + " " + password + " " + email + " " + attempts + " " + monthlyLimit;
	}
	
	/**
	 * Zwraca pozostałą liczbę prób logowania
	 * @return attempts
	 */
	public int getAttempts() {
		return attempts;
	}
	
	/**
	 * Ustawia liczbę prób logowania
	 * @param attemps próby logowania
	 */
	public void setAttempts(int attemps) {
		this.attempts = attemps;
	}
}