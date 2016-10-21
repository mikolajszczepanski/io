package app.models;

/**
 * Klasa pytania pomocniczego wykorzystywana, gdy użytkownik poda 3 razy źle hasło
 */
public class HelpQuestion {
	
	private int id;
	private String question;
	
	/**
	 * Ustawienie pytania pomocniczego
	 * @param id id pytania
	 * @param question pytanie
	 */
	public HelpQuestion(int id, String question) {
		this.id = id;
		this.question = question;
	}
	
	/**
	 * Zwraca pytanie w postaci zmiennej typu String
	 * @return question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * Zwraca id pytania
	 * @return id
	 */
	public int getId(){
		return id;
	}
	
	/**
	 * Zamienia obiekt klasy HelpQuestion na String
	 * @return obiekt typu string
	 */
	@Override
	public String toString(){
		return id + " " + question;
	}
}