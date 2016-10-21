package app.models;
/**
 * enum opisujący częstotliwość transakcji
 * ONCE(0),WEEKLY(1),MONTHLY(2);
 */
public enum FrequencyType {
	ONCE(0),WEEKLY(1),MONTHLY(2);
	private final int value;
	private FrequencyType(int value){
		this.value = value;
	}
	/**
	 * zwraca częstotliwość
	 * @return value
	 */
	public int getValue(){
		return value;
	}
}