package ca.chess.core;

import ca.chess.core.data.GameData;
import java.io.IOException;

public class GameManager {

    private Scene scene;
    
    private boolean gameRunning;

    public GameManager() {
        //Nothing really needed here.
    }

    public boolean OnCreate() {

        //Create a new scene, which calls its default constructor
        scene = new GameScene(); 
        
        //Make sure that the new game scene was created properly
        if (scene == null) {
            OnDestroy();
            return false;
        }
        
        //Store a reference to the game that will be loaded as the temp Int "gameMode"
        int gameMode = scene.MainMeun();
        
        //Pass that int into the scene.LoadGame() method
        try{
            if(scene.LoadGame(gameMode) == false)
            {
                OnDestroy();
                return false;
            }
        }
        
        //Catch any errors while loading the game
        catch(IOException e)
        {
            System.out.println(e.getMessage() + "Error while loading save game");
        }
        
        //Call OnCreate, if it fails, stop the program.
        if (scene.OnCreate() == false) {
            OnDestroy();         
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

    //Destroy the game if something goes wrong. Is this really needed in Java?
    public void OnDestroy() {

    }

}
