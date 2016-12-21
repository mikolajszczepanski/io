package app.helpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class ConfigurationFromFile{
	
	public ConfigurationFromFile(String filename){
		this.filename = filename;
		ReadFromFile();
	}
	
	public String getUser(){
		return user;
	}
	public String getPassword(){
		return password;
	}
	public String getDatabaseName(){
		return databaseName;
	}
	
	private void ReadFromFile(){
		Properties settings = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(filename);
			settings.load(input);
			user = settings.getProperty("user");
			password = settings.getProperty("password");
			databaseName = settings.getProperty("db");
		} catch (FileNotFoundException e) {
			WriteEmptyFile();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(input != null){
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
					
		
	}
	private void WriteEmptyFile(){
		Properties settings = new Properties();
		OutputStream output = null;
		try {
			output = new FileOutputStream(filename);
			settings.setProperty("user", "<user>");
			settings.setProperty("password", "<password>");
			settings.setProperty("db", "<database_name>");
			settings.store(output, null);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(output != null){
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String filename;
	private String user;
	private String password;
	private String databaseName;
}