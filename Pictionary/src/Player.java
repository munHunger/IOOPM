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
	
    /**
     * Creates a new player with a fixed name, fixed score and a new generated random number
     * @param  name  The name for the player
     * @param  score The fixed score for the player
     */
	public Player(String name, int score)
	{
		this.name = name;
		this.score = score;
		rng = Math.random();
	}
	
    /**
     * Creates a new Player with fixed name, score and rng
     * @param name The name for the player
     * @param score The fixed score for the player
     * @param rng The random number for the player
     */
	public Player(String name, int score, double rng)
	{
		this.name = name;
		this.score = score;
		this.rng = rng;
	}
    /**
     * Generates a new random number
     */
	public void rng()
	{
		rng = Math.random();
	}
    /**
     * Returns the randomly generated double held by the Player Object
     * <p>This method always returns in constant time
     * @return   A random number held by Player
     * @see      Player
     */
	public double getRng()
	{
		return rng;
	}
    /**
     * Returns the player name.
     * @return The name of the player
     */
	public String getName()
	{
		return name;
	}
    /**
     * Returns the player score
     * @return The score held by the player
     */
	public int getScore()
	{
		return score;
	}
    /**
     * Sets the name of the Player
     * @param s The new name for the player
     */
	public void setName(String s)
	{
		name = new String(s);
		//name = s;
	}
    /**
     * Sets the score for the Player
     * @param c The new score for the Player
     */
	public void setScore(int c)
	{
		score = c;
	}
}