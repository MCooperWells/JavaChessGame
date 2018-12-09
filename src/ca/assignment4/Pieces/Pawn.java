package ca.assignment4.Pieces;

public class Pawn extends ChessPiece {
    int compareY = 0;
    
    //Default constructor, needs an x and a y
    public Pawn(int _x, int _y, int _playerNum) {
        super(_x, _y, _playerNum);
        
        switch(_playerNum) {
            case 1:
                //If it is player1's pawn, then the deltaY comparison must be positive
                compareY = 1;
                break;
            case 2:
                //If it is player2's pawn, then the deltaY comparison must be negative
                compareY = -1;
                break;
            default:
                System.out.println("ERROR");
                break;
        }
    }

    @Override
    public boolean canMoveTo(int _newX, int _newY, int _playerTurn) {
        if(playerNum == _playerTurn)
        {
            int deltaY = _newY - y;
            int deltaX = Math.abs(_newX - x);
            
            //if it is the first turn, then the pawn could move forward 1 or 2 spots
            if(firstTurn)
            {     
                //if it is player 1's turn, then the different between the new Y and old one has to be positive
                boolean value = (x == _newX && deltaY == (compareY * 1) || x == _newX && deltaY == (compareY * 2) || deltaX == 1 && deltaY == (compareY * 1));
                if (value) {
                    firstTurn = false;
                    return true;
                } 
                else {
                    return false;
                }
            }
               
            //If it is not the first turn, then only allow the pawn to move forward 1 spot
            else
            {
                //If the move was in a straight line or the move was one step to the left or right, this will return true
                return(x == _newX && deltaY == (compareY * 1) || deltaX == 1 && deltaY == (compareY * 1));
            }
        }
        else
            return false;
    }

    @Override
    public String getName() {
        String name = "p" + playerNum;
        return name;
    }
    
    @Override
    public int getPieceType() {
        return 1;
    }
    
    @Override
    protected void Died() {
        
    }

	@Override
	public String getFullName() {
		String name = "Pawn";
        return name;
	}
    
}

