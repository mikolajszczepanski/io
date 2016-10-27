package app.models.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import app.models.DatabaseContext;

/**
 * Abstrakcyjna klasa Dao zawierająca podstawowe metody obługujące zapytania SQL
 */
public abstract class Dao {

	/**
	 * Klasa Result zawiera Statement i ResultSet odpowiadające za obsługe zapytania i odpowiedzi w zapytaniach SQL
	 */
	
	class Result{
		private Statement statement;
		private ResultSet resultSet;
		/**
		 * Konstruktor
		 * @param Statement
		 * @param ResultSet
		 */
		public Result(Statement statement, ResultSet resultSet){
			this.statement = statement;
			this.resultSet = resultSet; 
		}
		/**
		 * Getter zwracający obiekt ResultSet
		 * @return ResultSet
		 */
		ResultSet getResultSet(){
			return resultSet;
		}
		/**
		 * Metoda zamykająca zasoby wykorzystywane podczas zapytania
		 */
		void close(){
			try{
				if(statement != null){
					statement.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
			try{
				if(resultSet != null){
					resultSet.close();
				}
			
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
	}
	
	protected DatabaseContext databaseContext = null;
	
	/**
	 * Konstruktor bezargumentowy, tworzy nowy obiekt DatabaseContext z wcześniej ustalonmi ustawieniami
	 * @see DatabaseContext
	 */
	public Dao(){
		this.databaseContext = new DatabaseContext();
	}
	
	/**
	 * Konstruktor przyjmujący DatabaseContext
	 * @param databaseContext obiekt zawierający połączenie z bazą danych
	 */
	public Dao(DatabaseContext databaseContext){
		this.databaseContext = databaseContext;
	}
	
	/**
	 * Metoda służąca do wykonywania zapytań SQL typu UPDATE, DELETE, przyjmuje string zawierający zapytanie SQL które zostanie wykonane
	 * @param sql string zawierajacy zapytanie SQL
	 * @return boolean
	 */
	protected boolean executeUpdateSqlQuery(String sql){
		if(!databaseContext.CheckConnection()){
			return false;
		}
		
		Statement statement = null;
		boolean result = false;
		try {
			statement = databaseContext.getConnection().createStatement();
			statement.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
			result = true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
		
	}
	
	/**
	 * Metoda służąca do wykonywania zapytań SQL typu SELECT, przyjmuje string zawierający zapytanie SQL które zostanie wykonane
	 * @param sql string zawierajacy zapytanie SQL
	 */
	protected Result executeSelectSqlQuery(String sql){
		if(!databaseContext.CheckConnection()){
			return null;
		}
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = databaseContext.getConnection().createStatement();
			
			if(statement.execute(sql)){
				resultSet = statement.getResultSet();	
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new Result(statement, resultSet);
	}
	
	
}