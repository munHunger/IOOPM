import java.io.Serializable;


public class Message implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2723363051271966964L;

	private String message;
	private String sender;
	
	public Message(String sender, String message)
	{
		this.message = message;
		this.sender = sender;
	}
	
	public String getMessage()
	{
		return message;
	}
	
	public String getSender()
	{
		return sender;
	}
	
}
