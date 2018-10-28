package ca.chess.errors;

public class NullSpaceException extends Exception {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5760850929067414284L;

	public NullSpaceException(int[] _pos){
		
		super("Error: No pieces found on " + _pos[0] + "x & " + _pos[1] + "y. Cannot be moved.");
		
	}
}
