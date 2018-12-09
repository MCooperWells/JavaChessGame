package ca.assignment4.Pieces;

public class Queen extends ChessPiece {

    public Queen(int _x, int _y, int _playerNum) {
        super(_x, _y, _playerNum);
    }

    @Override
    public boolean canMoveTo(int _newX, int _newY, int _playerTurn) {
        if(playerNum == _playerTurn)
        {
            //Get the mangitude of the difference between the current location and the new location
            int deltaX = Math.abs(_newX - x);
            int deltaY = Math.abs(_newY - y);

            //Check for diagonal movement
            if(deltaX == deltaY)
                return true;

            //If it's not diagonal, check for straight line movement
            else 
                return ((x != _newX && y == _newY) || (x == _newX && y != _newY));
        }
        else
            return false;
    }

    @Override
    public String getName() {
        String name = "Q" + playerNum;
        return name;
    }
    
	@Override
	public String getFullName() {
		String name = "Queen";
        return name;
	}
    
    @Override
    public int getPieceType() {
        return 5;
    }
    
    @Override
    protected void Died() {
        
    }
    
}

