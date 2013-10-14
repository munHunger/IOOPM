public class Room extends Area{

    public String examine(){

	String s = super.examine();
	return s;
    }

    public Room(Door[] door){
	super.doors = door;

    }
    
    public Room()
    {
    	
    }

}
