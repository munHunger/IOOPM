import java.io.*;
public class Prime
{
    public static void main(String args[])
    {
	if(args.length > 0)
	    new Prime(args[0]);
	else
	    new Prime("prime.txt");
    }

    FileWriter fileWriter;
    BufferedWriter bufferedWriter;

    public void init(String fileName)
    {
	try{
	    fileWriter = new FileWriter(fileName);
	    bufferedWriter = new BufferedWriter(fileWriter);
	}
	catch(Exception e)
	    {
		System.out.print(e);
		System.exit(0);
	    }
    }

    public Prime(String fileName)
    {
	int c = 3;
	try
	    {
		//Read from file
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		String intString = "3";
		String s;
		while((s = bufferedReader.readLine()) != null)
		    intString = s;

		c = Integer.parseInt(intString);
		bufferedReader.close();
	    }
	catch(FileNotFoundException e)
	    {
		System.out.println("Couldn't read from file, creating " + fileName);
		writeString(1 + "\n" + 2 + "\n", fileName);
	    }
	catch(Exception e)
	    {
		System.out.println(e);
		System.exit(0);
	    }
	while(c < 1001)
	    {
		if(isPrime(c))
		    {
			writeString(c + "\n", fileName);
		    }
		c++;
	    }
	if(bufferedWriter != null)
	    try{
		bufferedWriter.close();
	    }
	    catch(Exception e){
		System.out.print(e);
		System.exit(0);
	    }
    }
    
    public void writeString(String s, String fileName)
    {
	try
	    {
		if(bufferedWriter == null)
		    init(fileName);
		//Write to file
		bufferedWriter.write(s);
	    }
	catch(IOException e)
	    {
		System.out.println(s);
	    }
	catch(Exception e)
	    {
		System.out.println(e);
		System.exit(0);
	    }
    }

    public boolean isPrime(int n)
    {
	int sqrt = (int)Math.sqrt(n);
	for(int i = 2; i <= sqrt; i++)
	    {
		if((n % i) == 0)
		    return false;
	    }
	return true;
    }
}
