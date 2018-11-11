package ca.chess.core;

import java.util.InputMismatchException;
import java.util.Scanner;

import ca.chess.pieces.Bishop;
import ca.chess.pieces.ChessPiece;
import ca.chess.pieces.King;
import ca.chess.pieces.Knight;
import ca.chess.pieces.Pawn;
import ca.chess.pieces.Queen;
import ca.chess.pieces.Rook;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFrame;


public class GameScene implements Scene {

    //Variables -----------------------------------------
    //This will be the main gameboard, instantiated below
    GameBoard board;

    //Create a new scanner to take in Input
    Scanner scanText = new Scanner(System.in);
    int[] positionInput = new int[4];

    
    //Variable used to save the current players turn
    private boolean player2Turn;

    
    //Variable used to save the current players turn
    private int savedPlayerTurn;

    
    //GUI JFrame that has the board
    private JFrame gameBoardFrame;
    
    
    //Constructor --------------------------------------
    public GameScene(JFrame boardFrame_) {
        gameBoardFrame = boardFrame_;
        savedPlayerTurn = 1;
        player2Turn = false;
    }

    //MainMenu ----------------------------------------------------------------------------------------------------------------
    /*When the game begins, this will be the first thing the user will see. It will ask them to input a value between 1 and 2
          which is used to determine if a new game will be started or the previous game will be loaded from a file
          It will return an int for gameMode, which will be used to either call OnCreate() or LoadGame() */
    @Override
    public int MainMeun() throws InputMismatchException {

        //Reference to the gameMode, default is zero
        int gameMode = -1;

        //Title screen
        System.out.println("Welcome to Chess Sim 2020, a game by Noah and Mike");

        //Forloop to make sure the user inputs the correct values 
        for (int i = 0; i < 1;) {

            System.out.print("\nEnter 0 to start a new game or 1 - 9 to load a previous game: ");

            //Try and allow the user to input an int
            try {
                gameMode = scanText.nextInt();
                //Load a new game if the user input 0
                if (gameMode == 0) {
                    System.out.println("Starting the game!");
                    i++;
                } //Load a previous saved game if the int input was between 1 and 9
                else if (gameMode >= 1 && gameMode <= 9) {
                    System.out.println("Loading game save " + gameMode);
                    i++;
                } //Let the user try 
                else {
                    System.out.println("Error: please make the number is between 0 to 9");
                }
            } //catch any mistakes they make
            catch (InputMismatchException e) {
                System.out.println("Error: " + e + ". Please make you enter a number");
                scanText.next();
            }

            
        }
        return gameMode;
    }

    // Create the game --------------------------------------------------------------------------------------
    //This is where new functionality can be added when things get more complicated 
    @Override
    public boolean OnCreate() {
        return true;
    }

