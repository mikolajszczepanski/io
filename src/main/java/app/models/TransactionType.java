package app.models;

/**
 * Enum definiujący rodzaj transakcji
 */
public enum TransactionType {
	
	SPENDING(0), REVENUE(1);
	
	private final int value;
	
	private TransactionType(int value) {
		this.value = value;
	}
	
	/**
	 * Zwraca wartość 
	 * 0 - wydatki
	 * 1 - przychody 
	 * @return value
	 */
	public int getValue() {
		return value;
	}
}