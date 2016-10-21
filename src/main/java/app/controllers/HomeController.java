package app.controllers;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import app.models.DatabaseContext;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;


/**
 * klasa obsługująca stronę domową
 */
public class HomeController extends Controller {
	
	/**
	 * żądanie get dla '/'
	 * sprawdza połączenie z bazą
	 */
	public void getHome(){
		get("/", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			DatabaseContext db = new DatabaseContext();
			if(!db.CheckConnection()){
				attributes.put("error", "Nie uzyskano połączenia z bazą danych!");
			}
            return new ModelAndView(attributes, "home.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * udostępnia żądanie {@link getHome()} 
	 */
	@Override
	public void addRoutes(){
		getHome();
	}

}
