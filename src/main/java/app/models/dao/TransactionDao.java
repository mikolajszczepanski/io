package app.models.dao;

import java.util.List;

import app.models.Transaction;
import app.models.TransactionType;
import app.models.User;

/**
 * Interfejs zawierający metody obługi transakcji 
 * @see Transaction
 */
public interface TransactionDao {
	/**
	 * Metoda zwracająca wszystkie transakcje miesięczne
	 * @return List transakcji miesięcznych
	 */
	List<Transaction> getAllMonthly();
	/**
	 * Metoda zwracająca wszystkie transakcje cotygodniowe
	 * @return List transakcji cotygodniowych
	 */
	List<Transaction> getAllWeekly();
	/**
	 * Metoda zwracająca wszystkie transakcje danego użytkownika
	 * @param user Użytkownik
	 * @return Lista transakcji użytkownika 
	 */
	List<Transaction> getAllByUser(User user);
	/**
	 * Metoda zwracająca wszystkie transakcje danego użytkownika i według określonego typu
	 * @param user Użytkownik
	 * @param type Typ transackji (jednorazowa,cotygodniowa,miesięczna)
	 * @return Lista transakcji
	 */
	List<Transaction> getTransactionByType(User user,TransactionType type );
	/**
	 * Metoda usuwajaca okreslona transakcje 
	 * @param user Użytkownik do ktorego transakcja jest przypisana
	 * @param transactionId Id transakcji która ma zostać usunięta
	 */
	void removeTransactionByTransactionIdAndUserId(User user,int transactionId);
	/**
	 * Metoda dodająca nową transakcje
	 * @param transaction Obiekt do dodania typu Transaction
	 * @return Wartość logiczna operacji
	 */
	boolean addTransaction(Transaction transaction);
}
