package ca.chess.core.data;

import ca.chess.pieces.Bishop;
import ca.chess.pieces.ChessPiece;
import ca.chess.pieces.King;
import ca.chess.pieces.Knight;
import ca.chess.pieces.Pawn;
import ca.chess.pieces.Queen;
import ca.chess.pieces.Rook;

public class GameData {
	
	
	public ChessPiece[] player1;
	public ChessPiece[] player2;
	
	public GameData(){
		
	    //Make the array of chess pieces for player 1
	    player1 = new ChessPiece[16];
	    		
	    		
	    	    //Add the Pawns to the player chess piece array
	    	    for (int i = 0; i < 8; i++) {
	    	        player1[i] = new Pawn(i, 1, 1);
	    	    }

	    	    //Add the Rooks
	    	    player1[8] = new Rook(0, 0, 1);
	    	    player1[15] = new Rook(7, 0, 1);

	    	    //The Knights!
	    	    player1[9] = new Knight(1, 0, 1);
	    	    player1[14] = new Knight(6, 0, 1);

	    	    //The Bishops
	    	    player1[10] = new Bishop(2, 0, 1);
	    	    player1[13] = new Bishop(5, 0, 1);

	    	    //Finally, add the Royalty, her majesty the Queen and his high lord, the King!
	    	    player1[11] = new Queen(3, 0, 1);
	    	    player1[12] = new King(4, 0, 1);	
		
	    	    
	    	    
	    	    player2 = new ChessPiece[16];

	    	    for (int i = 0; i < 8; i++) {
	    	        player2[i] = new Pawn(i, 6, 2);
	    	    }

	    	    player2[8] = new Rook(0, 7, 2);
	    	    player2[15] = new Rook(7, 7, 2);

	    	    player2[9] = new Knight(1, 7, 2);
	    	    player2[14] = new Knight(6, 7, 2);

	    	    player2[10] = new Bishop(2, 7, 2);
	    	    player2[13] = new Bishop(5, 7, 2);

	    	    player2[11] = new Queen(3, 7, 2);
	    	    player2[12] = new King(4, 7, 2);
	    	    
	    	    
	}
	
	
	
	/* 
    PLAYER 1
    0,1     1,1     2,1     3,1     4,1     5,1     6,1     7,1  
    pawn    pawn    pawn    pawn    pawn    pawn    pawn    pawn
    0,0     1,0     2,0     3,0     4,0     5,0     6,0     7,0   
    rook   knight  bishop  queen   king    bishop  knight   rook  
    //Setup Player 1
    //0-7 indices are the pawns, followed by 8,9 are knights, 10, 11 are bishops, 12,13 are rooks, 14 is the queen and 15 is the king

    		
    /* PLAYER 2
    0,7     1,7     2,7     3,7     4,7     5,7     6,7    7,7   
    rook   knight  bishop  queen   king    bishop  knight  rook
    
    0,6     1,6     2,6     3,6     4,6     5,6     6,6     7,6  
    pawn    pawn    pawn    pawn    pawn    pawn    pawn    pawn   */
    //Setup Player 2  - Same method as for player 1 but this time pass the new pieces their player Num (2)
    //0-7 indices are the pawns, followed by 8,9 are rooks, 10, 11 are knights, 12,13 are bishops, 14 is the queen and 15 is the king

    //Finally, once the two arrays of chess pieces are setup, create the Board using player1 and player 2
	
	public GameData LoadGame() {
		
		
		
		//This is where we can read the file and override the games DEFUALT data.
		
		return null;
	}
	
}
