import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JPanel;


public class PaintArea extends JPanel implements MouseListener, MouseMotionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2948758629445200831L;
	
	
	Color bgColor = new Color(40, 50, 50);
	PaintTools tools;
	BitCanvas canvas = new BitCanvas(35,35);
	int size;
	boolean painting = false;
	boolean paintTurn = false;
	
	ObjectOutputStream outStream;
	ObjectInputStream inStream;
	
	public PaintArea(PaintTools tools, ObjectOutputStream outStream, ObjectInputStream inStream)
	{
		this.outStream = outStream;
		this.inStream = inStream;
		this.tools = tools;
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		size = screenSize.height/canvas.getHeight();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(bgColor);
		
		for(int x = 0; x < canvas.getWidth(); x++)
		{
			for(int y = 0; y < canvas.getHeight(); y++)
			{
				int colorIndex = canvas.getColor(x,y);
				tools.paintBlock(g, x*size, y*size, size, tools.getFGColor(colorIndex), tools.getBGColor(colorIndex));
			}
		}
		
		try
		{
			Thread.sleep(100);
		}
		catch(Exception e)
		{
			
		}
		this.repaint();
	}
	
	public BitCanvas getCanvas()
	{
		return canvas;
	}
	
	public void setPaintTurn(boolean turn)
	{
		this.paintTurn = turn;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent e) 
	{
		if(paintTurn)
		{
			painting = true;
			canvas.changeColor(e.getX()/size, e.getY()/size, tools.getPaintingColor());
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		if(paintTurn)
		{
			painting = false;
			writeCanvas();
		}
	}

	public void mouseDragged(MouseEvent e) 
	{
		if(painting && paintTurn)
			canvas.changeColor(e.getX()/size, e.getY()/size, tools.getPaintingColor());
	}

	public void mouseMoved(MouseEvent e) 
	{
	}
	
	private void writeCanvas()
	{
		try 
		{
			outStream.writeObject(canvas);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void overWrite(BitCanvas o) 
	{
		this.canvas = o;
	}
	
}