    @Override
    public void Update() {
        /*While the game is running, prompt the current player for an X and Y location of the piece they wish to move, then 
             * the X and Y location of the position they wish to move their piece to. There are several checks that occur here, first 
             * making sure the player input the correct values for an X and Y position twice (1st for the current location and 2nd for the 
             * final destination of their piece). Then, there are 4 major checks that are done to ensure the move is within the rules
              1) Does the 1st pair of coordinates the player input correspond to one of their actual pieces?
              2) Does the 2nd pair of coordinates the player input correspond to a location that their current piece 
                            can logically move to according to how that piece can nmove within the rules of Chess?
              3) Is the location of the 2nd pair of coordinates the player input an empty space that the piece could move to OR is that location
                            currently being held by the other player? (The player cannot move a piece ontop of their own piece)
              4) Is there anything in between the 1st coordinates of their current piece and the 2nd coordinates of the location they
                            wish to move their piece to? (Pieces in chess cannot move through another piece UNLESS it is the KNIGHT, in which case,
                            our check will always return true as they can "hop" over other pieces) */
        //Loop through the following for loop twice, once for player1 and then for player2. Once this loop ends, it restarts and
        //it is player 1's turn again. 
        for (int playerTurn = 1; playerTurn <= 2; playerTurn++) {

            //If the game saved when it was player2's turn, then skip player1s turn only on the first pass through this loop
            if (player2Turn) {
                player2Turn = false;
            } //Otherwise, continue as normal
            else {
                //First, save who's turn it is
                savedPlayerTurn = playerTurn;
                //Let the players know who's turn it is and then ask the player to input the current location 
                System.out.println("It is player " + playerTurn + "'s turn! Press 9 to save the game to a file or,");
                System.out.println("Enter your piece's X, Y location and the X, Y for it's destination:");
                System.out.print("Next: ");
                //Accept 4 different coordinates, checking that the values input are actually Ints and that they are in between 0 and 7
                for (int i = 0; i < 4;) {

                    try {
                        //Get the next input from the user, make sure that it is an int and throw an exception if it is wrong
                        positionInput[i] = scanText.nextInt();

                        //If the input was 9, then prompt the user to save the game
                        if (positionInput[i] == 9) {
                            //Safety check loop for saving
                            for (int j = 0; j < 1;) {
                                //Ask the user where they would like to save the game
                                System.out.print("Select a save slot between 1 to 9: ");

                                try //Take in an int (checking that the input was an int first)
                                {
                                    int fileNum = scanText.nextInt();
                                    //if the int is in the proper range, then save the game
                                    if (fileNum >= 1 && fileNum <= 9) {
                                        try //Try and save the game 
                                        {
                                            this.SaveGame(fileNum);

                                        } //Make sure there were no errors when saving the game
                                        catch (Exception e) {
                                            System.out.println("Save game failed, try again");
                                            scanText.nextInt();
                                        }
                                        j++;
                                    } //If the int was beyond the correct range, let the user try again
                                    else {
                                        System.out.println("Error! Please enter a value between 1 to 9");
                                    }
                                } catch (InputMismatchException e) {
                                    //Warn the player to input the correct value
                                    System.out.println("Error: Please enter an int between 0 and 7");
                                    scanText.nextInt();
                                }

                            }
                            System.out.print("Next: ");
                        } //Make sure the user input a value between 0 to 7
                        else if (positionInput[i] > 7 || positionInput[i] < 0) {
                            System.out.println("Error: Value beyond accepted range. Please enter values between 0 to 7");
                            System.out.print("Next: ");
                        } //If the input value was good, then move the loop forward
                        else {
                            i++;
                        }
                    } //Catch any input mismatches 
                    catch (InputMismatchException e) {
                        //Warn the player to input the correct value
                        System.out.println("Error: " + e + ". Please enter an whole number between 0 and 7");
                        scanText.next();
                        //e.printStackTrace();
                    }
                }

                //CORE MOVEMENT CHECKS -----------------------------------------------------------------------------------------------------------------------------------
                //Check if the location of the piece is accurate
                if (board.pieces[positionInput[0]][positionInput[1]] != null && board.pieces[positionInput[0]][positionInput[1]].playerNum == playerTurn) {
                    //Check if the piece selected can move to the destination
                    if (board.pieces[positionInput[0]][positionInput[1]].canMoveTo(positionInput[2], positionInput[3], playerTurn)) {
                        //Check that the location where the piece wishes to move is either empty or an enemy spot 
                        if (board.pieces[positionInput[2]][positionInput[3]] == null || board.pieces[positionInput[2]][positionInput[3]].playerNum != playerTurn) {
                            //Check if there is anything in between the starting position of the piece and the final destination of that piece
                            if (board.CheckInBetween(positionInput[0], positionInput[1], positionInput[2], positionInput[3], playerTurn)) {
                                //If the move proposed by the user is good, then move that piece! 
                                String name = board.pieces[positionInput[0]][positionInput[1]].getFullName();
                                System.out.println("\nGood move Player " + playerTurn + "! The " + name + " at (" + positionInput[0] + ", " + positionInput[1] + ") will move to (" + positionInput[2] + ", " + positionInput[3] + ")");

                                board.MovePiece(positionInput[0], positionInput[1], positionInput[2], positionInput[3]);
                                board.PrintBoard();
                            } //Print out an error message that informs the player what their mistake was
                            else {
                                System.out.println("\nERROR CODE 4 = Bad move Player " + playerTurn + ", your " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as there are pieces in the way");
                                playerTurn--;
                                board.PrintBoard();
                            }

                        } //Print out an error message that informs the player what their mistake was
                        else {
                            System.out.println("\nERROR CODE 3 = Bad move Player " + playerTurn + ", your " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as you have a " + board.pieces[positionInput[2]][positionInput[3]].getFullName() + " on that square. Try again!");
                            playerTurn--;
                            board.PrintBoard();
                        }

                    } //Print out an error message that informs the player what their mistake was
                    else {
                        System.out.println("\nERROR CODE 2 = Bad move Player " + playerTurn + ", a " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + "). Try again!");
                        playerTurn--;
                        board.PrintBoard();
                    }
                } //Print out an error message that informs the player what their mistake was
                else {
                    System.out.println("\nERROR CODE 1 = Bad move Player " + playerTurn + ", (" + positionInput[0] + ", " + positionInput[1] + ") doesn't correspond to one of your pieces. Try again!");
                    playerTurn--;
                    board.PrintBoard();
                }
            }
        }
    }

    @Override
    public void Render() {
        //When the game runs, first Print the board!
        board.PrintBoard();

        

    }

    //Save Game function. Takes in an int and allows the user to save the game
    @Override
    public void SaveGame(int _fileNum) throws IOException {
        String fileToSave;

        switch (_fileNum) {
            case 1:
                fileToSave = "SaveGame1.txt";
                break;
            case 2:
                fileToSave = "SaveGame2.txt";
                break;
            case 3:
                fileToSave = "SaveGame3.txt";
                break;
            case 4:
                fileToSave = "SaveGame4.txt";
                break;
            case 5:
                fileToSave = "SaveGame5.txt";
                break;
            case 6:
                fileToSave = "SaveGame6.txt";
                break;
            case 7:
                fileToSave = "SaveGame7.txt";
                break;
            case 8:
                fileToSave = "SaveGame8.txt";
                break;
            case 9:
                fileToSave = "SaveGame9.txt";
                break;
            default:
                fileToSave = "SaveGame1.txt";
                break;
        }

        System.out.println("Making a save file!");

        if (!fileToSave.equalsIgnoreCase("")) {

            //Create the fileWriter and pass it the file name that was selected above
            try (BufferedWriter out = new BufferedWriter(new FileWriter(fileToSave))) {

                //Next, create a temp int for the number of pieces on the board used to set the array size when loading from a file
                int numPieces = 0;

                //Loop through the entire board 
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        //Each time a board location returns not null, increase the number of pieces on the board
                        if (board.pieces[i][j] != null) {
                            numPieces += 1;
                        }
                    }
                }

                //Save the number of pieces on the board to the text file, first line of it
                out.write(String.valueOf(numPieces));
                out.newLine();

                //Save the turn order for this game
                out.write(String.valueOf(savedPlayerTurn));
                out.newLine();

                //Loop through the entire board
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {

                        //If the piece at that board location is not null, then save the pieces data to the text file
                        if (board.pieces[j][i] != null) {

                            //Write the type of piece to the text file
                            out.write(String.valueOf(board.pieces[j][i].getPieceType()));
                            out.write(" ");

                            //Write the type of piece to the text file
                            out.write(String.valueOf(board.pieces[j][i].playerNum));
                            out.write(" ");

                            //Write the type of piece to the text file
                            out.write(String.valueOf(board.pieces[j][i].getPosition()[0]));
                            out.write(" ");

                            //Write the type of piece to the text file
                            out.write(String.valueOf(board.pieces[j][i].getPosition()[1]));
                            out.newLine();
                        }
                    }
                }
                //Once the file has finished saving, close the fileWriter
                out.close();
                System.out.println("File Created!");
            } //Catch any errors if something goes wrong
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Game Saved!");
        }
    }

    //Load the game. This can either load a new game (int of 0) or 1 of 9 saved files
    @Override
    public boolean LoadGame(int savedFile) throws IOException {

        String fileToLoad;

        //Switch on the saved File, which will select which saved game will be loaded
        switch (savedFile) {
            case 0:
                fileToLoad = "DefaultGame.txt";
                break;
            case 1:
                fileToLoad = "SaveGame1.txt";
                break;
            case 2:
                fileToLoad = "SaveGame2.txt";
                break;
            case 3:
                fileToLoad = "SaveGame3.txt";
                break;
            case 4:
                fileToLoad = "SaveGame4.txt";
                break;
            case 5:
                fileToLoad = "SaveGame5.txt";
                break;
            case 6:
                fileToLoad = "SaveGame6.txt";
                break;
            case 7:
                fileToLoad = "SaveGame7.txt";
                break;
            case 8:
                fileToLoad = "SaveGame8.txt";
                break;
            case 9:
                fileToLoad = "SaveGame9.txt";
                break;
            default:
                fileToLoad = "DefaultGame.txt";
                break;
        }

        try (BufferedReader in = new BufferedReader(new FileReader(fileToLoad))) {

            //Check to see how many pieces were on the board for each player from the previous game 
            //Read in the 1st line of code, which will be the number of pieces to create
            int numPieces = Integer.parseInt(in.readLine());

            //DEBUG ONLY
            //System.out.println(numPieces);
            //and use those to create the arrays for each player
            ChessPiece[] loadedPieces = new ChessPiece[numPieces];

            //Next, Load player Turn and set the boolean "Player2Turn" which is used to skip player1s turn
            savedPlayerTurn = Integer.parseInt(in.readLine());
            System.out.println("Player Turn: " + savedPlayerTurn);

            //Set the bool Player2Turn, which is used to load the game to a state where it is player2s turn
            switch(savedPlayerTurn) {
                case 1:
                    player2Turn = false;
                    break;
                case 2:
                    player2Turn = true;
                    break;
                default:
                    break;
            }
            
            System.out.println("Player Turn: " + player2Turn);

            //Setup an iterator for the while loop
            int pieceNum = 0;
            String line;

            //Load the textfile into the game and save those values into the Pieces Arrray
            while ((line = in.readLine()) != null) {
                int pieceType = Integer.parseInt(line.substring(0, 1));
                int playerNum = Integer.parseInt(line.substring(2, 3));
                int pieceX = Integer.parseInt(line.substring(4, 5));
                int pieceY = Integer.parseInt(line.substring(6, 7));
                //System.out.println(pieceType + " " + playerNum + " " + pieceX + " " + pieceY + " ");

                //Switch on the piece type, which will allow the game to add the different pieces required 
                switch (pieceType) {
                    //Add a pawn
                    case 1:
                        loadedPieces[pieceNum] = new Pawn(pieceX, pieceY, playerNum);
                        break;

                    //Add a /Knight
                    case 2:
                        loadedPieces[pieceNum] = new Knight(pieceX, pieceY, playerNum);
                        break;

                    //Add a Rook 
                    case 3:
                        loadedPieces[pieceNum] = new Rook(pieceX, pieceY, playerNum);
                        break;

                    //Add a Bishop 
                    case 4:
                        loadedPieces[pieceNum] = new Bishop(pieceX, pieceY, playerNum);
                        break;

                    //Add a queen
                    case 5:
                        loadedPieces[pieceNum] = new Queen(pieceX, pieceY, playerNum);
                        break;

                    //Add a king
                    case 6:
                        loadedPieces[pieceNum] = new King(pieceX, pieceY, playerNum);
                        break;
                }
                pieceNum++;
            }

            //Create the board using the array of Pieces
            board = new GameBoard(loadedPieces);
        } 
        
        catch (Exception e) {
            //If anything went wrong, print out some error messages and return false
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
