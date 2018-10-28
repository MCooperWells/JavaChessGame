package ca.chess.errors;

public class CriticalSystemErrorException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2284127523980624626L;

	public CriticalSystemErrorException(){
		
		super("There was a critical system error while running the game.");
	}
	
}
