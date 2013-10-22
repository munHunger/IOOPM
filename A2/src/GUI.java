import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI implements ActionListener{
	public static void main(String args[])
	{
		new GUI();
	}
	private JButton listButton = new JButton("List");
	private JButton treeButton = new JButton("Tree");
	private JTextArea contentArea = new JTextArea();
	
	private JTextField filterField = new JTextField();
	private JButton filterButton = new JButton("Filter");
	private JTextArea filteredArea = new JTextArea();
	private JTextField addField = new JTextField();
	private JButton addButton = new JButton("Add");
	
	private List list = new List();
	private Tree tree = new Tree();
	private FileStructure fileStructure = list;
	
	public GUI()
	{
		list.add("List");
		tree.add("Tree");
		try
		{
			FileInputStream fStream = new FileInputStream("data.txt");
			DataInputStream in = new DataInputStream(fStream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String s;
			while((s = br.readLine()) != null)
			{
				list.add(s);
				tree.add(s);
			}
			in.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
		JFrame frame = new JFrame("FileStructure");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300,800);
		frame.setLayout(new GridLayout(2,1));
		
		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(1,2));
		buttonPanel.add(listButton);
		buttonPanel.add(treeButton);
		topPanel.add(buttonPanel, BorderLayout.NORTH);
		topPanel.add(contentArea, BorderLayout.CENTER);
		frame.add(topPanel);
		
		JPanel bottomPanel = new JPanel(new BorderLayout());
		JPanel filterPanel = new JPanel(new BorderLayout());
		filterPanel.add(filterField, BorderLayout.CENTER);
		filterPanel.add(filterButton, BorderLayout.EAST);
		bottomPanel.add(filterPanel, BorderLayout.NORTH);
		bottomPanel.add(filteredArea, BorderLayout.CENTER);
		JPanel inputPanel = new JPanel(new BorderLayout());
		inputPanel.add(addField, BorderLayout.CENTER);
		inputPanel.add(addButton, BorderLayout.EAST);
		bottomPanel.add(inputPanel, BorderLayout.SOUTH);
		frame.add(bottomPanel);
		
		listButton.addActionListener(this);
		treeButton.addActionListener(this);
		addButton.addActionListener(this);
		filterButton.addActionListener(this);
		
		updateContentArea();
		frame.setVisible(true);
	}

	private void updateContentArea()
	{
		String s = "";
		for(int i = 0; i < fileStructure.size(); i++)
			s += (String)fileStructure.get(i) + "\n";
		contentArea.setText(s);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource().equals(listButton))
			fileStructure = list;
		else if(e.getSource().equals(treeButton))
			fileStructure = tree;
		else if(e.getSource().equals(filterButton))
		{
			String s = "";
			for(int i = 0; i < fileStructure.size(); i++)
				if(((String)fileStructure.get(i)).contains(filterField.getText()))
					s += (String)fileStructure.get(i) + "\n";
			filteredArea.setText(s);
		}
		else if(e.getSource().equals(addButton))
			fileStructure.add(addField.getText());
		
		updateContentArea();
	}
}
