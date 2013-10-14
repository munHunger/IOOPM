import javax.swing.*;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
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
		

      Room r1 = new Room();
      Room r2 = new Room();
      Room r3 = new Room();
      Door[] d1 = new Door[]{new Door(r1, r2, true), null, null, new Door(r1, r3, false)};
      r1.setDoors(d1);
      Door[] d2 = new Door[]{null, null, new Door(r1, r2, true), null};
      r2.setDoors(d2);
      Door[] d3 = new Door[]{null, new Door(r1, r3, false), null, null};
      r3.setDoors(d3);
		
      paintWorld(g, r1, new ArrayList<Area>(), 100, 100);
      this.repaint();
    }
	
  public void paintWorld(Graphics g, Area a, ArrayList<Area> visited, int x, int y)
    {
      if(!visited.contains(a))
      {
	int size = 35;
	paintBlock(g, x, y, size, new int[]{125,125,125});
	Door[] doors = a.getDoors();
	visited.add(a);
			
	int[] xA = new int[]{-size, 0, size, 0};
	int[] yA = new int[]{0, -size, 0, size};
	boolean[] vertical = new boolean[]{false, true, false, true};
	for(int i = 0; i < 4; i++)
	  if(doors[i] != null)
	  {
	    paintDoor(g, x+xA[i], y+yA[i], size, new int[]{175, 150, 150}, vertical[i], doors[i].isUnlocked());
	    paintWorld(g, doors[i].getArea(a), visited, x+(xA[i]*2), y+(yA[i]*2));
	  }
      }
    }
	
  public void paintDoor(Graphics g, int x, int y, int size, int[] color, boolean vertical, boolean opened)
    {
      if(color.length < 3) //Check if the length of the color array is smaller than 3, and throw an exception if so, because you cannot create a RGBA color with less than 3 values
	throw new IllegalArgumentException("color must be an array of 3 or more integers, making the color (color[0],color[1],color[2], rest) || (r,g,b,rest)"); //Throw exception with a message of what went wrong
      for(int i = 0; i < 3; i++) //Loop through each value of the color array
	if(color[i] > 255 || color[i] < 20) //Check to see if the value is between acceptable bounds. must be above 20 because the background value is 20 smaller than the foreground
	  throw new IllegalArgumentException("Each of the 3 first integers in the color array must be withing the range of 20 and 255"); //Throw exception with a message of what went wrong
		
      Color fgColor = new Color(color[0], color[1], color[2]); //Declare the foreground color
      Color bgColor = new Color(color[0]-20, color[1]-20, color[2]-20); //Declare the background color a bit darker than the foreground, but keeping the alpha the same
		
      BufferedImage door = new BufferedImage(size,size, BufferedImage.TYPE_INT_ARGB); //Create a BufferedImage to paint the door onto. This image will later on be rotated and moved before painted onto the screen
      Graphics iG = door.getGraphics(); //Get the Graphic object from the BufferedImage to use later on when painting
		
      int sizeDiv = size/7; //Get a sizeDiv
      iG.setColor(fgColor); //Set the foreground color
      iG.fillRect(0, sizeDiv*2, size, sizeDiv*3); //Paint the door outline
		
      iG.setColor(bgColor); //Change to background color
      if(!opened) //If the door is locked, paint a locked door, otherwise paint an opened door
	iG.fillRect(sizeDiv, sizeDiv*2, size-(2*sizeDiv), sizeDiv*3); //Draw a long square to indicate a locked door
      else //The door is opened, paint an opened door(2 stripes)
      {
	iG.fillRect(sizeDiv, sizeDiv*2, sizeDiv, sizeDiv*3); //Draw the first stripe
	iG.fillRect(size-(2*sizeDiv), sizeDiv*2, sizeDiv, sizeDiv*3); //Draw the second stripe
      }
		
      AffineTransform at = new AffineTransform(); //Create an AffineTransform to manage rotation
      int halfSize = size/2; //Get the distance(in pixels) to center of the image(on each axis)
      if(vertical) //If true, the image needs to be rotated
      {
	at.translate(halfSize, halfSize); //Move at to the center of the image
	at.rotate(Math.PI/2); //Rotate the image 90 degrees clockwise around its center
	at.translate(-halfSize, -halfSize); //Move back the at to the top left corner of the image
	at.translate(y, -x); //Move the image to the position(x,y). it is flipped because the at is rotated
      }
      else //The image does not need to be rotated so just move it in place
	at.translate(x, y); //Move the image to the position(x,y)
      Graphics2D g2d = (Graphics2D)g; //Convert g to a Graphics2D object. This is required for drawing images with AffineTransform
      g2d.drawImage(door, at, null); //Draw the image. The null argument is an ImageObserver(not sure what that is)
    }
	
	
  //Paints a block using the graphics object, at the position (x,y) with the width=height=size and the color(RGB)=color
  public void paintBlock(Graphics g, int x, int y, int size, int[] color)
    {
      if(color.length < 3) //Check if the length of the color array is smaller than 3, and throw an exception if so, because you cannot create a RGBA color with less than 3 values
	throw new IllegalArgumentException("color must be an array of 3 or more integers, making the color (color[0],color[1],color[2], rest) || (r,g,b,rest)"); //Throw exception with a message of what went wrong
      for(int i = 0; i < 3; i++) //Loop through each value of the color array
	if(color[i] > 255 || color[i] < 20) //Check to see if the value is between acceptable bounds. must be above 20 because the background value is 20 smaller than the foreground
	  throw new IllegalArgumentException("Each of the 3 first integers in the color array must be withing the range of 20 and 255"); //Throw exception with a message of what went wrong
		
      Color fgColor = new Color(color[0], color[1], color[2]); //Declare the foreground color
      Color bgColor = new Color(color[0]-20, color[1]-20, color[2]-20); //Declare the background color a bit darker than the foreground, but keeping the alpha the same
		
      int sizeDiv = size/5; //Divide the size by 5 to get the border/bevel width
		
      g.setColor(fgColor); //Change color to foreground
      g.fillRect(x, y, size, size); //Draw a "large" rectangle that will be the utmost border
		
      g.setColor(bgColor); //Change color to background
      g.fillRect(x+sizeDiv, y+sizeDiv, size-(sizeDiv*2), size-(sizeDiv*2)); //Draw a slightly smaller rectangle that will be the inner ring/bevel
		
      g.setColor(fgColor); //Change color to foreground
      g.fillRect(x+(2*sizeDiv), y+(2*sizeDiv), sizeDiv, sizeDiv); //Draw the smallest square that will be the inner square
    }
	
  //Main function creates a new MUD in a non-static context
  public static void main(String args[])
    {
      new MUD();
    }
}
