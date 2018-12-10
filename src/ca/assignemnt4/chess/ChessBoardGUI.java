package ca.assignemnt4.chess;

import ca.assignment4.Pieces.Bishop;
import ca.assignment4.Pieces.ChessPiece;
import ca.assignment4.Pieces.King;
import ca.assignment4.Pieces.Knight;
import ca.assignment4.Pieces.Pawn;
import ca.assignment4.Pieces.Queen;
import ca.assignment4.Pieces.Rook;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;

import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

public class ChessBoardGUI extends JPanel {
    
    //Database stuff
    MysqlDataSource dataSource;
    ChessDataBase dbCon;
    
    //Login Screen stuff -------------------------------------------------------
    
    //The main panel for the board
    private JPanel insidePanel;
    private JPanel sidePanel;
     
    //The start button of the game
    private JButton startButton;
    
    //Make a text field called Narrator that will tell the user if they made any mistakes
    JTextField textLog = new JTextField("Narrator");
    
    JLabel blankEast = new JLabel("");
    JLabel blankWest = new JLabel("");
    
    //Labels and Input fields for the player
    JLabel nameInfo = new JLabel("Name");
    JLabel emailInfo = new JLabel("Email");
    JLabel passwordInfo = new JLabel("Password");
    
    JTextField nameTextField = new JTextField("");    
    JTextField emailTextField = new JTextField("");    
    JTextField passwordTextField = new JTextField("");
    
    //Player information for the database
    int player1ID;
    int player1Wins;
    int player1Loses;
    String player1Email;
    String player1Name;
    int player2ID;
    int player2Wins;
    int player2Loses;
    String player2Name;
    
    //Game ID
    int gameID;
    
    //Variables -----------------------------------------
    //This will be the main gameboard, instantiated below
    GameBoard board;

    //Boolean used to determine if the input was from the first move (true) or the second part of the move (false)
    boolean firstMove = true;
    boolean gameRunning = false;
    boolean gameOver = false;
    
    //Variable used to save the current players turn
    private int playerTurn;

    //Setup the score board display for wins
    JLabel ScoreDisplayWins = new JLabel("Wins");
    JLabel player1DisplayWins = new JLabel("Player 1: ");
    JLabel player2DisplayWins = new JLabel("Player 2: ");
    JLabel player1ScoreWins = new JLabel("");    
    JLabel player2ScoreWins = new JLabel(""); 
    
    JLabel spaceLabel = new JLabel("");
    
    //Setup the score board display loses
    JLabel ScoreDisplayLoses = new JLabel("Loses");
    JLabel player1DisplayLoses = new JLabel("Player 1: ");
    JLabel player2DisplayLoses = new JLabel("Player 2: ");
    JLabel player1ScoreLoses = new JLabel("");    
    JLabel player2ScoreLoses = new JLabel(""); 
    
    //Create a new scanner to take in Input
    Scanner scanText = new Scanner(System.in);
    int[] positionInput = new int[4];
    
    //The array of buttons for the game
    private JButton button[][] = new JButton[8][8];
    
    //Create a JToolBar that will contain game options.
    private JToolBar tools = new JToolBar();
    //10 different buttons for loading games. Button 0 will load the default mode and 1-9 will load saved games
    private JButton[] LoadButtons = new JButton[9];
    private JButton[] SaveButtons = new JButton[9];
    private JButton newGameButton;
    private JButton quitButton;
    
    //Constructor
    public ChessBoardGUI() {
       
        //Call the initialize components method
        if(initComponents1(1))
        {
            System.out.println("Login screen creation was successful");
        }
        else
        {
            System.out.println("Error creating login screen");
        }
    }

    // -------------------------------
    //Initializing the components --------------------------------------------------------------
    //--------------------------------
    private boolean initComponents1(int playerNum) {
        
        //Clear out the JFrame
        this.removeAll();
        this.revalidate();
        this.repaint();
        
        //Create a nice border for the login screen
        Border thickBorder = new LineBorder(Color.DARK_GRAY, 30);

        //Set the layout to the be border layout
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);
        borderLayout.setHgap(100);        
        borderLayout.setVgap(10);

