package ca.chess.core;

import ca.chess.core.data.GameData;
import java.io.IOException;
import javax.swing.JFrame;

public class GameManager {

    private Scene scene;
    
    private boolean gameRunning;

    public GameManager() {
        //Nothing really needed here.
    }

    public boolean OnCreate() {
        
        //Load up the gameFrame
        JFrame gameFrame = new JFrame("Chess Game");
        
        //Make sure that the frame was loaded properly
        if(gameFrame == null)
        {
            return false;
        }
         //Setup the Content Fane
        gameFrame.setContentPane(new ChessBoardGUI());
        
        //Setup the defaults for the frame
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setBounds(200, 200, 800, 900);
        gameFrame.setResizable(false);
        
        //Create a new scene, which calls its default constructor and takes in one JFrame
        scene = new GameScene(gameFrame); 
        
        //Make sure that the new game scene was created properly
        if (scene == null) {
            return false;
        }
        
        //Store a reference to the game that will be loaded as the temp Int "gameMode"
        int gameMode = scene.MainMeun();
        
       
        try //Pass that int into the scene.LoadGame() method
        {  
            if(scene.LoadGame(gameMode) == false)
            {
                return false;
            }
        }
        catch(IOException e) //Catch any errors while loading the game
        {
            System.out.println(e.getMessage() + "Error while loading save game");
        }
        
        //Call OnCreate, if it fails, stop the program.
        if (scene.OnCreate() == false) {      
            return false;
        }
        gameRunning = true;
        return true;
    }

    public void Run() {

        //Master loop
        while (gameRunning) {
            scene.Render();
            scene.Update();
        }

    }
}
