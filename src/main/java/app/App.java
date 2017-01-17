package app;

import static spark.Spark.*;
import app.controllers.HomeController;
import app.controllers.ManagmentController;
import app.controllers.Scheduler;
import spark.Spark;
import static spark.Spark.*;
import app.controllers.AccountController;
import app.controllers.HomeController;
import spark.Spark;
/**
 * Główna klasa z której aplikacja jest uruchamiana.
 * @see Register
 */
public class App 
{
	private static Register register = null;
	
	/**
	 * Metoda z której uruchamiana zostaje aplikacja, załadowane zostają kontrolery i ustawiona zostaje ścieżka do plików statycznych 
	 * @param args Nie używane
	 */
    public static void main( String[] args )
    {
    	
    	 /*
    	  * Files : src/main/resources/public
    	  */
    	
    	 Spark.staticFileLocation("/public");
    	 
    	 /*
    	  * Routes
    	  */
    	 
    	 get("/home", (request, response) -> {
    		    response.redirect("/");
    		    return null;
    		});
    	 
    	 /*
    	  * Register
    	  */
    	 
    	 register = new Register();
    	 
    	 /*
    	  * Controllers
    	  */
    	 
    	 register.add( new HomeController() );
    	 register.add( new AccountController() );
    	 register.add( new ManagmentController() );
    	 register.add( new Scheduler() );
    	 
    }
}
