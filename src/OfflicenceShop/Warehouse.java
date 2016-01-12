package OfflicenceShop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Warehouse {
	
	private final String dataFile = "data/items.csv";
	private final String separator = ";";
	
	private ArrayList<Item> items = new ArrayList<Item>();
	
	private static Warehouse warehouse = null;
	
	private Warehouse() {
		loadItems();
	}
	
	public static Warehouse getWarehouse() {
		if (warehouse == null) {
			warehouse = new Warehouse();
		}
		
		return warehouse;
	}
	
	public ArrayList<Item> getAllItems() {
		if (items.isEmpty()) {
			loadItems();
		}
		
		return items;
	}
	
	public Warehouse addItem(Item item) {
		items.add(item);
		return this;
	}
	
	public Warehouse removeItem(Item item) {
		items.remove(item);
		return this;
	}
	
	public Warehouse save() {
		saveItems(items);
		return this;
	}
	
	public ArrayList<Item> search(String query) {
		ArrayList<Item> results = new ArrayList<Item>();
		for (Item item : items) {
			if (item.contains(query)) {
				results.add(item);
			}
		}
		
		return results;
	}
	
	private void loadItems() {
		try {
			Scanner in = new Scanner(new FileReader(dataFile));
			while (in.hasNextLine()) {				
				String[] values = in.nextLine().split(separator);
				Item item = new Item(Integer.valueOf(values[0]), values[1], Double.valueOf(values[4]));
				item.setProducer(values[2])
					.setVolume(Integer.valueOf(values[3]));
				items.add(item);
			}			
		} catch (FileNotFoundException e) {
			System.out.println("Data source file not found!");
		}			
	}
	
	private void saveItems(ArrayList<Item> items) {
		try {
			PrintWriter writer = new PrintWriter(dataFile, "UTF-8");			
			for (Item item : items) {
				writer.println(item.getID() + separator 
						+ item.getName() + separator
						+ item.getProducer() + separator
						+ item.getVolume() + separator
						+ item.getPrice());
			}			
			writer.close();
					
		} catch (FileNotFoundException e) {
			System.out.println("Data source file not found!");
		} catch (UnsupportedEncodingException e) {
			System.out.println("Data file cannot be created due to unknown encoding!");
		}
	}
	
	public Warehouse print() {
		return printItems(items);		
	}
	
	public Warehouse printItems(ArrayList<Item> items) {
		
		String leftAlignFormat = "| %-20s | %-4d | %-15s | %-4d | %-7.2f |%n";

		System.out.format("+----------------------+------+-----------------+------+---------+%n");
		System.out.format("| Product name         | ID   | Producer        | Vol. | Price ₺   |%n");
		System.out.format("+----------------------+------+-----------------+------+---------+%n");
		
		if (items.isEmpty()) {
			System.out.format("| NO ITEMS FOUND       |      |                 |      |         |%n");
		}
		for (Item item : items) {
			System.out.format(leftAlignFormat, item.getName(), item.getID(), item.getProducer(), item.getVolume(), item.getPrice());
		}
		
		System.out.format("+----------------------+------+-----------------+------+---------+%n");
		
		return this;
	}
	
	public ArrayList<Item> getItemsSortedByPrice() {
		ArrayList<Item> sortedList = items;
		Collections.sort(sortedList, new ItemPriceComparator());
		
		return sortedList;
	}
	
	public boolean itemIdExists(int id) {
		try {
			getItemById(id);
			return true;
		} catch (ItemNotFoundException e) {
			return false;
		}
	}
	
	public Item getItemById(int id) throws ItemNotFoundException {
		for (Item item : items) {
			if (item.getID() == id) {
				return item;
			}
		}
		
		throw new ItemNotFoundException();
	}
}
