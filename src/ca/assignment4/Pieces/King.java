package ca.assignment4.Pieces;

public class King extends ChessPiece {
	
    public King(int _x, int _y, int _playerNum) {
        super(_x, _y, _playerNum);
    }
    
    @Override
    public boolean canMoveTo(int _newX, int _newY, int _playerTurn) {
        if(playerNum == _playerTurn)
        {
            //Get the mangitude of the difference between the current location and the new location
            int deltaX = Math.abs(_newX - x);
            int deltaY = Math.abs(_newY - y);
        
            //Check for single spot diagonal movement in both X and Y axis
            if(deltaX == 1 && deltaY == 1)
                return true;
        
            //If it's not diagonal, check for straight line movement where either x = newX and y = +/-1 newY OR y = newY and x = +/-1 newX
            else 
                return (((x + 1) == _newX && y == _newY) || ((x - 1) == _newX && y == _newY) ||
                    (x == _newX && (y + 1) == _newY) || (x == _newX && (y - 1) == _newY));
        }
        else
            return false;
        
    }


    @Override
    public String getName() {
        String name = "K" + playerNum;
        return name;
    }

    @Override
    public int getPieceType() {
        return 6;
    }
    
    @Override
    protected void Died() {
        
    }

	@Override
	public String getFullName() {
		String name = "King";
        return name;
	}
}

