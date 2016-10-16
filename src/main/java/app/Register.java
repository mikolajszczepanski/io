package app;

import java.util.ArrayList;
import java.util.List;

import app.controllers.Controller;


/**
 * Klasa przechowywująca odwołania do zarejestrowanych kontrolerów i dodająca odpowednie ścieżki dla metod POST,GET do obługi przez framework spark
 */
class Register {
	
	private List<Controller> listOfControllers = null;
	
	/**
	 * Konstruktor 
	 */
	public Register(){
		listOfControllers = new ArrayList<Controller>();
	}
	
	/**
	 * Metoda dodająca controller i wywołująca metode addRoutes w celu przekazania ścieżek do obsługi przez framework spark
	 * @param controller Obiekt typu Controller 
	 */
	public void add(Controller controller){
		listOfControllers.add(controller);
		controller.addRoutes();
	}
}


