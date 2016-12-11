package app.models;
/**
 * generyczna klasa 
 * @param <T> klucz
 * @param <U> wartość
 */
public class Pair<T,U>{

	private T key;
	private U value;	
	
	/**
	 * konstruktor 
	 * @param key klucz
	 * @param value wartość
	 */
	public Pair(T key, U value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * zwraca klucz
	 * @return key klucz
	 */
	public T getKey() {
		return key;
	}
	
	/**
	 * ustawia klucz
	 * @param key klucz
	 */
	public void setKey(T key) {
		this.key = key;
	}
	
	/**
	 * zwraca wartość
	 * @return value wartość
	 */
	public U getValue() {
		return value;
	}
	/**
	 * ustawia wartość
	 * @param value wartość
	 */
	public void setValue(U value) {
		this.value = value;
	}
}
