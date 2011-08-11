// By Nate LaClaire
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

// application class
public class Josephus {
	int numWarriors;  // number of warriors (including Josephus)
	int killingNumber; // number that soldiers will count off before the next commits suicide 
	int placeJosephus; // where to place Josephus in the circle
	CircularList<Horse> horses; // list of horses
	CircularList<Warrior> warriors; // list of living warriors
	CircularList<Warrior> deadWarriors; // list of dead warriors
	Scanner input; // used for collecting input
	
	// constructor
	public Josephus() {
		input = new Scanner(System.in);
		
		// Collect the necessary numbers. I finally gave up trying to get 
		// my code to work with a killing number of 1. 
		numWarriors = getInteger("Please enter the number of warriors (3-39): ", 3, 39);
		killingNumber = getInteger("Please enter the killing number (2-" + (numWarriors-1) + "): ", 2, numWarriors-1);
	} // end constructor
	
	// method to initiate the work
	public void run() {
		placeJosephus = numWarriors; // place Josephus at the end
		
		// populate the lists from the files
		loadWarriors();
		loadHorses();
	    
		System.out.println(); // blank line
		
		// print the lists
	    horses.print();
	    warriors.print();
		
		killWarriors(); // initiate the killing
	    
		System.out.println(); // blank line
	    warriors.print(); // print the list of remaining soldiers

	    // determine where to place Josephus
	    placeJosephus = warriors.get(0).getNumber();
	    System.out.println("Place Josephus at position " + placeJosephus);
	    
	    loadWarriors(); // reload the warriors from the file
	    
	    System.out.println(); // blank line
	    warriors.print(); // print the list of warriors 
	    
	    killWarriors(); // initiate the killing a second time
	    System.out.println(); // blank line
	    warriors.print(); // print the list of remaining warriors
	} // end method run()
	
	// helper method that collects integer inputs
	public int getInteger(String prompt, int min, int max) {
		int integer;
		
		// request the input
		System.out.print(prompt);
		integer = input.nextInt();
		
		// if the integer isn't in the required range, prompt the user
		// until he/she reads the instructions :-)
		while (integer < min || integer > max) {
			System.out.println("Please enter a valid integer from " + min + " to " + max + " inclusive.");
			System.out.print(prompt);
			integer = input.nextInt();
		}
		
		return integer;
	} // end method getInteger
	
	// perform the killing
	public void killWarriors() {
		// start with an empty list of dead warriors
		deadWarriors = new CircularList<Warrior>("Dead Warriors");
		warriors.reset(); // go back to the chief
	    Warrior killedWarrior = null; // used for temporarily storing the last warrior killed
	    
	    System.out.println("Passing the knife to " + warriors.get(0).getName() + "...");
	    
	    // loop until all but 1 warrior are dead
	    for (int i = 1; i < numWarriors; i++) {
	    	// kill the next warrior that falls under the killing number
	    	killedWarrior = warriors.removeNext(killingNumber);
	    	
	    	// add the dead warrior to our list of dead warriors
	    	deadWarriors.insert(killedWarrior);
	    	
	    	// display to the user
	    	System.out.printf("%d. %s commits suicide\n", i, killedWarrior.toString());
	    } // end of for loop
	} // end method killWarriors
	
	// load the horses from a file
	public void loadHorses() {
		BufferedReader horsesFile; // used for reading from file
		horses = new CircularList<Horse>("Horses"); // blank list
		
		try
	    {
			// From http://www.horses-and-horse-information.com/horsenames/a.shtml
			horsesFile = new BufferedReader(new FileReader("res/Horses.txt"));
			horses.insert(new Horse(horsesFile.readLine())); // only getting one
			horsesFile.close();
	      
	    }
	    catch (FileNotFoundException e)
	    {
	    	System.out.println("File Not Found while loading horses: " + e.getMessage());
	    }
	    catch (IOException e)
	    {
	    	System.out.println("I/O Error while loading horses: " + e.getMessage());
	    }
	} // end method loadHorses()
	
	// load warriors from file
	public void loadWarriors() {
		BufferedReader warriorsFile; // used for reading from file
		warriors = new CircularList<Warrior>("Warriors"); // empty list
		int warriorsCounter = 1; // counts the number of warriors loaded
	    try
	    {
			// From http://www.20000-names.com/warrior_names_fighter_names_male.htm
			// and http://translate.google.com (Chief's name is Albanian for Chief)
			warriorsFile = new BufferedReader(new FileReader("res/Warriors.txt"));
			
			// when the counter is larger than the number of warriors,
			// we've loaded them all
			while (warriorsCounter <= numWarriors) {
				if (warriorsCounter==placeJosephus) {
					// if this is where Josephus should go, put him there
					warriors.insert(new Warrior("Josephus", warriorsCounter));
				} else {
					// otherwise insert the next warrior in the file
					warriors.insert(new Warrior(warriorsFile.readLine(), warriorsCounter));
				}
				warriorsCounter++; // increment counter
			} // end of while loop
	      
			warriorsFile.close(); // close the file
	      
	    }
	    catch (FileNotFoundException e)
	    {
	    	System.out.println("File Not Found while loading warriors: " + e.getMessage());
	    }
	    catch (IOException e)
	    {
	    	System.out.println("I/O Error while loading warriors: " + e.getMessage());
	    }
	} // end method loadWarriors
	
	public static void main(String args[]) {
		// instantiate a new object of the application class
		// and run it
		Josephus josephusGame = new Josephus();
		josephusGame.run();
	} // end method main
} // end class Josephus
