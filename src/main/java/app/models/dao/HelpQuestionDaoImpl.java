package app.models.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import app.models.DatabaseContext;
import app.models.HelpQuestion;

/**
 * Klasa odpowiedzialna za obsługe obiektów HelpQuestion przez zapytania SQL bazy danych
 * @see HelpQuestion
 */
public class HelpQuestionDaoImpl extends Dao implements HelpQuestionDao {
	
	
	/**
	 * Konstruktor bezargumentiowy
	 */
	public HelpQuestionDaoImpl(){
		super();
	}
	/**
	 * Konstruktor przyjmujący DatebaseContext
	 * @param databaseContext Obiekt zawierający połączenie z bazą danych
	 */
	public HelpQuestionDaoImpl(DatabaseContext databaseContext) {
		super(databaseContext);
	}
	/**
	 * Zwraca liste pytań pomocniczych z bazy danych 
	 * @return Lista pytań pomocniczych
	 */
	@Override
	public List<HelpQuestion> getAllHelpQuestions() {
		return convertResultToArray(executeSelectSqlQuery("SELECT * FROM help_questions"));
	}
	/**
	 * Zwraca określone pytanie pomocnicze określone przez id z bazy danych
	 * @param id Id szukanego pytania pomocniczego
	 * @return Pytanie pomocnicze
	 */
	@Override
	public HelpQuestion getHelpQuestion(int id) {
		List<HelpQuestion> list = convertResultToArray(executeSelectSqlQuery("SELECT * "
				+ "FROM help_questions "
				+ "WHERE id = '" + id + "' "));
		return list.isEmpty() ? null : list.get(0);
	}
	
	private List<HelpQuestion> convertResultToArray(Result result){
		List<HelpQuestion> array = new ArrayList<>();
		ResultSet resultSet = result.getResultSet();
		try {
			while(resultSet.next()){
				array.add( new HelpQuestion(
						resultSet.getInt("id"),
						resultSet.getString("question")
						)
				);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		result.close();
			
		return array;	
	}

	

}