package a1;

import java.util.Scanner;

public class A1Novice {

	/** Main entrypoint. We need to take input of customer names
	 * and their bought items. We need to output their names
	 * (in a specific format) and their totals
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		// Your code follows here.
		int numCustomers;
		
		// How many customers do we have?
		// Will control how many times we ask for customer data
		numCustomers = scan.nextInt();
		
		for( int i = 0; i < numCustomers; ++i ) {
			
		}
	}
	
	/** A customer can be kept up in this object.
	 * Each customer has a name, a number of items they bought
	 * and the items themselves (name and cost)
	 * 
	 * @author swali
	 *
	 */
	private class CustomerData {
		
		/** An item is simply a name and the cost, nothing more.
		 * 
		 * @author swali
		 *
		 */
		private class Item {
		}
	}
}
