import java.util.*; //Import java.util.* to get access to the ArrayList
public class Student extends Creature
{
  ArrayList<Course> unfinnishedCourses = new ArrayList<Course>(); //List of all unfinnished courses
  ArrayList<Course> finnishedCourses = new ArrayList<Course>(); //List of all finnished courses

  public Student() //Constructor to give the student a name and setup the lists of Courses
    {
      super(); //Call the super constructor to get a name
      unfinnishedCourses = new ArrayList<Course>(); //Initialize a list
      finnishedCourses = new ArrayList<Course>(); //Initialize a list
    }

  public Student(Course unfinnished, Course finnished) //Constructor to give a new student 1 finnished and 1 unfinnised course(will be used for NPC)
    {
      this(); //Call the empty constructor to get a name and to initialize lists
      unfinnishedCourses.add(unfinnished); //Add a Course to the list
      finnishedCourses.add(finnished); //Add a Course to the list
    }

  public String talk() //Function to be able to talk with student
    {
      return super.getRandomFromFile("Quotes.creature", "I do not wish to talk with you"); //Get a random quote from the file "Quotes.creature", if an exception is thrown the String "I do not wish to talk with you" is returned
    }
}
