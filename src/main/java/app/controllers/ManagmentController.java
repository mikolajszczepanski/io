package app.controllers;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.models.Transaction;
import app.models.TransactionType;
import app.models.User;
import app.models.dao.CategoryDao;
import app.models.dao.CategoryDaoImpl;
import app.models.dao.TransactionDao;
import app.models.dao.TransactionDaoImpl;
import app.models.dao.UserDao;
import app.models.dao.UserDaoImpl;
import app.models.Category;
import app.models.DatabaseContext;
import app.models.FrequencyType;
import app.models.Constants;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;

/**
 * klasa obsługująca żądzania związanie z działaniem aplikacji
 */
public class ManagmentController extends Controller {
	

	
	
	
	/**
	 * sprawdzenie czy sesja użytkownika nie wygasła
	 */
	public void authentication(){
		before("/app/*", (request, response) -> {
			User user = request.session().attribute("user");
			if(user == null){
				response.redirect("/login?error=auth");
			}
		});
	}
	
	/**
	 * udostępnia żądania dla ścieżek
	 */
	@Override
	public void addRoutes() {
		authentication();
		//Dodawac ponizej nowe scieżki:
		
	}

}
