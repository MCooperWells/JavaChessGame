package ca.chess.pieces;

public class Knight extends ChessPiece {
	
    public Knight(int _x, int _y, int _playerNum) {
        super(_x, _y, _playerNum);
    }

    @Override
    public boolean canMoveTo(int _newX, int _newY, int _playerTurn) {
        if(playerNum == _playerTurn)
        {
            //Get the mangitude of the difference between the current location and the new location
            int deltaX = Math.abs(_newX - x);
            int deltaY = Math.abs(_newY - y);
        
            //First check if the difference in x positions is equal to 2
            if(deltaX == 2)
                //If it is, check if the different in y position is equal to 1
                return(deltaY == 1);       

            //Else, check if the difference in Y positions is equal to 2
            else if(deltaY == 2)
                //If it is, check if the difference X positions is equal to 1
                return(deltaX == 1);
        
            else   
                return false; 
        }
        else
            return false;
        
    }

    @Override
    public String getName() {
        String name = "n" + playerNum;
        return name;
    }
    
    @Override
    public int getPieceType() {
        return 2;
    }
    
    @Override
    protected void Died() {
        
    }

	@Override
	public String getFullName() {
		String name = "Knight";
        return name;
	}
    
}
