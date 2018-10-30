package ca.chess.core;

import ca.chess.pieces.ChessPiece;

public class GameBoard {

    //Create the board array of pieces which will be a 8x8 array (board is 8 to 8 square)
    ChessPiece[][] pieces;

    
    //Overloaded Constructor that takes in only one array of pieces, when the game is loaded from memory
    public GameBoard(ChessPiece[] _loadedPieces) {

        //Set the array of pieces to be equal to a new 2D array of size 8x8
        pieces = new ChessPiece[8][8];

        //loop through the size of the player and for each location in the array (there are 16 pieces per player)
        //add each piece to the board
        for (int i = 0; i < _loadedPieces.length; i++) {
            //Add the piece of player 1, index "i" to the board based on that pieces internal position
            //This internal position is set in the game manager class
            pieces[_loadedPieces[i].getPosition()[0]][_loadedPieces[i].getPosition()[1]] = _loadedPieces[i]; 
        }
    }

    //Print out the board, by looping through all 8 rows and 8 columns (nested for loops)
    public void PrintBoard() {
        //Print out the top row of square locations
        System.out.println("    0    1    2    3    4    5    6    7");
        System.out.println("  |---------------------------------------|");

        //Nested For Loop
        for (int i = 7; i > -1; i--) {
            System.out.print(i + " ");
            for (int j = 0; j < 8; j++) {

                //if the location in the board array is not null, print out the short hand version of the pieces name
                if (pieces[j][i] != null) {
                    System.out.print("| " + pieces[j][i].getName() + " ");
                } //Otherwise, that spot is empty so print an empty space
                else {
                    System.out.print("|    ");
                }
            }
            System.out.println("| " + i);
            System.out.println("  |---------------------------------------|");
        }

        //Finally, print out the bottom row of square locations
        System.out.println("    0    1    2    3    4    5    6    7");

    }

    //When moving the piece, first call the internal function of that piece so it knows where it is located on the board and then
    //move that piece on the board to it's new location, and set it's old location to null
    public void MovePiece(int xCurrent, int yCurrent, int xNew, int yNew) {
        pieces[xCurrent][yCurrent].moveTo(xNew, yNew);
        pieces[xNew][yNew] = pieces[xCurrent][yCurrent];
        pieces[xCurrent][yCurrent] = null;
    }

