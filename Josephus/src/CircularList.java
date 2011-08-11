// By Nate LaClaire
// Based on Fig. 22.3 (List.java) in Deitel & Deitel
import com.deitel.ch22.EmptyListException;

// class to represent one node in a list
class ListNode< T > 
{
	// package access members; List can access these directly
	T data; // data for this node
	ListNode< T > nextNode; // reference to the next node in the list

	// constructor creates a ListNode that refers to object
	ListNode( T object ) 
	{ 
		this( object, null ); 
	} // end ListNode one-argument constructor 

	// constructor creates ListNode that refers to the specified
	// object and to the next ListNode
	ListNode( T object, ListNode< T > node )
	{
		data = object;
		if (node == null) {
			nextNode = this;
		} else {
			nextNode = node;
		}

	} // end ListNode two-argument constructor

	// return reference to data in node
	T getData() 
	{ 
		return data; // return item in this node
	} // end method getData

	// return reference to next node in list
	ListNode< T > getNext() 
	{ 
		return nextNode; // get next node
	} // end method getNext
} // end class ListNode< T >

// class CircularList definition
public class CircularList< T >
{
	private ListNode< T > firstNode; // the first node
	private ListNode< T > currentNode; // the current node
	private String name; // string like "list" used in printing

	// constructor creates empty List with "list" as the name
	public CircularList() 
	{ 
		this( "list" ); 
	} // end List no-argument constructor

	// constructor creates an empty List with a name
	public CircularList( String listName )
	{
		name = listName;
		currentNode = firstNode = null;
	} // end List one-argument constructor

	// get next node in the list
	public T getNext() throws EmptyListException {
		// if the list is empty, throw an exception
		if (isEmpty()) {
			throw new EmptyListException(name);
		}

		// change the current node to the next node
		currentNode = currentNode.nextNode; 

		// return the data from the new current node
		return currentNode.getData(); 
	} // end method getNext()

	// get next Ith node in the list
	public T getNext(int position) throws EmptyListException {
		// if the list is empty, throw an exception
		if (isEmpty()) {
			throw new EmptyListException(name);
		}

		// loop through the nodes until moving the number of positions
		// supplied to the method
		for (int i = 0; i < position; i++) {
			currentNode = currentNode.nextNode;
		}

		// return the data from the new current node
		return currentNode.getData();
	} // end method getNext(int)

	// get the data at the specified position in the list
	// (counting from firstNode==0)
	public T get(int position) throws EmptyListException {
		// if the list is empty, throw an exception
		if (isEmpty()) {
			throw new EmptyListException(name);
		}

		// reset the position to the first node
		currentNode = firstNode;

		// move ahead the number of positions specified
		for (int i = 0; i < position; i++) {
			currentNode = currentNode.nextNode;
		}

		// return the data from the new current node
		return currentNode.getData();
	} // end method get

	// insert data at the current position in the list
	public void insert(T insertItem) {

		if (isEmpty()) {
			// if the list is empty, the new node is both first and current
			currentNode = firstNode = new ListNode< T >( insertItem );
		} else {
			// otherwise temporarily save a reference to the current node
			ListNode<T> tempNode;
			tempNode = currentNode;

			// insert the item as the current node
			currentNode = new ListNode< T >( insertItem, currentNode.nextNode );

			// set the previous current node's nextNode to the new item
			tempNode.nextNode = currentNode;
		}

	} // end method insert(T)

	// insert data at the specified position in the list
	// (counting from firstNode==0)
	public void insert(T insertItem, int position) {

		if (isEmpty()) {
			// if the list is empty, insert the new item as the current and first node
			currentNode = firstNode = new ListNode< T >( insertItem );
		} else {
			// otherwise...
			ListNode<T> tempNode; // will store a temporary reference to the previous node

			// reset the current position in the list to the first node
			currentNode = firstNode;

			// move ahead the specified number of spaces
			for (int i = 0; i < position-1; i++) {
				currentNode = currentNode.nextNode;
			}

			tempNode = currentNode; // save a reference to the current node

			// insert the new item as the new current node
			currentNode = new ListNode< T >( insertItem, currentNode.nextNode );

			// set the previous node's nextNode to the new node
			tempNode.nextNode = currentNode;
		}

	} // end method insert(T, int)

	// remove data at the Ith position from the current position
	// in the list
	public T removeNext(int position) throws EmptyListException {
		// if the list is empty, throw an exception
		if (isEmpty()) {
			throw new EmptyListException(name);
		}

		// temporarily save a reference to the current node
		ListNode<T> tempNode = currentNode;
		T returnData; // declare a reference to the return data object

		// loop through the nodes until reaching the position before
		// the node to be removed
		for (int i = 1; i < position; i++) {
			tempNode = currentNode;
			currentNode = currentNode.nextNode;
		}

		if (currentNode != currentNode.nextNode) {
			// if there's more than one node in the list

			if (currentNode==firstNode) {
				// if the current node is the first node, make the next node
				// the first one
				firstNode = currentNode.nextNode;
			}

			// set the previous node's nextNode to the next node
			tempNode.nextNode = currentNode.nextNode;

			// the data to return is the current node's data
			returnData = currentNode.getData();

			// the next node is now the current node
			currentNode = currentNode.nextNode;
		} else {
			// otherwise return the current node's data and remove it from the list
			returnData = currentNode.getData();
			currentNode = null;
			firstNode = null;
		}

		return returnData;
	} // end method removeNext(int)

	// reset the current position in the node to the first node
	public void reset() {
		currentNode = firstNode;
	} // end method reset()

	// determine whether list is empty
	public boolean isEmpty()
	{ 
		return firstNode == null; // return true if list is empty
	} // end method isEmpty

	// output list contents
	public void print()
	{
		if ( isEmpty() ) 
		{
			System.out.printf( "Empty %s\n", name );
			return;
		} // end if

		int i = 1;
		System.out.printf( "%s:\n", name ); // print the name of the list

		// create a temporary node reference and set it to the first node
		ListNode< T > current = firstNode; 

		// print the first node
		System.out.printf( "%d. %s\n", i, current.data );

		// advance to the next node
		current = current.nextNode;

		// while not back at beginning of list, output current node's data
		while ( current != firstNode ) 
		{
			i++;
			System.out.printf( "%d. %s\n", i, current.data );
			current = current.nextNode;
		} // end while

		System.out.println(); // blank line
	} // end method print
} // end class List< T >
