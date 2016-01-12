package OfflicenceShop;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Scanner;

public class Cart {

	private ArrayList<Item> items = new ArrayList<Item>();
	
	public Cart() {
	}
	
	public void menu() {
		System.out.println("\n+---- Cart actions -----+");					
		System.out.println("[0] Back");	
		System.out.println("[1] Add product");
		System.out.println("[2] Remove product");
		System.out.println("[3] Show product in cart");
		System.out.println("[4] Clear");
		System.out.println("[5] Purchase");
		
		Scanner in = new Scanner(System.in);
		int action = in.nextInt();
		switch (action) {
		case 0:
			return;
		case 1:
			addItem();
			break;
		
		case 2:
			removeItem();
			break;
				
		case 3:
			Warehouse.getWarehouse().printItems(items);
			printTotalPrice();
			break;
		
		case 4:
			items.clear();
			System.out.println("Cart is empty\n");
			break;
		
		case 5:						
			purchase();
			items.clear();
			break;	
			
		default:
			System.out.println("Wrong input");			
			break;
		}
			
		menu();
	}
	
	public Cart addItem() {
		Scanner in = new Scanner(System.in);
		System.out.println("+----- Add product to cart ----+");
		System.out.print("Enter product ID: ");
		try {
			int id = Integer.valueOf(in.nextLine());
			Item item = Warehouse.getWarehouse().getItemById(id);
			items.add(item);
			System.out.println("Product \"" + item + "\" added to cart");
		} catch (NumberFormatException e) {
			System.out.println("Wrong input. Number needed!");
			addItem();
		} catch (ItemNotFoundException e) {
			System.out.println("Product not found");
		}
		
		return this;
	}
	
	public Cart removeItem() {
		Scanner in = new Scanner(System.in);
		System.out.println("+----- Remove product from cart ----+");
		System.out.print("Enter product ID: ");
		try {
			int id = Integer.valueOf(in.nextLine());
			Item item = Warehouse.getWarehouse().getItemById(id);
			items.remove(item);
			System.out.println("Product \"" + item + "\" removed from cart");
		} catch (NumberFormatException e) {
			System.out.println("Wrong input. Number needed!");
			addItem();
		} catch (ItemNotFoundException e) {
			System.out.println("Product not found");
		}
		
		return this;
	}
	
	public ArrayList<Item> getItems() {
		return items;
	}
	
	public Cart printTotalPrice() {
		String leftAlignFormat = "| TOTAL PRICE                                            %-7.2f₺  |%n";		
		System.out.format(leftAlignFormat, getTotalPrice());
		System.out.format("+----------------------+------+-----------------+------+---------+%n");
		
		return this;
	}
	
	public double getTotalPrice() {
		double price = 0;
		for (Item item : items) {
			price += item.getPrice();
		}
		
		return price; 
	}
	
	private Cart purchase() {
		Scanner in = new Scanner(System.in);
		System.out.println("+----- Export order ----+");
		System.out.print("Enter file name: ");
		String filename = in.nextLine();
		String leftAlignFormat = "| %-20s | %-4d | %-15s | %-4d | %-7.2f |%n";
		
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			
			writer.format("+----------------------+------+-----------------+------+---------+%n");
			writer.format("| Product name         | ID   | Producer        | Vol. | Price ₺ |%n");
			writer.format("+----------------------+------+-----------------+------+---------+%n");			
			
			for (Item item : items) {
				writer.format(leftAlignFormat, item.getName(), item.getID(), item.getProducer(), item.getVolume(), item.getPrice());
			}			
			
			writer.format("+----------------------+------+-----------------+------+---------+%n");			
			writer.format("| TOTAL PRICE                                           %-7.2f₺ |%n", getTotalPrice());
			writer.format("+----------------------+------+-----------------+------+---------+%n");
			
			writer.close();
			
			System.out.println("Order was exported to file " + filename + "\n");
					
		} catch (FileNotFoundException e) {
			System.out.println("Data source file not found!");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Data file cannot be created due to unknown encoding!");
		}
		
		return this;
	}
}
