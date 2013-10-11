import javax.swing.*;
import java.awt.*;
public class MUD extends JPanel
{
	//MUD constructor
	public MUD()
	{
		JFrame frame = new JFrame("MUD"); //Create a window with the title "MUD".
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Set default close operation so that the program exits when you shutdown the window
		frame.setSize(1400,1000); //Set the size of window to 1400 pixels wide and 1000 pixels high
		
		JPanel mainPanel = new JPanel(); //Create a panel to separate player input and game output
		mainPanel.setLayout(new BorderLayout()); //Set the layout manager to BorderLayout to later use its location properties
		
		JTextField userInput = new JTextField(); //Create the text input field
		mainPanel.add(userInput, BorderLayout.SOUTH); //Add it to the manPanel at the southern location
		
		JPanel contentPanel = new JPanel(); //Create a panel to hold all the games output components
		contentPanel.setLayout(new GridLayout(1,2)); //Set the panels layout manager to GridLayout in order to evenly split the output between its components. The arguments for GridLayout is (number of rows, number of columns)
		
		JTextArea contentText = new JTextArea(); //Create a text area where the game will print information for the user
		contentPanel.add(contentText); //Add the text area to the panel. This component will be located at the first available slot in the grid created by the GridLayout. As it is the first component to be added it will be positioned to the left
		
		contentPanel.add(this); //Add the games panel to the content. It will be positioned to the right of contentText. This panel will be used to paint a graphical minimap
		this.repaint(); //Start painting the map (This will be made in a separate thread so do not worry about blocking)
		
		mainPanel.add(contentPanel, BorderLayout.CENTER); //Add the game output panel to the main panel
		
		frame.add(mainPanel); //Add the main panel to the window
		frame.setVisible(true); //Show the window
	}
	
	//Override the paintComponent function found in the JPanel object that this class extends
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); //Call JPanels paintComponent to make sure you don't lose any potentially important operations found there
		this.setBackground(Color.BLACK); //Set the background color of this panel(minimap) to black
	}
	
	//Main function creates a new MUD in a non-static context
	public static void main(String args[])
	{
		new MUD();
	}
}
