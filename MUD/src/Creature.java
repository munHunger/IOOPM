import java.util.*; //Import java.util.* to be able to create ArrayLists
import java.io.*; //Import java.io.* to be able to handle file input/output
public abstract class Creature implements Entity //Abstract superclass to all "living" objects such as teachers, students and the sphinx
{
  protected String name; //The name of the creature. It is protected because the classes extending creatures might need to know the name but other classes should use access functions

  public Creature() //Super constructor. All sub classes should call this.
    {
      name = getRandomFromFile("Names.creature", "John Doe"); //Set the creatures name to some random name found in the file "Names.creature". If it throws an exception, the name will be set to "John Doe"
    }

  protected String getRandomFromFile(String toRead, String defaultString)
    {
      try //Since we are going to read from a file we should expect possible exceptions, so we try to read and catch possible exceptions.
      {
	ArrayList<String> possibleStrings = new ArrayList<String>(); //Create a list to hold all possible Strings

	//This is just one way of reading from a file. Use google/java docs to get other ways of doing it.
	FileInputStream fStream = new FileInputStream(toRead); //Initialize a stream from the file toRead
	DataInputStream in = new DataInputStream(fStream); //Create a DataInputStream from the FileInputStream
	BufferedReader br = new BufferedReader(new InputStreamReader(in)); //Lastly create a BufferedReader.
	
	String s; //Create a string to hold each line of the file
	while((s = br.readLine()) != null) //Keep reading line after line until you come to the end of the file and s = null
	  possibleStrings.add(s); //Add the line to the list of possibleStrings
	in.close(); //Close the file stream
	return possibleStrings.get((int)(Math.random()*possibleStrings.size())); //Return a string chosen at random from the list of random strings
      }
      catch(IOException e) //Catch an IOException. should also catch FileNotFoundException.
      {
	return defaultString; //If an IOException is caught, return the default string
      }
      catch(Exception e) //If any other exception is caught, return null
      {
	return null;
      }
    }

  public String examine()
    {
      return "Its' name is " + name;
    }
}
