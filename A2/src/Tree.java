
public class Tree implements FileStructure
{
	private int length;
	private int index;
	private Object obj;
	private Tree[] branches;
	public Tree()
	{
		branches = new Tree[2];
	}
	
	public Tree(int i, Object obj)
	{
		branches = new Tree[2];
		this.obj = obj;
		index = i;
	}
	
	public void add(Object obj)
	{
		add(obj, length);
	}
	
	private void add(Object obj, int index)
	{
		if(this.obj == null)
			this.obj = obj;
		else
		{
			int minNum = 9999999;
			int shortTree = 0;
			for(int i = 0; i < branches.length; i++)
			{
				if(branches[i] == null || branches[i].size() < minNum)
				{
					if(branches[i] == null)
						minNum = 0;
					else
						minNum = branches[i].size();
					shortTree = i;
				}
			}
			
			if(branches[shortTree] == null)
				branches[shortTree] = new Tree(index, obj);
			else
				branches[shortTree].add(obj, index);
		}
			
		length++;
	}
	
	public Object get(int i)
	{
		if(i == index)
			return obj;
		else
			for(int x = 0; x < branches.length; x++)
				if(branches[x] != null && !branches[x].get(i).equals("NaN"))
					return branches[x].get(i);
		
		return "NaN";
	}

	public boolean contains(Object obj) 
	{
		if(this.obj.equals(obj))
			return true;
		for(int i = 0; i < branches.length; i++)
			if(branches[i].contains(obj))
				return true;
		return false;
	}

	public int size() 
	{
		return length;
	}

}
