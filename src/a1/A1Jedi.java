package a1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author swali
 *
 */
public class A1Jedi {

	/** Main entrypoint
	 * 
	 * We need to take input from the user of the items available
	 * and their associated costs. Then we need to take in customer
	 * data and what they bought. What needs to be output is each item
	 * and how many customers bought it, and how many of that item
	 * was sold.
	 * 
	 * @param args Unusued
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
		
		// Number of items
		int numberOfItemsInInventory = scan.nextInt();
		
		// Our inventory system is just a list of items
		Item itemList[] = new Item[numberOfItemsInInventory];
		
		for( int i = 0; i < numberOfItemsInInventory; ++i ) {
			// For each item, take note of costs
			String itemName = scan.next();
			scan.nextDouble(); // Discarded item cost
			
			itemList[i] = new Item( itemName );
		}
		
		// Number of customers
		int numberOfCustomers = scan.nextInt();
		
		// TODO:
		// Handle invalid input of <= 0
		
		for( int i = 0; i < numberOfCustomers; ++i ) {
			// For each customer, take their name then items
			String firstName = scan.next();
			String lastName = scan.next();
			int numberOfItems = scan.nextInt();
			
			// Now we need their items
			for( int itemIndex = 0; itemIndex < numberOfItems; ++itemIndex ) {
				int itemQuantity = scan.nextInt();
				String itemName = scan.next();
				
				int itemFoundIndex = FindItemIndex( itemList, itemName );
				if( itemFoundIndex == -1 ) {
					scan.close();
					throw new IllegalArgumentException( "Invalid item name specified (not found)");
				}
				itemList[itemFoundIndex].NotifyItemSold( firstName + lastName, itemQuantity );
			}
		}
		
		scan.close();
		
		/* The next few blocks of code will be working on output
		 * 
		 * The output for this program is actually fairly simple.
		 * As described in the javadoc comment, we need to output
		 * how many customers bought a specific item, and go through
		 * all items.
		 * 
		 * No customers bought Apple             : Don't worry about plurals
		 * 2 customers bought 5 Banana           :
		 * 1 customers bought 2 Milk             : Customers can remain plural
		 * 
		 */
		for( Item item : itemList ) {
			System.out.println( item );
		}
	}
	
	private static int FindItemIndex( Item itemlist[], String itemName ) {
		for( int i = 0; i < itemlist.length; ++i ) {
			if( itemlist[i].GetItemName().equals( itemName ) )
				return i;
		}
		
		return -1;
	}
	
	/** This object will keep up with an item. More importantly, it will
	 * keep up with how many customers bought it (incremented via
	 * NotifyItemSold) and how many of those items were bought (added
	 * via NotifyItemSold parameter- quantitySold).
	 * 
	 * @author swali
	 *
	 */
	private static class Item {
		private String itemName;
		private int itemSoldCount;
		private List<String> customerNames;
		
		public Item( String name ) {
			itemName = name;
			itemSoldCount = 0;
			customerNames = new ArrayList<String>();
		}
		
		/** This function does TWO THINGS:
		 * It increments (adds 1) to the count of customers
		 * It adds (by the parameter) to the count of how many items were sold
		 * 
		 * @param quantitySold how many did we still to this customer?
		 */
		public void NotifyItemSold( String customerName, int quantitySold ) {
			itemSoldCount += quantitySold;
			InsertIntoCustomerList( customerName );
		}
		
		/** We're gonna need this in order to identify items in an array.
		 * 
		 * @return String the name of this item
		 */
		public String GetItemName() {
			return itemName;
		}
		
		/** A formatted string of how many customers combined bought how many of this item.
		 * 
		 * No customers bought Apple
		 * 1 customers bought Apple
		 * 2 customers bought Apple
		 * 
		 */
		public String toString() {
			return String.format("%s customers bought %s",
					customerNames.size() != 0 ? Integer.toString(customerNames.size()) : "No",
					itemSoldCount != 0 ? String.format("%d %s", itemSoldCount, itemName) : itemName
					);
		}
		
		private void InsertIntoCustomerList( String customerName ) {
			for( String name : customerNames ) {
				if( name.contentEquals( customerName ) )
					return;
			}
			
			customerNames.add( customerName );
		}
	}
}
