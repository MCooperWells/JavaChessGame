package ca.chess.core;

import ca.chess.errors.CriticalSystemErrorException;

public class Main {

    public static void main(String[] args) throws CriticalSystemErrorException {

        GameManager manager = new GameManager();

        //If the GameManager fails to initialize anything, lets not run the program...
        if (manager.OnCreate() == false) {
            throw new CriticalSystemErrorException();
        } 
        
        else { //If there are no errors, run the manager.

            manager.Run();
        }
    }
}
