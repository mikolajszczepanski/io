package app.models;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * klasa reprezentująca transakcję
 * zawiera id transakcji, 
 * kategorię 
 * kwota
 * częstotliwość 
 * typ transakcji  
 * date wykonania 
 * oraz użytkownika który ją wykonał 
 * @see Category FrequencyType  TransactionType Date User
 */
public class Transaction {
	private int id;
	private Category category;
	private double amount;
	private FrequencyType frequencyType;
	private TransactionType transactionType;
	private Date date;
	private User user;
	
	public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * konstruktor
	 * @param id id transakcji 
	 * @param user użytkownik 
	 * @param category kategoria 
	 * @param amount kwota transakcji
	 * @param frequencyType częstotliwość transakcji 
	 * @param transactionType typ transakcji 
	 * @param date data wykonania 
	 * @see Category FrequencyType TransactionType Date
	 */
	public Transaction(int id,
			User user,
			Category category,
			double amount,
			FrequencyType frequencyType,
			TransactionType transactionType,
			Date date){
		this.id = id;
		this.category = category;
		this.amount = amount;
		this.setFrequencyType(frequencyType);
		this.setTransactionType(transactionType);
		this.date = date;
		this.user = user;
	}
	/**
	 * zwraca id transakcji
	 * @return id 
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * zwraca kategorie transakcji
	 * @return category
	 */
	public Category getCategory(){
		return category;
	}
	
	/**
	 * zwraca kwotę transakcji
	 * @return amount
	 */
	public double getAmount(){
		return amount;
	}
	
	/**
	 * zwraca date transakcji
	 * @return date
	 */
	public Date getDate(){
		return date;
	}
	/**
	 * zwraca date jako obiekt String
	 * @return String 
	 */
	public String getStringDate(){
		SimpleDateFormat sdf = new SimpleDateFormat(Transaction.DATE_FORMAT);
		String date = sdf.format(this.date);
		return date;
	}
	/**
	 * zwraca częstotliwość transakcji 
	 * @return frequencyType
	 */
	public FrequencyType getFrequencyType() {
		return frequencyType;
	}
	
	/**
	 * 
	 * ustawia częstotliwość transakcji 
	 * @param frequencyType częstotliwość transakcji 
	 */
	public void setFrequencyType(FrequencyType frequencyType) {
		this.frequencyType = frequencyType;
	}
	
	/**
	 * zwraca typ transakcji 
	 * @see TransactionType
	 * @return transactionType
	 */
	public TransactionType getTransactionType() {
		return transactionType;
	}
	
	/**
	 * ustawia typ transakcji
	 * @see transactionType
	 * @param transactionType typ transakcji
	 */
	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}
	/**
	 * zwraca użytkownika który dokonał transakcji
	 * @return user użytkownik
	 */
	public User getUser(){
		return user;
	}
	/**
	 * zamiana obiektu transaction na obiekt klasy String
	 */
	@Override
	public String toString(){
		return id + " u:" + user.toString() + " c:" + category + " a:" + amount + " f:" + frequencyType + " t:" + transactionType + " " + date.toString();
	}
}