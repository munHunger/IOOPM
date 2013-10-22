import java.util.ArrayList;
public class Tree <ElementType> implements FileStructure <ElementType>
{
    private int length;
    private int index;
    private ElementType obj;
    private ArrayList<Tree> branches = new ArrayList<Tree>();
    private int branchNum = 2;
    public Tree()
	{
	    for(int i = 0; i < branchNum; i++)
		branches.add(null);
	}
	
    public Tree(int i, ElementType obj)
	{
	    for(int n = 0; n < branchNum; n++)
		branches.add(null);
	    this.obj = obj;
	    index = i;
	}
	
    public void add(ElementType obj)
    {
	add(obj, length);
    }
	
    private void add(ElementType obj, int index)
    {
	if(this.obj == null)
	    this.obj = obj;
	else
	    {
		int minNum = 9999999;
		int shortTree = 0;
		for(int i = 0; i < branches.size(); i++)
		    {
			if(branches.get(i) == null || branches.get(i).size() < minNum)
			    {
				if(branches.get(i) == null)
				    minNum = 0;
				else
				    minNum = branches.get(i).size();
				shortTree = i;
			    }
		    }
			
		if(branches.get(shortTree) == null)
		    {
			branches.set(shortTree, new Tree<ElementType>(index, obj));
		    }
		else
		    branches.get(shortTree).add(obj, index);
	    }
			
	length++;
    }
	
    public ElementType get(int i)
    {
	if(i == index)
	    return obj;
	else
	    {
		for(int x = 0; x < branches.size(); x++)
		    {
			if(branches.get(x) != null && ((Tree<ElementType>)(branches.get(x))).get(i) != null)
			    return ((Tree<ElementType>)(branches.get(x))).get(i);
		    }
	    }
	return null;
    }

    public boolean contains(ElementType obj) 
    {
	if(this.obj.equals(obj))
	    return true;
	for(int i = 0; i < branches.size(); i++)
	    if(branches.get(i).contains(obj))
		return true;
	return false;
    }

    public int size() 
    {
	return length;
    }

}
