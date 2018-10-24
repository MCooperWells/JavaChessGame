package ca.chess.pieces;

public abstract class ChessPiece {
	
	//An internal method of storing the location of this piece
	public int x = 0, y = 0;
    
	//Which player the piece belongs to
    public int playerNum;
    
    //This boolean is used when determiningg where a pawn can move
    public boolean firstTurn = true;
    
    //Simple constructor. Where is the starting location of this piece and who's player is does the piece belong to
    public ChessPiece(int _x, int _y, int _playerNum) {
        x = _x;
        y = _y;
        playerNum = _playerNum;
    }
    
    //Returns true if a piece could move to the input location (also checks if it's the correct player's turn)
    public abstract boolean canMoveTo(int x, int y, int playerTurn);
    
    //Get a short-hand version of the player's piece name (EG. Player 1's Pawn = P1 and Player 2's King = K2)
    public abstract String getName();
    
    //Returns the full name of the piece
    public abstract String getFullName();
    
    //Returns an int for the piece type. 1 = pawn, 2 = Knight, 3 = Rook, 4 = Bishop, 5 = Queen and 6 = King
    public abstract int getPieceType();
    
    //Did the piece die? This will be used in later iterations of the game
    protected abstract void Died();
    
    //Class's method for moving
    public void moveTo(int _newX, int _newY)
    {
        x = _newX;
        y = _newY;
    }
    
    //Abstract class's method for getting the position of the piece
    public int getPosition()[] {
        int[] position = new int[2];
        position[0] = x;
        position[1] = y;
        return position;
    }
    
    //Simple getter functions for the individual parts
    public int getXLocation() {
        return x;
    }
    public int getYLocation() {
        return y;
    }
    
}
