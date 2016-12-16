package app.controllers;

import static spark.Spark.get;

import java.util.Date;
import java.util.List;

import app.models.DatabaseContext;
import app.models.FrequencyType;
import app.models.Transaction;
import app.models.dao.TransactionDao;
import app.models.dao.TransactionDaoImpl;

/**
 * Klasa służąca do cyklicznego dodawnia transackji miesięcznych jak i tygodniowych przez cyklicze wywołania job schedulera np: cron
 */
public class Scheduler extends Controller{
	
	/**
	 * Metoda sprawdza wszystkie stałe transakcje miesięczne i dodaje ich kopie z ustawioną transakcją jako jednorazowa
	 * @see Transaction
	 * @see FrequencyType
	 */
	public void checkMonthlyTransactions(){
		get("/cron/cm", (request, response) -> {
			StringBuilder sb = new StringBuilder();
			DatabaseContext db = new DatabaseContext();
			TransactionDao transDao = new TransactionDaoImpl(db);
			List<Transaction> transactions = transDao.getAllMonthly();
			if(transactions != null){
				for (Transaction transaction : transactions) {
					Transaction newTransaction = new Transaction(-1, 
							transaction.getUser(), 
							transaction.getCategory(), 
							transaction.getAmount(), 
							FrequencyType.ONCE, 
							transaction.getTransactionType(), 
							new Date());
					transDao.addTransaction(newTransaction);
					sb.append(newTransaction + "<br>");
				}
			}
			return sb.toString();
		});
	}
	
	/**
	 * Metoda sprawdza wszystkie stałe transakcje tygodniowe i dodaje ich kopie z ustawioną transakcją jako jednorazowa
	 * @see Transaction
	 * @see FrequencyType
	 */
	public void checkWeeklyransactions(){
		get("/cron/cw", (request, response) -> {
			StringBuilder sb = new StringBuilder();
			DatabaseContext db = new DatabaseContext();
			TransactionDao transDao = new TransactionDaoImpl(db);
			List<Transaction> transactions = transDao.getAllWeekly();
			if(transactions != null){
				for (Transaction transaction : transactions) {
					Transaction newTransaction = new Transaction(-1, 
							transaction.getUser(), 
							transaction.getCategory(), 
							transaction.getAmount(), 
							FrequencyType.ONCE, 
							transaction.getTransactionType(), 
							new Date());
					transDao.addTransaction(newTransaction);
					sb.append(newTransaction + "<br>");
				}
			}
			return sb.toString();
		});
	}
	/**
	 * Dodaje niezbędne ścieżki dla frameworka spark
	 */
	@Override
	public void addRoutes() {
		checkMonthlyTransactions();
		checkWeeklyransactions();
	}
}