
package ca.chess.core;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ChessBoardGUI extends JPanel {
    
    //The main panel for the board
    private JPanel insidePanel;
    
    //The array of buttons for the game
    private JButton button[][] = new JButton[8][8];
    
    //Constructor
    public ChessBoardGUI() {
        
        //Call the initialize components method
        initComponents();
    }
    
    //Initializing the components
    public void initComponents() {
        
        //Create the inside Panel Frame
        insidePanel = new JPanel();
        
        //Set the layout to the be border layout
        this.setLayout(new BorderLayout());
        
        //Set the inside panel layout to be an 8x8 grid
        insidePanel.setLayout(new GridLayout(8, 8));
        
        //Add that grid to the center of the layout
        this.add(insidePanel, BorderLayout.CENTER);
        
        //Make a text field called Narrator that will tell the user if they made any mistakes
        JTextField textLog = new JTextField("Narrator");
        
        //Add the text window to the bottom of the game and make the text uneditable
        textLog.setPreferredSize( new Dimension( 200, 50 ) );
        textLog.setFont(new Font("Arial", Font.BOLD, 20));
        textLog.setEditable(false);
        this.add(textLog, BorderLayout.SOUTH);
        
        //The string used to load the images to the board
        String name = "";
        
        //starting from the top row (order that the buttons get added), the i is equal to the X-axis
        for(int i = 7; i > -1; i--)
        {
            //starting from the far right column (order that the buttons get added), the j is equal to the Y-axis
            for(int j = 0; j < 8; j++)
            {
                //Create the button in the array, with the appropriate name
                button[j][i] = new JButton("");
                
                //Add the button to the panel
                insidePanel.add(button[j][i]);
                 
                button[j][i].addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent ae){
                        System.out.println("Space was " );
                    }
                });
                
                //Try adding the pictures to the images
                try{                    
                    //White Pawns
                    if(i == 1) 
                        name = "/Pictures/WhitePawn.png";
                    
                    //Black Pawns
                    else if(i == 6) 
                        name = "/Pictures/BlackPawn.png";
                    
                    //The White back row
                    else if(i == 0)
                    {
                        //White Rooks
                        if(j == 0 || j == 7)
                            name = "/Pictures/WhiteRook.png";
                        
                        //White Knights
                        else if(j == 1 || j == 6) 
                            name = "/Pictures/WhiteHorse.png";
                        
                        //White Bishops
                        else if(j == 2 || j == 5) 
                            name = "/Pictures/WhiteBishop.png";
                        
                        //White Queens
                        else if(j == 3) 
                            name = "/Pictures/WhiteQueen.png";
                        
                        //White Kings
                        else if(j == 4)
                            name = "/Pictures/WhiteKing.png";
                    }
                    
                    //The Black backrow
                    else if(i == 7)
                    {
                        //Black Rooks
                        if(j == 0 || j == 7)
                            name = "/Pictures/BlackRook.png";
                        
                        //Black Knights
                        else if(j == 1 || j == 6) 
                            name = "/Pictures/BlackHorse.png";
                        
                        //Black Bishops
                        else if(j == 2 || j == 5) 
                            name = "/Pictures/BlackBishop.png";
                        
                        //Black Queens
                        else if(j == 3) 
                            name = "/Pictures/BlackQueen.png";

                        //Black Knights
                        else if(j == 4)
                            name = "/Pictures/BlackKing.png";
                    }
                    else
                        name = "";
                    
                    if(!name.equalsIgnoreCase(""))
                    {
                        //Get the picture from the pictures folder
                        Image tempImg = ImageIO.read(getClass().getResource(name));
                        
                        //Add that image to the button
                        button[j][i].setIcon(new ImageIcon(tempImg));
                    }

                }
                catch(Exception e)
                {
                    System.out.println(e.getMessage() + "Error loading image!");
                }
            }
        }
       
        

    }
    
}
