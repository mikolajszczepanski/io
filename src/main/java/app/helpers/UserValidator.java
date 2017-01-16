package app.helpers;

import java.util.ArrayList;
import java.util.List;

public class UserValidator {
	
	/**
	 * Walidacja nazwy użytkownika, hasła, e-maila oraz odpowiedzi
	 * @param username nazwa użytkownika
	 * @param password hasło
	 * @param email 
	 * @param answer odpowiedź na pytanie
	 * @return lista błędów
	 */
	public static List<String> Validate(String username,String password,String email,String answer) {
		List<String> errors = new ArrayList<String>();
		if (username == null || username.length() < Constants.usernameMinLength || username.length() > Constants.usernameMaxLength) {
			errors.add("Nazwa użytkownika nie mieści się w przedziale ["+Constants.usernameMinLength.toString()+", "+Constants.usernameMaxLength.toString()+"].");
		}
		if (password == null || password.length() < Constants.passwordMinLength || password.length() > Constants.passwordMaxLength) {
			errors.add("Hasło nie mieści się w przedziale ["+Constants.passwordMinLength.toString()+", "+Constants.passwordMaxLength.toString()+"].");
		}
		if (!password.matches(".*\\d+.*")) errors.add("Hasło musi zawierać co najmniej jedną cyfrę.");
		if (password.equals(password.toLowerCase())) errors.add("Hasło musi zawierać co najmniej jedną dużą literę.");
		if (password.equals(password.toUpperCase())) errors.add("Hasło musi zawierać co najmniej jedną małą literę.");
		if (email == null || email.length() < Constants.emailMinLength || email.length() > Constants.emailMaxLength) {
			errors.add("Email nie mieści się w przedziale ["+Constants.emailMinLength.toString()+", "+Constants.emailMaxLength.toString()+"].");
		}
		if (email.indexOf('@') < 1 || email.indexOf('.') < 1 || new StringBuilder(email).reverse().toString().indexOf('.') > new StringBuilder(email).reverse().toString().indexOf('@')) {
			errors.add("Podaj poprawny Email.");
		}
		if (answer == null || answer.length() < Constants.answerMinLength || answer.length() > Constants.answerMaxLength) {
			errors.add("Odpowiedź nie mieści się w przedziale ["+Constants.answerMinLength.toString()+", "+Constants.answerMaxLength.toString()+"].");
		}
		return errors;
	}

}
