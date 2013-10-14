public class Course //Object to represent a course by holding a string and an int, as name and HP
{
  private String name; //The name of the course
  private int hp; //The amount of HP the course represents
  public Course(String name, int hp) //Constructor(only place to set the variabels)
    {
      this.name = name; //Set the Course name to name
      this.hp = hp; //Set the Course hp to hp
    }

  public int getHP() //Get function for the amount of HP that the course gives
    {
      return hp; //Return hp
    }

  public String getName() //Get function for the name of the course
    {
      return name; //Return the name of the course
    }
}
