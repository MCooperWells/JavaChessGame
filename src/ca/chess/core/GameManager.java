package ca.chess.core;

import ca.chess.core.data.GameData;

public class GameManager {

    private Window window;
    private Scene scene;
    private GameData data;
    
    private boolean gameRunning;

    public GameManager() {
        //Nothing really needed here.
    }

    public boolean OnCreate() {

        //First, make a new window for the game to be rendered to and check that it was made properly
        window = new Window();
        if (window == null) {
            OnDestroy();
            return false;
        }

        //Then, call that windows create function, checking if it was created successfully
        if (window.OnCreate(1280, 920) == false) {
            OnDestroy();
            return false;
        }

        //Create a new scene, passing it the window and the game data
        scene = new GameScene(window, null); //TODO data.LoadGame()
        if (scene == null) {
            OnDestroy();
            return false;
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
            scene.Update();
            scene.Render();
        }

    }

    //Destroy the game if something goes wrong. Is this really needed in Java?
    public void OnDestroy() {

    }

}
