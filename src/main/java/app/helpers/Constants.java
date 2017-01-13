package app.helpers;
/**
 * klasa przypisująca nazwy typom transakcji
 */
public final class Constants {
	
	public static final String SPENDING = "spending";
	public static final String REVENUE = "revenue";
	//Wymagane parametry rejstracji
	public static final Integer usernameMinLength =5;
	public static final Integer usernameMaxLength=20;
	
	public static final Integer passwordMinLength=5;
	public static final Integer passwordMaxLength=40;
	
	public static final Integer emailMinLength=5;
	public static final Integer emailMaxLength=40;
	
	public static final Integer answerMinLength=2;
	public static final Integer answerMaxLength=40;
	public static final String monthPl[]= {"Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec", "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"};
	public static final String days = "'1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20','21','22','23','24','25','26','27','28'";
	
}