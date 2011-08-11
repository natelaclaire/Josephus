// By Nate LaClaire
public class Warrior {

	private String name; // warrior's name
	private int number; // warrior's initial position in list
	
	// constructors
	public Warrior() {
		this("", 0);
	}
	
	public Warrior(String name) {
		this(name, 0);
	}
	
	public Warrior(String name, int number) {
		this.name = name;
		this.number = number;
	}
	
	// accessors and mutators
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	// return a String representation of the Warrior object
	public String toString() {
		StringBuilder retval = new StringBuilder(name);
		
		// if there is a number, add it to the String
		if (number > 0) {
			retval.append(" (" + number + ")");
		}
		
		return retval.toString(); // format is "Name (#)"
	}
} // end class Warrior
