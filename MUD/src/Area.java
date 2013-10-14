public abstract class Area implements Entity{

    // alla areas ska ha en array med dörrar (max fyra)
    Door[] doors;

    public Area()
    {
    	doors = new Door[4];
    }
    
    public void setDoors(Door[] doors)
    {
    	this.doors = doors;
    }
    
    public Door[] getDoors()
    {
    	return doors;
    }
    
    // switch-sats som kollar vilket vädersträck med en siffra (index i array)
    private String getDir(int a){
	switch(a){
	case 0: return "West";
	case 1: return "North";
	case 2: return "East";
	case 3: return "South";
	default: return "";
	}
    }
    
    // loop som loopar igenom doors och skriver ut info om dörrarna med hjälp av getArea och classen Door's examine
    public String examine(){
	String s = "";
	for(int i = 0; i<4; i++){
	    if(doors[i] != null){
		s += "There is a door to the " + getDir(i);
		if(doors[i].getArea(this) instanceof Room)
		    s += " that leads to a room.";
		else
		    s += " that leads to a hallway.";

		s += doors[i].examine();
		s += "\n";

	    }else{
		//insert logic here
	    }
	}
	return s;
    }

}
