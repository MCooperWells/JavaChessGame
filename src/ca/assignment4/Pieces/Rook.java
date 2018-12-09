package ca.assignment4.Pieces;

public class Rook extends ChessPiece {
	
    public Rook(int _x, int _y, int _playerNum) {
        super(_x, _y, _playerNum);
    }

    @Override
    public boolean canMoveTo(int _newX, int _newY, int _playerTurn) {
        //Check for straight line movement, where the one of the two values must be different while the other must be the same
        if(playerNum == _playerTurn)
            return((x != _newX && y == _newY) || (x == _newX && y != _newY));
        else
            return false;
    }

    @Override
    public String getName() {
        String name = "r" + playerNum;
        return name;
    }
    
	@Override
	public String getFullName() {
		String name = "Rook";
        return name;
	}
    
    @Override
    public int getPieceType() {
        return 3;
    }
    
    @Override
    protected void Died() {
        
    }
}

