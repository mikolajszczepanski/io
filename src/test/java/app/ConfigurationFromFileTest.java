package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import app.helpers.ConfigurationFromFile;

public class ConfigurationFromFileTest {
	
	@Test
	public void CreateEmptyFileWhenIsNoSettingsFile(){
		String filename = "test1.config";
		
		File file = new File(filename);
		assertTrue(!file.exists());
		
		new ConfigurationFromFile(filename);
		
		file = new File(filename);
		assertTrue(file.exists());
	}
	
	
	private void PrepareMockFile(String filename) throws IOException{
		File file = new File(filename);
		if(file.exists()){
			assertTrue(file.delete());
		}
		
		OutputStream output = new FileOutputStream(filename);
		Properties settings = new Properties();
		settings.setProperty("db", "test_db");
		settings.setProperty("user", "test_user");
		settings.setProperty("password", "test_password");
		settings.store(output, null);
		output.close();
	}
	
	
	
	@Test
	public void LoadFileWhenItIsExists() throws IOException{
		String filename = "test2.config";
		PrepareMockFile(filename);
		
		ConfigurationFromFile settings = new ConfigurationFromFile(filename);
		assertTrue(settings.getUser().equals("test_user"));
		assertTrue(settings.getDatabaseName().equals("test_db"));
		assertTrue(settings.getPassword().equals("test_password"));
		
		DeleteMockFile(filename);
		
	}
	
	@Test
	public void LoadFileWhenItIsNotExists() throws IOException{
		String filename = "test3.config";
		PrepareMockFile(filename);
		
		ConfigurationFromFile settings = new ConfigurationFromFile(filename);
		assertFalse(settings.getUser().equals("not_test_user"));
		assertFalse(settings.getDatabaseName().equals("not_test_db"));
		assertFalse(settings.getPassword().equals("not_test_password"));
		
		DeleteMockFile(filename);
		
	}
	
	@After
	public void DeleteAllMockFiles(){
		DeleteMockFile("test1.config");
		DeleteMockFile("test2.config");
		DeleteMockFile("test3.config");
	}
	
	private void DeleteMockFile(String filename){
		File file = new File(filename);
		if(file.exists()){
			assertTrue(file.delete());
		}
	}
	

}
