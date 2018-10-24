package ca.chess.core;

public class GameManager {

    private Window window;
    private Scene scene;
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

        //Create a new scene, passing it the window created before
        scene = new GameScene(window);
        if (scene == null) {
            OnDestroy();
            return false;
        }

        //Allow the user to load a previous game or start a new one
        int mode = scene.MainMeun();

        //Switch on the mode, either making a new game or loaded an older one
        switch (mode) {
            case 0:
                //If the main menu returned 1, then it will call the scenes OnCreate
                if (scene.OnCreate() == false) {
                    OnDestroy();
                    return false;
                }
                break;
                
            case 1:
                //If the main menu returns 2, then it will call the load game function
                if (scene.LoadGame(1) == false) {
                    OnDestroy();
                    return false;
                }
                break;
            
            //TODO: add the ability to load other saved files
            case 2:
                
                break;
            case 3:
                
                break;
            case 4:
                
                 break;
            case 5:
                
                 break;
            case 6:
                
                 break;
            case 7:
                
                 break;
            case 8:
                
                 break;
            case 9:
                break;
                
            default:
                //Error message??
                break;
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
