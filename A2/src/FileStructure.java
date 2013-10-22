public interface FileStructure <ElementType>
{
	public void add(ElementType obj);
	
	public ElementType get(int i);
	
	public boolean contains(ElementType obj);
	
	public int size();
}