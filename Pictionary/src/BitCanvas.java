import java.io.Serializable;

public class BitCanvas implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2358522968416540000L;
	
	byte[][] canvas;
	public BitCanvas(int x, int y)
	{
		canvas = new byte[x][y];
	}
	public void reset()
	{
		for(int x = 0; x < canvas.length; x++)
			for(int y = 0; y < canvas[x].length; y++)
				canvas[x][y] = 0;
	}
	public int getWidth() 
	{
		return canvas.length;
	}
	public int getHeight() 
	{
		return canvas[0].length;
	}
	public int getColor(int x, int y) 
	{
		return canvas[x][y];
	}
	public void changeColor(int x, int y, int c)
	{
		canvas[x][y] = (byte)c;
	}
}
