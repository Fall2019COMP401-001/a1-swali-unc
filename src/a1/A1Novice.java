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
		private String firstName;
		private String lastName;
		private int itemCount;
		private Item items[];
		
		/** Initializes our customer's data
		 * 
		 * @param numItems The number of items they're buying
		 * @param firstname Their first name (can be just 1 letter or full first name)
		 * @param lastname Their full last name
		 */
		public CustomerData( int numItems, String firstname, String lastname ) {
			this.itemCount = numItems;
			this.firstName = firstname;
			this.lastName = lastname;
			
			this.items = new Item[itemCount];
			
			// Error checking
			if( this.firstName.length() == 0 || this.lastName.length() == 0 )
				throw new IllegalArgumentException( "First or last name is blank" );
		}
		
		/** Sets an item in our customer's inventory
		 * 
		 * @param itemIndex This is the index in the array for the item
		 * @param itemName Name of the item
		 * @param itemQuantity How many of said item do we have?
		 * @param itemCost What is the cost of ONE item
		 */
		public void SetItem( int itemIndex, String itemName, int itemQuantity, double itemCost ) {
			if( itemIndex > this.itemCount || itemIndex <= 0 ) // Invalid index
				throw new IllegalArgumentException( "Invalid item index" );
			
			this.items[itemIndex].SetItem( itemName, itemQuantity, itemCost);
		}

		/** The customer's name must be formatted as follows:
		 * F. Lastname
		 * - Where F is the first letter of the first name,
		 * - Where Lastname is their last name.
		 * 
		 * @return The formatted name
		 */
		private String GetFormattedCustomerName() {
			return String.format( "%c. %s",
					firstName.charAt(0), lastName );
		}

		/** Loops through the customer's items and returns
		 * the total cost of all items.
		 * 
		 * @return The total cost of this customer's items
		 */
		private double GetTotalOfItems() {
			double total = 0;
			
			for( Item item : items ) {
				total += item.getTotalCost();
			}
			
			return total;
		}
		
		/** An item is simply a name, quantity, and the cost. nothing more.
		 * Since there is a quantity field, a single Item object can technically
		 * reference multiple items.
		 * 
		 * @author swali
		 *
		 */
		private class Item {
			private String itemName;
			private int itemQuantity;
			private double itemCost;
			
			/** This will allow us to set the properties of the item.
			 * 
			 * @param name The name of the item
			 * @param quantity How many do we have?
			 * @param cost What is the INDIVIDUAL cost of this item.
			 */
			public void SetItem( String name, int quantity, double cost ) {
				this.itemName = name;
				this.itemQuantity = quantity;
				this.itemCost = cost;
			}
			
			/** 
			 * 
			 * @return double The total cost of this item (incorporating quantity)
			 */
			public double getTotalCost() {
				return itemCost * itemQuantity;
			}
		}
	}
}
