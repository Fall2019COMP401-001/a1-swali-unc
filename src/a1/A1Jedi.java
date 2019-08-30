package a1;

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
		private int customerCount;
		private int itemSoldCount;
		
		public Item( String name ) {
			itemName = name;
			customerCount = 0;
			itemSoldCount = 0;
		}
		
		/** This function does TWO THINGS:
		 * It increments (adds 1) to the count of customers
		 * It adds (by the parameter) to the count of how many items were sold
		 * 
		 * @param quantitySold how many did we still to this customer?
		 */
		public void NotifyItemSold( int quantitySold ) {
			++customerCount;
			itemSoldCount += quantitySold;
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
					customerCount != 0 ? Integer.toString(customerCount) : "No",
					itemSoldCount != 0 ? String.format("%d %s", itemSoldCount, itemName) : itemName
					);
		}
	}
}
