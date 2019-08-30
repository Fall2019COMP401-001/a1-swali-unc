package a1;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class A1Adept {

	/** Main entrypoint.
	 * 
	 * We need to take user input to create an inventory of items
	 * and their prices. Then we take in customer data including
	 * what item they bought and how many.
	 * 
	 * Finally, we will output our biggest spender, the smallest
	 * spender, and the average spent.
	 * 
	 * @param args Unused
	 */
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);

		/* This is what our expected input will look like
		 * and this comment block will describe most of the
		 * next few blocks of code taking input.
		 * 
		 * First we need to take in an inventory, starting
		 * with the number of items. Then we take in each
		 * item name, and the associated cost.
		 * 
		 * 2                  : Number of items
		 * Apple 0.25         : The item name and cost
		 * Jackfruit 0.75     : The item name and cost
		 * 
		 * Then we need to take in customer counts, and their
		 * names and how which items (and how many) they bought.
		 * 
		 * 2                  : Number of customers
		 * Mike Watt          : Name of customer
		 * 2                  : Number of items bought
		 * 2 KMPTicket        : He bought 2 tickets to see KMP
		 * 4 Apple            : And bought four apples
		 * Ketan Meyer-Patel  : Name of customer
		 * 1                  : Number of items bought
		 * 100 MikeWattTicket : He bought 100 tickets to see Mike Watt
		 * 
		 * Although it must be noted in the above example that
		 * the items bought are not listed in the inventory
		 * and therefore will throw an exception.
		 * 
		 * This ends the input block. After the input block,
		 * there will be calculations and an output block.
		 */
		
		// Instantiate our singleton inventory system which tracks prices
		ItemInventory itemCosts = ItemInventory.Instance();
		
		// Number of items
		int numberOfItemsInInventory = scan.nextInt();
		for( int i = 0; i < numberOfItemsInInventory; ++i ) {
			// For each item, take note of costs
			String itemName = scan.next();
			double itemCost = scan.nextDouble();
			
			itemCosts.StoreItemAndCost(itemName, itemCost);
		}
		
		// Number of customers
		int numberOfCustomers = scan.nextInt();
		
		// TODO:
		// Handle invalid input of <= 0
		
		// Create our customers shopping carts
		CustomerData customers[] = new CustomerData[numberOfCustomers];
		
		for( int i = 0; i < numberOfCustomers; ++i ) {
			// For each customer, take their name then items
			String firstName = scan.next();
			String lastName = scan.next();
			int numberOfItems = scan.nextInt();
			
			customers[i] = new CustomerData( numberOfItems, firstName, lastName );
			
			// Now we need their items
			for( int itemIndex = 0; itemIndex < numberOfItems; ++itemIndex ) {
				int itemQuantity = scan.nextInt();
				String itemName = scan.next();
				
				customers[i].SetItem( itemIndex, itemName, itemQuantity);
			}
		}
		
		// We're done with input
		scan.close();
		
		/* The next few blocks of code will be working on output
		 * 
		 * The output for this program is actually fairly simple.
		 * As described in the javadoc comment, we need to output
		 * who spent the most, the last, and the average of all
		 * customers.
		 * 
		 * Biggest: Ketan Meyer-Patel (1000.00)
		 * Smallest: Joe Bob (0.02)
		 * Average: 500.01
		 */
		List<Object> calculationResults = CalculateSpenders( customers );
		int indexLargestSpender = (int)calculationResults.get(0);
		int indexSmallestSpender = (int)calculationResults.get(1);
		double averageSpent = (double)calculationResults.get(2);
		
		System.out.printf( "Biggest: %s\n", customers[indexLargestSpender] );
		System.out.printf( "Smallest: %s\n", customers[indexSmallestSpender] );
		System.out.printf( "Average: %.2f\n", averageSpent );
	}
	
	/** This function will iterate through the passed customer list.
	 * The return value is a list of objects, but more specifically:
	 * 
	 * [0] is the index of the largest spender.
	 * [1] is the index of the smallest spender.
	 * [2] is a Double average spent from all customers.
	 * 
	 * @param customers Our array of customers
	 * @return List<Object> of a Integer indexLargestCust, Integer indexSmallestCust, Double average
	 */
	private static List<Object> CalculateSpenders( CustomerData customers[] ) {
		int indexLargestSpender = 0;
		int indexSmallestSpender = 0;
		double totalSpent = customers[0].GetTotalOfItems();
		
		// For every customer..
		for( int i = 1; i < customers.length; ++i ) {
			double customerTotal = customers[i].GetTotalOfItems();
			
			// Instead of falling GetTotalOfItems so many times, this could
			// likely be optimized by caching the largest total and smallest total.
			// But this file is large enough, and I don't know if autograder would
			// let me split this into multiple files.
			if( customerTotal > customers[indexLargestSpender].GetTotalOfItems() )
				indexLargestSpender = i;
			if( customerTotal < customers[indexSmallestSpender].GetTotalOfItems() )
				indexSmallestSpender = i;
			totalSpent += customerTotal;
		}
		
		return Arrays.asList(
				(Integer)indexLargestSpender,
				(Integer)indexSmallestSpender,
				(Double)(totalSpent/customers.length)
				);
	}
	
	/** A singleton object that stores information about items
	 * and their prices. We can look up the price of an item
	 * by the name, and we can also add items to our inventory.
	 * This is a singleton because of implementation specific
	 * reasons.
	 * 
	 * @author swali
	 *
	 */
	private static class ItemInventory {
		static ItemInventory _instance = null;
		Map<String, Double> itemCostTable;
		
		/** Constructor.
		 * 
		 * We're using ConcurrentHashMap to store item names
		 * and their associated cost.
		 */
		public ItemInventory() {
			itemCostTable = new ConcurrentHashMap<String, Double>();
		}
		
		/** Here we need to find the itemname given in the parameter
		 * and return the associated cost. Will throw an exception
		 * if we cannot find the item.
		 * 
		 * @param itemName Name of the item we want to look up
		 * @return double The cost of the item
		 */
		public double GetItemCost( String itemName ) {
			if( !itemCostTable.containsKey( itemName ) )
				throw new IllegalArgumentException(String.format("Specified item (%s) is not in the inventory", itemName));
			return itemCostTable.get( itemName );
		}
		
		/** This will store an item in our internal map. This item
		 * will then be lookup-able through the GetItemCost method.
		 * 
		 * @param itemName The name of the item
		 * @param itemCost The cost of the item
		 */
		public void StoreItemAndCost( String itemName, double itemCost ) {
			itemCostTable.put( itemName, itemCost );
		}
		
		public static ItemInventory Instance() {
			if( _instance == null )
				_instance = new ItemInventory();
			return _instance;
		}
	}
	
	/** A customer can be kept up in this object.
	 * Each customer has a name, a number of items they bought
	 * and the items themselves (name and cost)
	 * 
	 * @author swali
	 *
	 */
	private static class CustomerData {
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
		 */
		public void SetItem( int itemIndex, String itemName, int itemQuantity ) {
			if( itemIndex > this.itemCount || itemIndex < 0 ) // Invalid index
				throw new IllegalArgumentException( "Invalid item index" );
			
			this.items[itemIndex] = new Item( itemName, itemQuantity );
		}
		
		/** The format for how to output a customer is as follows:
		 * The accepted form of output looks as follows:
		 * Carrie Brownstein (0.95)
		 */
		public String toString() {
			return String.format( "%s %s (%.2f)", this.firstName, this.lastName, this.GetTotalOfItems() );
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
		 * reference multiple items. The cost is looked up in the ItemInventory
		 * instanced object.
		 * 
		 * @author swali
		 *
		 */
		private class Item {
			private String itemName; // We might need this for the future.
			private double itemCost;
			private int itemQuantity;
			
			/** This will allow us to set the properties of the item.
			 * The cost is looked up in the ItemInventory instanced object.
			 * 
			 * @param name The name of the item
			 * @param quantity How many do we have?
			 */
			public Item( String name, int quantity ) {
				this.itemName = name;
				this.itemQuantity = quantity;
				this.itemCost = ItemInventory.Instance().GetItemCost( name );
			}
			
			/** This function will return the cost of the item. NOTE, if the item
			 * cost has been modified, it will NOT be re-looked up. This object
			 * will maintain the original cost of the item, which would be ideal
			 * for refunds anyway (can't refund a sale item at full price later
			 * unless you wanna go bankrupt).
			 * 
			 * @return double The total cost of this item (incorporating quantity)
			 */
			public double getTotalCost() {
				return itemCost * itemQuantity;
			}
		}
	}
}
