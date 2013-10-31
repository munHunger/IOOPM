import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


public class PaintTools extends JPanel implements MouseListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Color[] fgColors;
	Color[] bgColors;
	Rectangle[] swatches;
	int paintingColor = 1;
	
	int size = 50;
	public PaintTools()
	{	
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		size = screenSize.height/10;
		fgColors = new Color[30];
		bgColors = new Color[fgColors.length];
		swatches = new Rectangle[fgColors.length];
		fgColors[0] = new Color(40,50,50);
		fgColors[1] = new Color(200,200,200);
		fgColors[2] = new Color(255,0,0);
		fgColors[3] = new Color(128,0,0);
		fgColors[4] = new Color(255,255,0);
		fgColors[5] = new Color(255,128,0);
		fgColors[6] = new Color(128,255,0);
		fgColors[7] = new Color(0,128,0);
		fgColors[8] = new Color(0,255,64);
		fgColors[9] = new Color(0,128,64);
		fgColors[10] = new Color(0,255,255);
		fgColors[11] = new Color(0,0,255);
		fgColors[12] = new Color(0,128,192);
		fgColors[13] = new Color(0,0,160);
		fgColors[14] = new Color(128,128,192);
		fgColors[15] = new Color(128,0,128);
		fgColors[16] = new Color(255,0,255);
		fgColors[17] = new Color(128,0,255);
		fgColors[18] = new Color(25,25,25);
		fgColors[19] = new Color(255,255,255);
		fgColors[20] = new Color(128,64,0);
		fgColors[21] = new Color(255,128,64);
		fgColors[22] = new Color(0,64,0);
		fgColors[23] = new Color(0,255,0);
		fgColors[24] = new Color(0,64,64);
		fgColors[25] = new Color(128,128,128);
		fgColors[26] = new Color(110,40,0);
		fgColors[27] = new Color(240,180,120);
		fgColors[28] = new Color(255,128,255);
		fgColors[29] = new Color(64,128,128);
		
		
		for(int i = 0; i < fgColors.length; i++)
		{
			int[] color = new int[]{fgColors[i].getRed()-25, fgColors[i].getGreen()-25, fgColors[i].getBlue()-25};
			for(int n = 0; n < color.length; n++)
				if(color[n] < 0)
					color[n] = 0;
			bgColors[i] = new Color(color[0], color[1], color[2]);
			swatches[i] = new Rectangle((i/10)*size, (i%10)*size, size, size);
		}
		
		
		Dimension preferredSize = new Dimension(((fgColors.length/10))*size,size);
		this.setPreferredSize(preferredSize);
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		this.setBackground(Color.BLACK);
		
		for(int i = 0; i < fgColors.length; i++)
			paintBlock(g, (i/10)*size, (i%10)*size, size, fgColors[i], bgColors[i]);
		
	}
	
	public void paintBlock(Graphics g, int x, int y, int size, Color fg, Color bg)
	{
		int size5 = size/5;
		int size2 = size5*2;
		
		g.setColor(fg);
		g.fillRect(x, y, size, size);
		
		g.setColor(bg);
		g.fillRect(x+size5, y+size5, size-size2, size-size2);
		
		g.setColor(fg);
		g.fillRect(x+size2, y+size2, size5, size5);
	}
	
	public int getPaintingColor()
	{
		return paintingColor;
	}

	public Color getFGColor(int colorIndex) 
	{
		return fgColors[colorIndex];
	}

	public Color getBGColor(int colorIndex) 
	{
		return bgColors[colorIndex];
	}

	public void mouseClicked(MouseEvent e) 
	{
		for(int i = 0; i < swatches.length; i++)
			if(swatches[i].contains(e.getX(), e.getY()))
				paintingColor = i;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
