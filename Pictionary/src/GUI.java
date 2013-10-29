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
import java.io.Serializable;
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

	public void actionPerformed(ActionEvent e) 
	{	
		try 
		{
			outStream.writeObject(new Message(name, inputField.getText()));
		} 
		catch (IOException e1) 
		{
			e1.printStackTrace();
		}
		inputField.setText("");
	}
	
	private class ClientInput implements Runnable
	{
		private ObjectInputStream inStream;
		private ObjectOutputStream outStream;
		private PaintArea pArea;
		private JEditorPane textArea;
		JLabel toDraw;
		
		public ClientInput(ObjectInputStream inStream, ObjectOutputStream outStream, PaintArea pArea, JEditorPane textArea, JLabel toDraw)
		{
			this.inStream = inStream;
			this.outStream = outStream;
			this.pArea = pArea;
			this.textArea = textArea;
			this.toDraw = toDraw;
		}
		
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
						System.out.println("Recieved name:" + ((Player)o).getName());
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
									System.out.println(p.getName() + ":" + p.getRng());
								}
							if(nextPlayer.equals(me))
							{
								System.out.println("I am painting!");
								drawing = true;
								toDraw.setText(currentDrawing);
							}
							else
							{
								System.out.println("I am not painting!");
								drawing = false;
								String s = currentDrawing;
								for(int i = 0; i < s.length(); i++)
									s.replace(s.charAt(i), '-');
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
			{
				System.out.println("Clearing playerList");
				players.clear();
			}
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
					System.out.println("Setting name:" + me.getName());
				}
			}
			else if(msg.getMessage().startsWith("/add "))
				topics.add(msg.getMessage().substring("/add ".length()));
			else if(msg.getMessage().startsWith("/next"))
				reset();
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
		
		private void reset()
		{
			try
			{
				currentPlayerCount = 0;
				outStream.writeObject("updatePlayerList");
				me.rng();
				System.out.println("My name is " + me.getName());
				outStream.writeObject(me);
			}
			catch(Exception e)
			{
				
			}
			pArea.getCanvas().reset();
			currentDrawing = topics.get((int)(Math.random()*topics.size()));
		}

		private void updateTextArea(Message msg) 
		{
			String messageColor = "#FFFFFF";
			if(msg.getMessage().equalsIgnoreCase(toDraw.getText()))
			{
				try 
				{
					outStream.writeObject("Correct");
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				messageColor = "#648CB4";
			}
			messageLog += "<span style='font-size:24'><span style='color:#96C85A'>" + msg.getSender() + ":</span><span style='color:" + messageColor + "'>" + msg.getMessage() + "</span></span><br />";
			textArea.setText(messageLog);
		}
	}
}
