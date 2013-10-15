public class Comp
{
  public static void main(String args[])
    {
      SuperOBJ obj1 = new SuperOBJ(3, "foo");
      SuperOBJ obj2 = new SuperOBJ(3, "foo");
      SuperOBJ obj3 = new SuperOBJ(5, "bar");

    }

  private String compare(SuperOBJ o1, SuperOBJ o2)
    {
      String s = "Object 1 and 2 are ";
      if(!o1.equals(o2))
	s += "not ";
      s += "equal. ";

      s += "Object 1 and 2 are ";
      if(o1 != o2)
	s += "not ";
      s += "the same object";

      return s;
    }

}

public class SuperOBJ
{
  private int value;
  private String s;

  public boolean comparator(SuperOBJ o2)
    {
      if((value == o2.getVal()) && s.equals(o2.getString()))
	return true;
      return false;
    }

  public SuperOBJ(int value, String s)
    {
      this.value = value;
      this.s = s;
    }

  public int getVal()
    {
      return value;
    }

  public String getString()
    {
      return s;
    }
}
