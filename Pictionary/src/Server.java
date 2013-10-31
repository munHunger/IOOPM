import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server 
{
	public static void main(String args[])
	{
		new Server();
	}
	
	boolean playing = false;
	int port = 6677;
	ArrayList<Client> clients = new ArrayList<Client>();
	public Server()
	{
		acceptConnections();
	}
	public void acceptConnections()
	{
		try
		{
			ServerSocket serverSocket = new ServerSocket(port);
			while(!playing)
			{
				Client newPlayer = new Client(serverSocket.accept(), this);
				clients.add(newPlayer);
				Thread playerThread = new Thread(newPlayer);
				playerThread.start();
			}
			serverSocket.close();
		}
		catch(Exception e)
		{
			
		}
	}
	
	public void writeToAll(Object o)
	{
		if(o != null)
			for(Client p: clients)
				p.writeObject(o);
	}
	
	private class Client implements Runnable
	{
		private final Server server;
		private ObjectOutputStream outStream = null;
		private ObjectInputStream inStream = null;
		public Client(Socket socket, Server server)
		{
			this.server = server;
			try
			{
				outStream = new ObjectOutputStream(socket.getOutputStream());
				inStream = new ObjectInputStream(socket.getInputStream());
			}
			catch(Exception e)
			{
				
			}
		}

		public void writeObject(Object o)
		{
			try 
			{
				outStream.writeObject(o);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
		
		public void run() 
		{
			while(true)
			{
				try 
				{
					Object o = inStream.readObject();
					server.writeToAll(o);
				} 
				catch (ClassNotFoundException e) 
				{
					e.printStackTrace();
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
