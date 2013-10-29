import java.io.Serializable;

	
public class Player implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7574367895014041967L;
	private String name;
	private int score;
	private double rng;
	
	public Player(String name, int score)
	{
		this.name = name;
		this.score = score;
		rng = Math.random();
	}
	
	public Player(String name, int score, double rng)
	{
		this.name = name;
		this.score = score;
		this.rng = rng;
	}
	
	public void rng()
	{
		rng = Math.random();
	}
	
	public double getRng()
	{
		return rng;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setName(String s)
	{
		name = new String(s);
		//name = s;
	}
	
	public void setScore(int c)
	{
		score = c;
	}
}