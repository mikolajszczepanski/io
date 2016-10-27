package app.controllers;

import static spark.Spark.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.models.DatabaseContext;
import app.models.HelpQuestion;
import app.models.User;
import app.models.dao.HelpQuestionDao;
import app.models.dao.HelpQuestionDaoImpl;
import app.models.dao.UserDao;
import app.models.dao.UserDaoImpl;
import spark.ModelAndView;
import spark.template.freemarker.FreeMarkerEngine;
/**
 * klasa obsługująca żądania związane z logowaniem, rejstracją oraz zmianą hasła
 */
public class AccountController extends Controller {
		
	/**
	 * żądanie get dla /logout
	 * wylogowanie
	 */
	public void getLogout(){
		get("/logout", (request, response) -> {
			request.session().removeAttribute("user") ;
			response.redirect("/");
			return new ModelAndView(null, "home.ftl");
        }, new FreeMarkerEngine());
	}
	
	/**
	 * żądanie get dla /register
	 */
	public void getRegister(){
		get("/register", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			
			HelpQuestionDao questionsDao = new HelpQuestionDaoImpl();
			List<HelpQuestion> questions = questionsDao.getAllHelpQuestions(); 
			attributes.put("questions", questions);
            return new ModelAndView(attributes, "account/register.ftl");
        }, new FreeMarkerEngine());
	}
	/**
	 * żądanie post dla /register
	 * rejstruje użytkownika sprawdzając
	 * czy pola wymagane w rejstracji zostały 
	 * dobrze uzupełnione
	 */
	public void postRegister(){
		post("/register", (request, response) -> {
			Map<String, Object> attributes = new HashMap<>();
			DatabaseContext databaseContext = new DatabaseContext();
			
			String login = request.queryParams("login");
			String email = request.queryParams("email");
			String password = request.queryParams("password");
			String passwordRepeat = request.queryParams("passwordRepeat");
			String helpQuestionStr = request.queryParams("helpQuestion");
			String helpAnswer = request.queryParams("helpQuestionAnswer");
			
			
			Boolean checkData = false;
			List<String> errors = new ArrayList<String>();
			if(password.equals(passwordRepeat)){
				List<String> errorsFromValidation = User.Validate(login, password, email, helpAnswer);
				errors.addAll(errorsFromValidation);
				checkData = errorsFromValidation.size() == 0 ? true : false;
			}
			else{
				errors.add("Hasła nie zgadzają się.");
			}
			
			UserDao userDao = new UserDaoImpl(databaseContext);
			if(userDao.getUserByUsername(login) != null){
				checkData = false;
				errors.add("Nazwa użytkownika już istnieje.");
			}
			
			


			if(checkData.booleanValue()){
				int helpQuestion = -1;
				try{
					helpQuestion = Integer.parseInt(helpQuestionStr);
				}catch(NumberFormatException ex){
					errors.add("Coś poszło nie tak...");
				}
				if(helpQuestion != -1){
					HelpQuestionDao questionDao = new HelpQuestionDaoImpl(databaseContext);
					HelpQuestion question = questionDao.getHelpQuestion(helpQuestion);
					User user = new User(-1,login,password,email,question,helpAnswer,User.MAX_ATTEMPTS,0);
					
					if(userDao.addUser(user)){
						attributes.put("success","Pomyślnie dodano konto, zaloguj się.");
						return new ModelAndView(attributes, "account/login.ftl");
					}
					else if(checkData){
						errors.add("Nie udało się wykonać zapytania SQL.");
					}
				}
			}
			HelpQuestionDao questionsDao = new HelpQuestionDaoImpl(databaseContext);
			List<HelpQuestion> questions = questionsDao.getAllHelpQuestions(); 
			attributes.put("questions", questions);
		
			attributes.put("error","Popraw błędy w formularzu a następnie wyślij ponownie.");
			attributes.put("errors", errors);
            return new ModelAndView(attributes, "account/register.ftl");
        }, new FreeMarkerEngine());
	}
	/**
	 * udostępnia żądania dla ścieżek
	 */
	@Override
	public void addRoutes() {
		getRegister();
		postRegister();
	}

}