        //Add the text window to the bottom of the game and make the text uneditable
        textLog.setPreferredSize(new Dimension(200, 200));
        textLog.setFont(new Font("Arial", Font.BOLD, 25));
        textLog.setForeground(Color.BLACK);
        textLog.setEditable(false);
        textLog.setBorder(thickBorder);
        textLog.setHorizontalAlignment(JTextField.CENTER);
        this.add(textLog, BorderLayout.NORTH);
        if(playerNum == 1)
            textLog.setText("Welcome to Chess Sim 2020: Player 1 sign in!");
        else if(playerNum == 2)
            textLog.setText("Welcome to Chess Sim 2020: Player 2 sign in!");
    
        this.add(blankEast, BorderLayout.EAST);        
        this.add(blankWest, BorderLayout.WEST);

        
        //Create the inside Panel Frame
        insidePanel = new JPanel();
        
        //Set the inside panel layout to be an 8x8 grid
        insidePanel.setLayout(new GridLayout(6, 1));

        //Add that grid to the center of the layout
        this.add(insidePanel, BorderLayout.CENTER);
        
        //Create a new font for the input fields of the login screen
        Font inputFont = new Font("Arial", Font.BOLD, 25);
        
        //Add the name text field and label, as well as set the font and color
        nameTextField.setPreferredSize(new Dimension(200, 50));
        nameTextField.setFont(inputFont);
        nameTextField.setForeground(Color.BLACK);
        nameInfo.setFont(inputFont);
        nameInfo.setForeground(Color.BLACK);
        insidePanel.add(nameInfo);
        insidePanel.add(nameTextField);
        
        //Add the email text field and label, as well as set the font and color
        emailTextField.setPreferredSize(new Dimension(200, 50));
        emailTextField.setFont(inputFont);
        emailTextField.setForeground(Color.BLACK);
        emailInfo.setFont(inputFont);
        emailInfo.setForeground(Color.BLACK);
        insidePanel.add(emailInfo);
        insidePanel.add(emailTextField);
        
        //Add the password text field and label, as well as set the font and color
        passwordTextField.setPreferredSize(new Dimension(200, 50));
        passwordTextField.setFont(inputFont);
        passwordTextField.setForeground(Color.BLACK);
        passwordInfo.setFont(inputFont);
        passwordInfo.setForeground(Color.BLACK);
        insidePanel.add(passwordInfo);
        insidePanel.add(passwordTextField);

        //Start button initialization
        startButton = new JButton("");
        
        startButton.setBorder(thickBorder);
        startButton.setForeground(Color.BLACK);
        startButton.setFont(new Font("Arial", Font.BOLD, 30));
        startButton.setPreferredSize(new Dimension(80, 200));
        
        if(playerNum == 1)
            startButton.setText("Player 1 Ready!");
        
        else if(playerNum == 2)
            startButton.setText("Player 2 Ready!");
        
        this.add(startButton, BorderLayout.SOUTH);
        
        if(playerNum == 1)
        {
            //Database Connection ------------------------------------------
            if(!ConnectToDataBase())
            {
                return false;
            }

            //If there is no table, create one. If one exists, then do not create one
            if(CreateTable())
            {
                System.out.println("Connected to database with existing table");
            }
            else 
            {
                System.out.println("Connected to a database with a table. One has been created");
            }
        }

        //Setup the button press for the start Button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get the values the player has input into the login screen
                String tempName = nameTextField.getText();
                String tempEmail = emailTextField.getText();
                String tempPassword = passwordTextField.getText();
                
                //Check to make sure the user input a valid name, email and password
                if(tempName.equalsIgnoreCase(""))
                    textLog.setText("Please enter a name before staring the game");
                
                else if(tempEmail.equalsIgnoreCase(""))
                    textLog.setText("Please enter an email before staring the game");
                
                else if(tempPassword.equalsIgnoreCase(""))
                    textLog.setText("Please enter a password name before staring the game");
                
