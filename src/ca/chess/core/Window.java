package ca.chess.core;


import javax.swing.JFrame;
import javax.swing.JPanel;


public class Window {

	private JFrame frame;
	private JPanel panel;
	
	public Window()
	{
		
		//Once again, not sure what to put here... yet.

	}
	
	public boolean OnCreate(int _width, int _height){
		
		frame = new JFrame("Chess Game");
		frame.setSize(_width, _height);
		frame.setVisible(true);
		
		return true;
	}
}
