package ca.chess.errors;

public class CriticalSystemErrorException extends Exception {

	public CriticalSystemErrorException(){
		
		super("There was a critical system error while running the game.");
	}
	
}
