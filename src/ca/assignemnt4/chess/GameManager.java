
package ca.assignemnt4.chess;

import javax.swing.JFrame;


public class GameManager  {

    //The object gameFrame
    JFrame gameFrame;
    
    //Constructor for the frame
    public GameManager() {
       gameFrame = new JFrame("Chess Game");
    }
    
    //On Create function
    public boolean OnCreate()
    {
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
        gameFrame.setBounds(200, 200, 830, 800);
        gameFrame.setResizable(false);
        return true;
    }  
}   