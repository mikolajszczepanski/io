package app.models.dao;

import java.util.List;

import app.models.HelpQuestion;

/**
 * Interfejs zawierający metody obługi pytań pomocniczych 
 * @see HelpQuestion
 */
public interface HelpQuestionDao {
	/**
	 * Zwraca liste pytań pomocniczych
	 * @return Lista pytań pomocniczych
	 */
	List<HelpQuestion> getAllHelpQuestions();
	/**
	 * Zwraca określone pytanie pomocnicze z id
	 * @param id Id szukanego pytania pomocniczego
	 * @return Pytanie pomocnicze
	 */
	HelpQuestion getHelpQuestion(int id);
}