// classs för dörrar ett rum på enasidan (a1) o ett på andra
public class Door implements Entity{
   private Area a1;
   private Area a2;
   private boolean unlocked;

    // Metod som returnerar area 1
    public Area getArea1(){
	return a1;
    }

    // Metod som returnerar area 2
    public Area getArea2(){
	return a2;
    }

    // Metod som ger den area som inte motsvarar input (andra sidan av dörren)
    public Area getArea(Area a){
	if(!a.equals(a1))
	    return a1;
	else if(!a.equals(a2))
	    return a2;
	else
	    return null;
    }

    //Constructor
    public Door(Area a1, Area a2, boolean unlocked)
    {
    	this.a1 = a1;
    	this.a2 = a2;
    	this.unlocked = unlocked;
    }
    
    public boolean isUnlocked()
    {
    	return unlocked;
    }

    // Metod som kollar om en dörr är stängd och returnerar lämpligt meddelande
    public String examine(){
	if(unlocked)
	    return "The doors is unlocked.";
	else
	    return "The door is locked.";
    }

}
