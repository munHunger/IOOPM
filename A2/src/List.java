
public class List implements FileStructure
{
	private Node first;
	private int length;
	public List()
	{
		
	}
	
	public void add(Object obj) 
	{
		Node newNode = new Node(null, obj);
		if(first == null)
			first = newNode;
		else
			addLast(newNode, first);
		length++;
	}

	private void addLast(Node n, Node c)
	{
		if(c == null)
			return;
		else if(c.next != null)
			addLast(n, c.next);
		else
			c.next = n;
	}
	
	public Object get(int i) 
	{
		if(i > length)
			return null;
		else
		{
			Node current = first;
			for(int x = 0; x < i; x++)
				current = current.next;
			return current.obj;
		}
	}

	public boolean contains(Object obj) 
	{
		Node current = first;
		while(current != null)
		{
			if(current.obj.equals(obj))
				return true;
			current = current.next;
		}
		return false;
	}

	public int size() 
	{
		return length;
	}
	
	private class Node
	{
		public Node next;
		public Object obj;
		
		public Node(Node next, Object obj)
		{
			this.next = next;
			this.obj = obj;
		}
	}
}
