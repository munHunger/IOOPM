import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

/**
 * GUI is the main class that will first ask the user for an IP-Address and port.
 * It will then draw out a window with the chat to the left, canvas in the center and the toolbar to the right
 * @see PaintArea
 * @see PaintTools
 */
public class GUI implements ActionListener
{
	private ObjectOutputStream outStream = null;
	private ObjectInputStream inStream = null;
	private JTextField inputField = new JTextField();
	private String currentDrawing = "Draw This Shit";
	private JLabel drawTopic = new JLabel(currentDrawing);
	private JEditorPane textArea = new JEditorPane();
	private String name = "Player";
	private String messageLog = "";
	private ArrayList<String> topics = new ArrayList<String>();
	private ArrayList<Player> players = new ArrayList<Player>();
	private Player me = new Player("nisse", 0);
	private boolean drawing = false;
	private int totalPlayerCount;
	private int currentPlayerCount;
	
	/**
	 * Main constructor to create and link all the parts of the program.
	 * This also connects the client to the server
	 */
	public GUI()
	{
		Socket clientSocket;
		try
		{
			JTextField ipAddress = new JTextField();
			JTextField port = new JTextField("6677");
			JComponent[] inputs = new JComponent[]
					{
						new JLabel("IP-Address"),
						ipAddress,
						new JLabel("Port"),
						port
					};
			JOptionPane.showMessageDialog(null, inputs, "Client", JOptionPane.PLAIN_MESSAGE);
			clientSocket = new Socket(ipAddress.getText(), Integer.parseInt(port.getText()));
			outStream = new ObjectOutputStream(clientSocket.getOutputStream());
			inStream = new ObjectInputStream(clientSocket.getInputStream());
			outStream.writeObject(me);
		}
		catch(Exception e)
		{
			JComponent[] msg = new JComponent[]
					{
						new JLabel(e + "")
					};
			JOptionPane.showMessageDialog(null, msg, "Error", JOptionPane.PLAIN_MESSAGE);
			System.exit(0);
		}
		
		PaintTools tools = new PaintTools();
		PaintArea canvas = new PaintArea(tools, outStream, inStream);
		
		JFrame frame = new JFrame("Pictionary");
		frame.setUndecorated(true);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setBounds(0, 0, screenSize.width, screenSize.height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		
		Color bgColor = new Color(40,50,50);
		Color fgColor = new Color(200,200,200);
		Color hlColor = new Color(60,110,180);
		
		JPanel textPanel = new JPanel(new BorderLayout());
		textPanel.setBackground(bgColor);
		Dimension preferredSize = new Dimension(250,0);
		textPanel.setPreferredSize(preferredSize);
		
		
		drawTopic.setForeground(hlColor);
		Font font = new Font("Dialog", Font.PLAIN, 32);
		drawTopic.setFont(font);
		textPanel.add(drawTopic, BorderLayout.NORTH);
		
		
		textArea.setContentType("text/html");
		HTMLEditorKit kit = new HTMLEditorKit();
		textArea.setEditorKit(kit);
		Document doc = kit.createDefaultDocument();
		textArea.setDocument(doc);
		textArea.setBackground(bgColor);
		textArea.setForeground(fgColor);
		textArea.setBorder(null);
		JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.setBorder(null);
		textPanel.add(scrollPane, BorderLayout.CENTER);
		
		
		inputField.setBackground(bgColor);
		inputField.setForeground(fgColor);
		inputField.addActionListener(this);
		inputField.setFocusable(true);
		textPanel.add(inputField, BorderLayout.SOUTH);
		
		frame.add(textPanel, BorderLayout.WEST);
		frame.add(canvas, BorderLayout.CENTER);
		frame.add(tools, BorderLayout.EAST);
		
		ClientInput cInput = new ClientInput(inStream, outStream, canvas, textArea, drawTopic);
		Thread cThread = new Thread(cInput);
		cThread.start();
		
		frame.setVisible(true);
	}
	
	public static void main(String args[])
	{
		new GUI();
	}

	/**
	 * Handles the inputs from the chat panel
	 */
	public void actionPerformed(ActionEvent e) 
	{	
		try 
		{
			String toWrite = inputField.getText();
			if(toWrite.startsWith("/next"))
				toWrite += " " + topics.get((int)(Math.random()*topics.size()));
			outStream.writeObject(new Message(name, toWrite));
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		inputField.setText("");
	}
	
	/**
	 * Timer will when started wait a certain amount of time and then send out a "/next " message to inform the clients that it is time to change who is allowed to paint
	 * @author munHunger
	 *
	 */
	private class Timer implements Runnable
	{
		/**
		 * Start when you want to send a "/next " message in 30 seconds
		 * uses the GUI:s outputStream
		 * @see GUI
		 */
		public void run() 
		{
			long time = System.currentTimeMillis();
			while(System.currentTimeMillis()-time < 30000);
			try 
			{
				outStream.writeObject(new Message(name, "/next " + topics.get((int)(Math.random()*topics.size()))));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ClientInput handles all input from the server to the client
	 * @author munHunger
	 *
	 */
	private class ClientInput implements Runnable
	{
		private ObjectInputStream inStream;
		private ObjectOutputStream outStream;
		private PaintArea pArea;
		private JEditorPane textArea;
		JLabel toDraw;
		
		/**
		 * Basic constructor
		 * @param inStream client ObjectInputStream for later use
		 * @param outStream client ObjectOutputStream for later use
		 * @param pArea The paintArea used in GUI
		 * @param textArea The Textarea used for chat in GUI
		 * @param toDraw The label that notifies what is being painted
		 * @see GUI
		 */
		public ClientInput(ObjectInputStream inStream, ObjectOutputStream outStream, PaintArea pArea, JEditorPane textArea, JLabel toDraw)
		{
			this.inStream = inStream;
			this.outStream = outStream;
			this.pArea = pArea;
			this.textArea = textArea;
			this.toDraw = toDraw;
		}
		
		/**
		 * Constantly reads objects from the server and does useful stuff with it
		 */
		public void run() 
		{
			while(true)
			{
				try
				{
					Object o = inStream.readObject();
					if(o instanceof BitCanvas)
						pArea.overWrite((BitCanvas)o);
					
					else if(o instanceof Message)
						newMessage((Message)o);
					
					else if(o instanceof Player)
					{
						players.add((Player)o);
						currentPlayerCount++;
						if(currentPlayerCount == totalPlayerCount)
						{
							double lowNum = 1.0;
							Player nextPlayer = me;
							for(Player p:players)
								if(p.getRng() < lowNum)
								{
									lowNum = p.getRng();
									nextPlayer = p;
								}
							if(nextPlayer.getName().equals(me.getName()))
							{
								System.out.println("I am painting!");
								drawing = true;
								toDraw.setText(currentDrawing);
								Timer timer = new Timer();
								Thread timerThread = new Thread(timer);
								timerThread.start();
							}
							else
							{
								System.out.println("I am not painting!");
								drawing = false;
								String s = currentDrawing;
								for(int i = 0; i < s.length(); i++)
									s = s.replace(s.charAt(i), '-');
								toDraw.setText(s);
							}
							pArea.setPaintTurn(drawing);
						}
						if(currentPlayerCount > totalPlayerCount)
							totalPlayerCount++;
					}
					else if(o instanceof String)
						action((String)o);
				}
				catch(Exception e)
				{
					
				}
			}
		}
		
		private void action(String a)
		{
			if(a.equals("updatePlayerList"))
				players.clear();
		}
		
		private void newMessage(Message msg)
		{
			if(msg.getMessage().startsWith("/name "))
			{
				if(msg.getSender().equals(name))
				{
					name = msg.getMessage().substring("/name ".length());
					//me.setName(name);
					me = new Player(name, me.getScore(), me.getRng());
				}
			}
			else if(msg.getMessage().startsWith("/addScore "))
			{
				if(msg.getSender().equals(name))
				{
					int score = Integer.parseInt(msg.getMessage().substring("/addScore ".length()));
					//me.setName(name);
					me = new Player(me.getName(), me.getScore() + score, me.getRng());
				}
			}
			else if(msg.getMessage().startsWith("/add "))
				topics.add(msg.getMessage().substring("/add ".length()));
			else if(msg.getMessage().startsWith("/next "))
				reset(msg.getMessage().substring("/next ".length()));
			else if(msg.getMessage().startsWith("/score"))
				showScore();
			else
				updateTextArea(msg);
		}
		
		private void showScore()
		{
			for(Player p:players)
			{
				try 
				{
					outStream.writeObject(new Message("System", (p.getName() + ":" + p.getScore())));
				} 
				catch (Exception e) 
				{
				}
			}
		}
		
		
		private void reset(String topic)
		{
			try
			{
				currentPlayerCount = 0;
				outStream.writeObject("updatePlayerList");
				me.rng();
				outStream.writeObject(me);
			}
			catch(Exception e)
			{
				
			}
			pArea.getCanvas().reset();
			currentDrawing = topic;
		}

		private void updateTextArea(Message msg) 
		{
			String messageColor = "#FFFFFF";
			String s = msg.getMessage();
			if(s.equalsIgnoreCase(toDraw.getText()))
			{
				if(!msg.getSender().equals(me.getName()))
				{
					if(drawing)
					{
						try 
						{
							outStream.writeObject("/addScore 1");
						}
						catch (IOException e) 
						{
							e.printStackTrace();
						}
					}
					for(int i = 0; i < s.length(); i++)
						s = s.replace(s.charAt(i), '-');
				}
				else
				{
					try 
					{
						outStream.writeObject("/addScore 10");
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
				messageColor = "#648CB4";
			}
			messageLog += "<span style='font-size:24'><span style='color:#96C85A'>" + msg.getSender() + ":</span><span style='color:" + messageColor + "'>" + s + "</span></span><br />";
			textArea.setText(messageLog);
		}
	}
}
