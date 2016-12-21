package app.models;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import app.helpers.ConfigurationFromFile;

/**
 * Klasa zawierająca połączenie i konfigurację z bazą danych
 */
public class DatabaseContext {
	
	
	
	
	private Connection connection = null;
	private ConfigurationFromFile configuration = null;
	
	/**
	 * Tworzy nową konfiguracje z bazą danych
	 */
	public DatabaseContext(){
		
		configuration = new ConfigurationFromFile("settings.config");
		
	}
	
	/**
	 * Metoda służąca do zainicjowania połączenie z bazą danych 
	 */
	public void connect(){
		try {
			
			connection = DriverManager.getConnection("jdbc:mysql://localhost/"
		    + configuration.getDatabaseName()
			+ "?user=" + configuration.getUser()
			+ "&password=" + configuration.getPassword()
			+ "&characterEncoding=utf8");
			
			
		} catch (SQLException ex) {
			
			connection = null;
			
			System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		    
		}
		
	}
	/**
	 * Metoda sprawdzająca czy połączenie z bazą istnieje, jeżeli nie połączenie jest nawiązywane a wartość logiczna z tej operacji jest zwraca
	 * @return boolean wartość logiczna reprezentująca czy połączenie z bazą zostało nawiązane
	 */
	public boolean CheckConnection(){
		if(connection == null){
			connect();
		}
		return connection != null ? true : false;
	}
	/**
	 * Getter zwracający obiekt z połączeniem
	 * @return Connection
	 */
	
	public Connection getConnection(){
		return connection;
	}
	
}
