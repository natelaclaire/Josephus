// By Nate LaClaire
public class Horse {
	private String name; // horse's name

	// constructors
	public Horse() {
		name = "";
	}

	public Horse(String name) {
		this.name = name;
	}

	// accessors and mutators
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// return a String representation of the object
	public String toString() {
		return name;
	}
} // end class Horse