                else
                {
                    //Start checking the database
                    if(playerNum == 1 && InsertPlayerInformation(tempName, tempEmail, tempPassword))
                    {
                        player1Email = tempEmail;
                        player1Name = tempName;
                        player1ID = dbCon.GetPlayerID(tempEmail);
                        player1Wins = dbCon.getPlayerWins(player1ID);
                        player1Loses = dbCon.getPlayerLoses(player1ID);
                        initComponents1(2);
                    }
                    //Check that player1 isn't equal to player 2
                    else if(!player1Email.equals(tempEmail) && playerNum == 2)
                    {
                        if(InsertPlayerInformation(tempName, tempEmail, tempPassword))
                        {
                            player2ID = dbCon.GetPlayerID(tempEmail);
                            player2Name = tempName;
                            player2Wins = dbCon.getPlayerWins(player2ID);
                            player2Loses = dbCon.getPlayerLoses(player2ID);
                            SaveGameState(player1Name, player2Name);
                            initComponents2();
                        }
                    }
                    else
                    {
                        textLog.setText("Player 2 cannot be the same as player 1");
                    }
                }
            }
        });
        return true;
    }
    
    private boolean ConnectToDataBase() {
        try{        
            //The name must match the schema name in mySQL database
            String url = "jdbc:mysql://localhost:3306/chess";

            //This import is from the Jar File
            dataSource = new MysqlDataSource();

            //Set the URL of the data Source (The location of our schema in mySQL)
            dataSource.setURL(url);
            dataSource.setUser("root");
            dataSource.setPassword("RunningDragon3!");
            
            //Create DataBase
            dbCon = new ChessDataBase(dataSource);
            return true;
        }
        
        //Catch statement
        catch (SQLException ex){
            System.out.println("ConnectToDataBase() Error " + ex.getErrorCode() + " " + ex.getMessage());
            return false;
        }
    }
    
    private boolean SaveGameState(String p1Name, String p2Name)
    {
        gameID = -1;
         try{  //Try and add the game info to the table    
            gameID = dbCon.AddGameInfo(p1Name, p2Name);
            return (gameID >= 0);
        }
        //Catch statement
        catch (SQLException ex){
            System.out.println("SaveGameState() Error " + ex.getErrorCode() + " " + ex.getMessage());
            return false;
        }
    }
    
    //Function that will attempt to create a table in the database
    private boolean CreateTable() {
        try{  //Try and create the table    
            dbCon.CreateTable();
            return true;
        }
        //Catch statement
        catch (SQLException ex){
            System.out.println("CreateTable() Error " + ex.getErrorCode() + " " + ex.getMessage());
            return false;
        }
    }
    
    private boolean InsertPlayerInformation(String userName, String userEmail, String userPassword) {
        //Try and insert the player information into the table.
        try{        
            if(dbCon.PasswordCheck(userName, userEmail, userPassword))
            {
                //If this returns true, the player is logged in and they can play the game
                System.out.println("Player Logged in!");
                return true;
            }
            //If the password check fails, then they can try again
            else {
                System.out.println("Please try again");
                return false;
            }
        }
        //Catch statement if the password check fails
        catch (SQLException ex){
            System.out.println("InsertPlayerInformation() Error " + ex.getErrorCode() + " " + ex.getMessage());
            return false;
        }
    }
    
    // -------------------------------
    //Initializing the components --------------------------------------------------------------
    //--------------------------------
    private void initComponents2() {

        //When the game starts, remove all the old stuff from the JFrame and load in the new stuff
        this.removeAll();
        this.revalidate();
        this.repaint();
        
        //Create the inside Panel Frame
        insidePanel = new JPanel();
           
        //Set the layout to the be border layout
        this.setLayout(new BorderLayout());

        //Set the inside panel layout to be an 8x8 grid
        insidePanel.setLayout(new GridLayout(8, 8));

        //Add that grid to the center of the layout
        this.add(insidePanel, BorderLayout.CENTER);
        
        //Add the side bar
        sidePanel = new JPanel();
        sidePanel.setLayout(new GridLayout(11, 1));
        sidePanel.setBorder(BorderFactory.createEmptyBorder(80,10,80,10)); 
        this.add(sidePanel, BorderLayout.EAST);
        
        //Setup the font and spacing of the side bars
        Font inputFont = new Font("Arial", Font.BOLD, 20);
        
        //WINS ------------------------------------------------
        ScoreDisplayWins.setHorizontalAlignment(JLabel.CENTER);
        ScoreDisplayWins.setFont(inputFont);
        ScoreDisplayWins.setForeground(Color.BLACK);
        
        player1DisplayWins.setHorizontalAlignment(JLabel.CENTER);
        player1DisplayWins.setFont(inputFont);
        player1DisplayWins.setForeground(Color.BLACK);
        
        player1ScoreWins.setHorizontalAlignment(JLabel.CENTER);
        player1ScoreWins.setFont(inputFont);
        player1ScoreWins.setForeground(Color.BLACK);
        player1ScoreWins.setText(String.valueOf(player1Wins));
        
        player2DisplayWins.setHorizontalAlignment(JLabel.CENTER);
        player2DisplayWins.setFont(inputFont);
        player2DisplayWins.setForeground(Color.BLACK);
        
        player2ScoreWins.setHorizontalAlignment(JLabel.CENTER);
        player2ScoreWins.setFont(inputFont);
        player2ScoreWins.setForeground(Color.BLACK);
        player2ScoreWins.setText(String.valueOf(player2Wins));
        
        sidePanel.add(ScoreDisplayWins);
        sidePanel.add(player1DisplayWins);
        sidePanel.add(player1ScoreWins);
        sidePanel.add(player2DisplayWins);
        sidePanel.add(player2ScoreWins);
        sidePanel.add(spaceLabel);

        //Loses ----------------------------------------------
        ScoreDisplayLoses.setHorizontalAlignment(JLabel.CENTER);
        ScoreDisplayLoses.setFont(inputFont);
        ScoreDisplayLoses.setForeground(Color.BLACK);
        
        player1DisplayLoses.setHorizontalAlignment(JLabel.CENTER);
        player1DisplayLoses.setFont(inputFont);
        player1DisplayLoses.setForeground(Color.BLACK);
        
        player1ScoreLoses.setHorizontalAlignment(JLabel.CENTER);
        player1ScoreLoses.setFont(inputFont);
        player1ScoreLoses.setForeground(Color.BLACK);
        player1ScoreLoses.setText(String.valueOf(player1Loses));
        
        player2DisplayLoses.setHorizontalAlignment(JLabel.CENTER);
        player2DisplayLoses.setFont(inputFont);
        player2DisplayLoses.setForeground(Color.BLACK);
        
        player2ScoreLoses.setHorizontalAlignment(JLabel.CENTER);
        player2ScoreLoses.setFont(inputFont);
        player2ScoreLoses.setForeground(Color.BLACK);
        player2ScoreLoses.setText(String.valueOf(player2Loses));

        sidePanel.add(ScoreDisplayLoses);
        sidePanel.add(player1DisplayLoses);
        sidePanel.add(player1ScoreLoses);
        sidePanel.add(player2DisplayLoses);
        sidePanel.add(player2ScoreLoses);
        
        //Add the text window to the bottom of the game and make the text uneditable
        textLog.setPreferredSize(new Dimension(200, 50));
        textLog.setFont(new Font("Arial", Font.BOLD, 15));
        textLog.setEditable(false);

        //Add the text window to the bottom of the game and make the text uneditable
        textLog.setForeground(Color.BLACK);
        //Create a nice border for the login screen
        Border thinBorder = new LineBorder(Color.DARK_GRAY, 5);
        textLog.setBorder(thinBorder);
        textLog.setHorizontalAlignment(JTextField.LEFT);
        this.add(textLog, BorderLayout.SOUTH);

        
        
        /*  private JButton[] LoadButtons = new JButton[9];
            private JButton[] SaveButtons = new JButton[9];
            private JButton newGameButton;
            private JButton quitButton;*/
        
        tools.setFloatable(false);
        tools.setLayout(new GridLayout(2, 10));
        this.add(tools, BorderLayout.NORTH);
        
        //Setup the new Game button 
        newGameButton = new JButton("New Game");
        tools.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //This button will always act as a new game button and will by default load a new game
                try { 
                        LoadGame(0);
                } 
                catch (IOException e1) {
                    System.out.println(e1.getMessage());
                }
            }
        });
        
        //Adds loading game buttons to the JToolbar
        for (int i = 1; i < LoadButtons.length; i++) {
            //Give the button some text.
            LoadButtons[i] = new JButton("Load " + i);
            
            //Assign the button a client property
            LoadButtons[i].putClientProperty("buttonNum", i);
            tools.add(LoadButtons[i]);
            
            //Add the action listener to the tool bar
            LoadButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    try { 
                            LoadGame((int)btn.getClientProperty("buttonNum"));
                    } 
                    catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            });
        }
        
        //Setup the new quit game button
        quitButton = new JButton("Quit Game");
        tools.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        //Save game buttons
        //Adds loading game buttons to the JToolbar
        for (int i = 1; i < SaveButtons.length; i++) {
            //Give the button some text.
            SaveButtons[i] = new JButton("Save " + i);
            
            //Assign the button a client property
            SaveButtons[i].putClientProperty("buttonNum", i);
            tools.add(SaveButtons[i]);
            
            //Add the action listener to the tool bar
            SaveButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton btn = (JButton) e.getSource();
                    try { 
                        if(gameOver)
                        {
                            textLog.setText("The game has ended. You cannot save the game");
                        }
                        else {
                            SaveGame((int)btn.getClientProperty("buttonNum"));
                        }
                    } 
                    catch (IOException e1) {
                        System.out.println(e1.getMessage());
                    }
                }
            });
        }
        
        
        //starting from the top row (order that the buttons get added), the i is equal to the X-axis
        for (int i = 7; i > -1; i--) {
            //starting from the far right column (order that the buttons get added), the j is equal to the Y-axis
            for (int j = 0; j < 8; j++) {
                //Create the button in the array, with the appropriate name
                button[j][i] = new JButton("");

                //Add the button to the panel
                insidePanel.add(button[j][i]);

                //Add client properties to the button so that it knows which button it is
                button[j][i].putClientProperty("column", j);
                button[j][i].putClientProperty("row", i);
                button[j][i].addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        //Get the button in question and using the client properties, call the UpdateMove passing the x and y value
                        JButton btn = (JButton) e.getSource();
                        int y = (int) btn.getClientProperty("row");
                        int x = (int) btn.getClientProperty("column");
                        updateMove(x, y);
                    }
                });
            }
        }
        textLog.setText("Select a game to load or start a new game");
    }

    //--------------------------------------------------------
    //Update the move of the player when they press a button 
    //--------------------------------------------------------
    private void updateMove(int xVal_, int yVal_) {
        if(!gameOver)
        {
            if (firstMove) {
                positionInput[0] = xVal_;
                positionInput[1] = yVal_;
                if (board.pieces[positionInput[0]][positionInput[1]] != null && board.pieces[positionInput[0]][positionInput[1]].playerNum == playerTurn) {
                    firstMove = false;
                    textLog.setText("Player " + playerTurn + " has selected a " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " on square: (" + xVal_ + ", " + yVal_ + ")");
                }
                else {
                textLog.setText("Wrong piece player " + playerTurn + ", Square (" + positionInput[0] + ", " + positionInput[1] + ") doesn't correspond to one of your pieces. Try again Player " + playerTurn);
                }
            } 
            else {
                positionInput[2] = xVal_;
                positionInput[3] = yVal_;
                firstMove = true;
                CheckMove();
                //System.out.println("Move was from [" + positionInput[0] + ", " + positionInput[1] + "] to [" + positionInput[2] + ", " + positionInput[3] + "]");
            }
        }
    }
    
    //----------------------------
    //CORE MOVEMENT CHECKS -----------------------------------------------------------------------------------------------------------------------------------
    //----------------------------
    private void CheckMove() {
        //Check if the piece selected can move to the destination
        if (board.pieces[positionInput[0]][positionInput[1]].canMoveTo(positionInput[2], positionInput[3], playerTurn)) {
             //Check if the piece is a pawn as their move and attack movements are different
            if (board.pieces[positionInput[0]][positionInput[1]].getPieceType() == 1) {
                //If the pawn moved up in a straight line or in a diagonal to attack, check the following
                if (positionInput[0] == positionInput[2]) {
                    if (board.pieces[positionInput[2]][positionInput[3]] == null) {
                        UpdateBoard(positionInput[0], positionInput[1], positionInput[2], positionInput[3]);
                    } 
                    else {
                        textLog.setText("Bad move Player " + playerTurn + ", your Pawn can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as there is a piece in the way. Try Again Player " + playerTurn);
                    }
                } //If the pawn moved diagonally
                else if (positionInput[0] != positionInput[2]) {
                    if (board.pieces[positionInput[2]][positionInput[3]] != null && board.pieces[positionInput[2]][positionInput[3]].playerNum != playerTurn) {
                        UpdateBoard(positionInput[0], positionInput[1], positionInput[2], positionInput[3]);
                    } 
                    else {
                        textLog.setText("Bad move Player " + playerTurn + ", your Pawn can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as there is no enemy there. Try Again Player " + playerTurn);
                    }
                }

            }

            //Continue as normal if it's not a pawn
            else
            {
                //Check that the location where the piece wishes to move is either empty or an enemy spot
                if (board.pieces[positionInput[2]][positionInput[3]] == null || board.pieces[positionInput[2]][positionInput[3]].playerNum != playerTurn) {
                    //Check if there is anything in between the starting position of the piece and the final destination of that piece
                    if (board.CheckInBetween(positionInput[0], positionInput[1], positionInput[2], positionInput[3], playerTurn)) {

                        UpdateBoard(positionInput[0], positionInput[1], positionInput[2], positionInput[3]);

                    } //Print out an error message that informs the player what their mistake was
                    else {
                        textLog.setText("Bad move Player " + playerTurn + ", your " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as there are pieces in the way. Try Again Player " + playerTurn);
                    }

                } //Print out an error message that informs the player what their mistake was
                else {
                    textLog.setText("Bad move Player " + playerTurn + ", your " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + ") as you have a " + board.pieces[positionInput[2]][positionInput[3]].getFullName() + " on that square. Try again Player " + playerTurn);
                }
            }
        } //Print out an error message that informs the player what their mistake was
        else {
            textLog.setText("Bad move Player " + playerTurn + ", your " + board.pieces[positionInput[0]][positionInput[1]].getFullName() + " can't move to (" + positionInput[2] + ", " + positionInput[3] + "). Try again Player " + playerTurn);
        }
    }

    private void LoadGame(int savedFile) throws IOException {
        ClearBoard();
        
        //String name of the file to be loaded
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

            //Set the game ID to be equal to the savead game ID
            if(savedFile != 0)
                gameID = Integer.parseInt(in.readLine());
            
            //Check to see how many pieces were on the board for each player from the previous game 
            //Read in the 1st line of code, which will be the number of pieces to create
            int numPieces = Integer.parseInt(in.readLine());

            //DEBUG ONLY
            //System.out.println(numPieces);
            //and use those to create the arrays for each player
            ChessPiece[] loadedPieces = new ChessPiece[numPieces];

            //Next, Load player Turn and set the boolean "Player2Turn" which is used to skip player1s turn
            playerTurn = Integer.parseInt(in.readLine());
            gameOver = false;
            textLog.setText("Game starting with Player " + playerTurn + " going first");

            //Setup an iterator for the while loop
            int pieceNum = 0;
            String line;

            //Load the textfile into the game and save those values into the Pieces Arrray
            while ((line = in.readLine()) != null) {
                int pieceType = Integer.parseInt(line.substring(0, 1));
                int playerNum = Integer.parseInt(line.substring(2, 3));
                int pieceX = Integer.parseInt(line.substring(4, 5));
                int pieceY = Integer.parseInt(line.substring(6, 7));
                String name = "";
                //System.out.println(pieceType + " " + playerNum + " " + pieceX + " " + pieceY + " ");
                try
                {
                    
                    //Switch on the piece type, which will allow the game to add the different pieces required 
                    switch (pieceType) {
                        //Add a pawn
                        case 1:
                            loadedPieces[pieceNum] = new Pawn(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhitePawn.png";
                            } 
                            else {
                                name = "/Pictures/BlackPawn.png";
                            }
                            break;

                        //Add a /Knight
                        case 2:
                            loadedPieces[pieceNum] = new Knight(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhiteHorse.png";
                            } 
                            else {
                                name = "/Pictures/BlackHorse.png";
                            }
                            break;

                        //Add a Rook 
                        case 3:
                            loadedPieces[pieceNum] = new Rook(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhiteRook.png";
                            } 
                            else {
                                name = "/Pictures/BlackRook.png";
                            }
                            break;

                        //Add a Bishop 
                        case 4:
                            loadedPieces[pieceNum] = new Bishop(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhiteBishop.png";
                            } 
                            else {
                                name = "/Pictures/BlackBishop.png";
                            }
                            break;

                        //Add a queen
                        case 5:
                            loadedPieces[pieceNum] = new Queen(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhiteQueen.png";
                            } 
                            else {
                                name = "/Pictures/BlackQueen.png";
                            }
                            break;

                        //Add a king
                        case 6:
                            loadedPieces[pieceNum] = new King(pieceX, pieceY, playerNum);
                            //Set the path name of the picture to be added to the variable "name"
                            if (playerNum == 1) {
                                name = "/Pictures/WhiteKing.png";
                            } 
                            else {
                                name = "/Pictures/BlackKing.png";
                            }
                            break;
                    }
                    //Next, load the image to the correct button
                    if (!name.equalsIgnoreCase("")) {
                        //Get the picture from the pictures folder
                        Image tempImg = ImageIO.read(getClass().getResource(name));

                        //Add that image to the button
                        button[pieceX][pieceY].setIcon(new ImageIcon(tempImg));
                    }
                
                } 
                catch (Exception e) {
                    System.out.println(e.getMessage() + "  Error loading image!");
                }
                pieceNum++;
            }

            //Create the board using the array of Pieces
            board = new GameBoard(loadedPieces);
        } catch (Exception e) {
            //If anything went wrong, print out some error messages and return false
            System.out.println(e.getMessage() + "Error message! Game failed to load");
        }
        //This should be moved.
        //Changes the text inside the buttons once the game is finished loading.
        gameRunning = true;
    }

    private void SaveGame(int _fileNum) throws IOException {
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
                
                //Save the game ID 
                out.write(String.valueOf(gameID));
                out.newLine();
                
                //Save the number of pieces on the board to the text file, first line of it
                out.write(String.valueOf(numPieces));
                out.newLine();

                //Save the turn order for this game
                out.write(String.valueOf(playerTurn));
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
            } 
            //Catch any errors if something goes wrong
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Game Saved!");
            textLog.setText("Game Saved!");
        }
    }
    
    private void ClearBoard() {
        //starting from the top row (order that the buttons get added), the i is equal to the X-axis
        for (int i = 7; i > -1; i--) {
            //starting from the far right column (order that the buttons get added), the j is equal to the Y-axis
            for (int j = 0; j < 8; j++) {
                button[j][i].setIcon(null);
            }
        }
    }
    
    private void UpdateBoard(int X1, int Y1, int X2, int Y2) {
        //If the move proposed by the user is good, then move that piece! 
        String name = board.pieces[positionInput[0]][positionInput[1]].getFullName();
        
        if(playerTurn == 1){
            textLog.setText("Good move Player 1! The " + name + " at (" + positionInput[0] + ", " + positionInput[1] + ") will move to (" + positionInput[2] + ", " + positionInput[3] + "). It's now Player 2's turn!");
        }
        else{
             textLog.setText("Good move Player 2! The " + name + " at (" + positionInput[0] + ", " + positionInput[1] + ") will move to (" + positionInput[2] + ", " + positionInput[3] + "). It's now Player 1's turn!");
        }
        board.MovePiece(positionInput[0], positionInput[1], positionInput[2], positionInput[3]);
        
        //After the move is complete, check to see if that player wins!
        if (playerTurn == 1) {
            if (board.CheckForWin(2)) {
                textLog.setText("The King has fallen! Player " + playerTurn + " Wins!");
                //Update the database that player1 won
                player1Wins += 1;
                System.out.println("Current player 1 ID: " + player1ID);
                dbCon.updatePlayerScore(player1ID, player1Wins, player1Loses);
                player2Loses += 1;
                System.out.println("Current player 2 ID: " + player2ID);
                dbCon.updatePlayerScore(player2ID, player2Wins, player2Loses);
                dbCon.updateGameScore(gameID, 1);
                gameOver = true;
            }
            playerTurn = 2;
        } else if (playerTurn == 2) {
            if (board.CheckForWin(1)) {
                textLog.setText("The King has fallen! Player " + playerTurn + " Wins!");
                player1Loses += 1;
                dbCon.updatePlayerScore(player1ID, player1Wins, player1Loses );
                player2Wins += 1;
                dbCon.updatePlayerScore(player2ID, player2Wins, player2Loses);
                dbCon.updateGameScore(gameID, 2);
                gameOver = true;
            }
            playerTurn = 1;
        }
        player1ScoreWins.setText(String.valueOf(player1Wins));
        player2ScoreWins.setText(String.valueOf(player2Wins));
        player1ScoreLoses.setText(String.valueOf(player1Loses));        
        player2ScoreLoses.setText(String.valueOf(player2Loses));
        button[X2][Y2].setIcon(button[X1][Y1].getIcon());
        button[X1][Y1].setIcon(null);
    }
}
