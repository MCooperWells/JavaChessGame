package ca.chess.errors;

public class NullSpaceException extends Exception {

	
	public NullSpaceException(int[] _pos){
		
		super("Error: No pieces found on " + _pos[0] + "x & " + _pos[1] + "y. Cannot be moved.");
		
	}
}
