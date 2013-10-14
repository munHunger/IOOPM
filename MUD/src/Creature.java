import java.util.*; //Import java.util.* to be able to create ArrayLists
import java.io.*; //Import java.io.* to be able to handle file input/output
public abstract class Creature implements Entity //Abstract superclass to all "living" objects such as teachers, students and the sphinx
{
  protected String name; //The name of the creature. It is protected because the classes extending creatures might need to know the name but other classes should use access functions

  public Creature() //Super constructor. All sub classes should call this.
    {
      try //Since we are going to read from a file we should expect possible exceptions, so we try to read and catch possible exceptions.
      {
	ArrayList<String> possibleNames = new ArrayList<String>(); //Create a list to hold all the names written in a file

	//This is just one way of reading from a file. Use google/java docs to get other ways of doing it.
	FileInputStream fStream = new FileInputStream("CreatureNames.names"); //Initialize a stream from the file "CreatureNames.names"
	DataInputStream in = new DataInputStream(fStream); //Create a DataInputStream from the FileInputStream
	BufferedReader br = new BufferedReader(new InputStreamReader(in)); //Lastly create a BufferedReader.
	
	String s; //Create a string to hold each line of the file
	while((s = br.readLine()) != null) //Keep reading line after line until you come to the end of the file and s = null
	  possibleNames.add(s); //Add the line to the list of possible names for the creature
	in.close(); //Close the file stream
	name = possibleNames.get((int)(Math.random()*possibleNames.size())); //Set the creatures name to one of the names in the list. The name is chosen randomly based on the number of possible names
      }
      catch(IOException e) //Catch an IOException. should also catch FileNotFoundException.
      {
	name = "John Doe"; //If an IOException is caught, set the name of the creature to the generic name "John Doe"
      }
      catch(Exception e) //If any other exception is caught, print a stack trace and close the program.
      {
	e.printStackTrace(); //Print stacktrace
	System.exit(0); //Close the program
      }
    }
}