    //Called when checking if there are any pieces in between the current location of the piece and it's destination
    public boolean CheckInBetween(int xCurrent, int yCurrent, int xNew, int yNew, int playerTurn) {
        //Switch on the piece type. Need to check which piece is being moved 
        switch (pieces[xCurrent][yCurrent].getPieceType()) {

            //*********************************************************************************************************
            // Handle Pawns ---------------------------------------------------------------------------------------------
            //*********************************************************************************************************
            case 1:
                //If the pawn is not on it's first turn, then it cannot move through a piece as it can only move 1 square
                if (!pieces[xCurrent][yCurrent].firstTurn) {
                    return true;
                }

                //Set a simple integer equal to 1, which will be used for player 1 
                int turn = 1;

                //Set the integer equal to negative 1 if it is player 2's turn
                if (playerTurn == 2) {
                    turn = -1;
                } //Otherwise, it can move through one piece and that square needs to be checked
                else {
                    if ((yCurrent + turn) == yNew) {
                        return true;
                    } else {
                        return (pieces[xCurrent][yCurrent + turn] == null);
                    }
                }

            //********************************************************************************
            // Handle Knight - note: they can move through pieces so we return true right away
            //********************************************************************************
            case 2:
                return true;

            //*********************************************************************************************************
            // Handle Rook ----------------------------------------------------------------------------------------------
            //**********************************************************************************************************
            case 3:

                //First, check if the Rook is moving in the Y-axis (therefore the current X location should be equal to the new X)
                if (xCurrent == xNew) {
                    //If the Rook is moving in -Y only -----------------------------
                    if (yCurrent > yNew) {
                        //Loop through each location below the current Y value until that value reaches its destination (newY)
                        for (int i = (yCurrent - 1); i > yNew; i--) {
                            //Check if the piece in between is null. If it isn't null, return false
                            if (pieces[xCurrent][i] != null) {
                                return false;
                            }
                        }

                        /*If the for loop completes without any non-null places, then return true as there is nothing in between the
    				  pieces current location and it's final destination*/
                        return true;
                    } //If the Rook is moving in +Y only -----------------------------
                    else if (yCurrent < yNew) {
                        //Loop through each location above the current Y value until that value reaches its destination (newX)
                        for (int i = (yCurrent + 1); i < yNew; i++) {
                            //Check if the piece in between is null. If it isn't null, return false
                            if (pieces[xCurrent][i] != null) {
                                return false;
                            }
                        }

                        /*If the for loop completes without any non-null places, then return true as there is nothing in between the
  				  	pieces current location and it's final destination*/
                        return true;
                    }
                } //The Rook must be moving in the X-axis (therefore the current Y location should be equal to the new Y)
                
                else if (yCurrent == yNew) {
                    //If the Rook is moving in -X only ------------------------------------
                    if (xCurrent > xNew) {
                        //Loop through each location below the current X value until that value reaches its destination (newX)
                        for (int i = (xCurrent - 1); i > xNew; i--) {
                            if (pieces[i][yCurrent] != null) {
                                return false;
                            }
                        }

                        /*If the for loop completes without any non-null places, then return true as there is nothing in between the
  				  	pieces current location and it's final destination*/
                        return true;
                    } //If the Rook is moving in +X only ------------------------------------
                    
                    else if (xCurrent < xNew) {
                        //Loop through each location above the current X value until that value reaches its destination (newX)
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            //Check if the piece in between is null. If it isn't null, return false
                            if (pieces[i][yCurrent] != null) {
                                return false;
                            }
                        }

                        /*If the for loop completes without any non-null places, then return true as there is nothing in between the
  				  	pieces current location and it's final destination*/
                        return true;
                    }
                } 
                
                else {
                    return false;
                }

            //**********************************************************************************************************
            // Handle Bishops
            //**********************************************************************************************************
            case 4:
                //If the Bishops is moving in +X and....
                if (xCurrent < xNew) {
                    //If the Bishop is moving in the +X and +Y
                    if (yCurrent < yNew) {
                        //Prepare a value to store the current Y-value + 1 (which will be the 1st square to check)
                        int j = (yCurrent + 1);

                        //Loop through each location above the current X value until that value reaches its destination (newX) 
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            //Check if the piece in between is null. If it isn't null, return false. 
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            //If it is null, increment the J value to match the incremented I value of the for loop
                            j++;
                        }
                        //If none of the squares in between the current position and new position are equal to anything but null, then return true
                        return true;
                    } //If the Bishop is moving in the +X and -Y
                    
                    else if (yCurrent > yNew) {
                        //Prepare a value to store the current Y-value - 1 (which will be the 1st square to check)
                        int j = (yCurrent - 1);

                        //Loop through each location above the current X value until that value reaches its destination (newX)
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            //Check if the piece in between is null. If it isn't null, return false. 
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            //If it is null, decrease the J value to match the incremented I value of the for loop
                            j--;
                        }
                        //If none of the squares in between the current position and new position are equal to anything but null, then return true
                        return true;
                    }
                } //If the Bishops is moving in -X and....
                else if (xCurrent > xNew) {
                    //If the Bishop is moving in the -X and +Y
                    if (yCurrent < yNew) {
                        //Prepare a value to store the current Y-value + 1 (which will be the 1st square to check)
                        int j = (yCurrent + 1);

                        //Loop through each location below the current X value until that value reaches its destination (newX) 
                        for (int i = (xCurrent - 1); i > xNew; i--) {
                            //Check if the piece in between is null. If it isn't null, return false. 
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            //If it is null, increment the J value to match the incremented I value of the for loop
                            j++;
                        }
                        //If none of the squares in between the current position and new position are equal to anything but null, then return true
                        return true;
                    } //If the Bishop is moving in the -X and -Y
                    else if (yCurrent > yNew) {
                        //Prepare a value to store the current Y-value - 1 (which will be the 1st square to check)
                        int j = (yCurrent - 1);

                        //Loop through each location below the current X value until that value reaches its destination (newX) 
                        for (int i = (xCurrent - 1); i > xNew; i--) {

                            //Check if the piece in between is null. If it isn't null, return false. 
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            //If it is null, decrease the J value to match the incremented I value of the for loop
                            j--;
                        }
                        //If none of the squares in between the current position and new position are equal to anything but null, then return true
                        return true;
                    }
                }

            //**********************************************************************************************************	
            // Handle Queen
            //**********************************************************************************************************
            case 5:
                /*NOTE: The queen can move in both the diagonal and straight lines, similar to the bishop and rook respectively.
    		 * As such, the code for determining if there is anything in between the Queen's destination and current location are the
    		 * same as the Rook and the Bishop combined. First, we check if the Queen is moving in a straight line on the X or Y axis
    		 * and then if not, check if it is moving on a diagonal axis in either of the 4 different directions  */

                //The Queen is moving in the Y axis only
                if (xCurrent == xNew && yCurrent != yNew) {
                    // -Y //
                    if (yCurrent > yNew) {
                        for (int i = (yCurrent - 1); i > yNew; i--) {
                            if (pieces[xCurrent][i] != null) {
                                return false;
                            }
                        }
                        return true;
                    } // +Y //
                    else if (yCurrent < yNew) {
                        for (int i = (yCurrent + 1); i < yNew; i++) {
                            if (pieces[xCurrent][i] != null) {
                                return false;
                            }
                        }
                        return true;
                    }
                } //The Queen must be moving in the X-axis only
                else if (yCurrent == yNew && xCurrent != xNew) {
                    // -X //
                    if (xCurrent > xNew) {
                        for (int i = (xCurrent - 1); i > xNew; i--) {
                            if (pieces[i][yCurrent] != null) {
                                return false;
                            }
                        }
                        return true;
                    } // +X  //
                    else if (xCurrent < xNew) {
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            if (pieces[i][yCurrent] != null) {
                                return false;
                            }
                        }
                        return true;
                    }
                } //The Queen is moving in the +X axis and either +Y or -Y
                else if (xCurrent < xNew) {
                    // +X and +Y //
                    if (yCurrent < yNew) {
                        int j = (yCurrent + 1);
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            j++;
                        }
                        return true;
                    } // +X and -Y //
                    else if (yCurrent > yNew) {
                        int j = (yCurrent - 1);
                        for (int i = (xCurrent + 1); i < xNew; i++) {
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            j--;
                        }
                        return true;
                    }
                } //The Queen is moving in the -X axis and either +Y or -Y
                else if (xCurrent > xNew) {
                    // -X and +Y //
                    if (yCurrent < yNew) {
                        int j = (yCurrent + 1);
                        for (int i = (xCurrent - 1); i > xNew; i--) {
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            j++;
                        }
                        return true;
                    } // -X and -Y //
                    else if (yCurrent > yNew) {
                        int j = (yCurrent - 1);
                        for (int i = (xCurrent - 1); i > xNew; i--) {
                            if (pieces[i][j] != null) {
                                return false;
                            }
                            j--;
                        }
                        return true;
                    }
                } //if none of the cases occurred (should be impossible) return false
                
                else {
                    return false;
                }
            //**********************************************************************************************************
            //Handle King - cannot move more than 1 square, therefore there cannot be anything in between it's current location and final location. 
            //**********************************************************************************************************
            case 6:
                //Simply return true
                return true;

            //If the piece moved was not valid, return false (THIS SHOULDN'T EVER BE CALLED BUT YA NEVER KNOW! It will stop a system crash)
            default:
                return false;
        }
    }
}
