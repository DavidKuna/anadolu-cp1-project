package OfflicenceShop;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Console {
	
	private static Auth auth = new Auth();
	private static Cart cart = new Cart();
	
	public static void main(String[] args){
		mainMenu();		
	}
	
	private static void mainMenu() {
		Scanner in = new Scanner(System.in);
		int action;
		

		System.out.println("Enter option: ");
		System.out.println("[1] Customer menu");
		System.out.println("[2] Staff menu");
		System.out.println("[3] About ");			
		System.out.println("[4] Exit ");
		
		action = in.nextInt();
		
		switch (action) {
			case 1:
				customerMenu();
				break;
			case 2:					
				staffMenu();
				break;
			
			case 3:
				System.out.println("\n+-------- About --------------+");
				System.out.println("|  Final Homework to BIM101   |");
				System.out.println("|                             |");
				System.out.println("|         Authors             |");
				System.out.println("| David Kuna  & Matěj Novotný |");
				System.out.println("| 99713165090 &  99176179122  |");
				System.out.println("|           2016              |");
				System.out.println("+-----------------------------+");
				mainMenu();
				break;
				
			case 4:
				System.out.println("Thank you for your shopping");
				System.exit(0);
				return;
				
			default:
				System.out.println("Wrong option selected\n");
				mainMenu();
				return;
		}	
	}
	
	private static void customerMenu(){
		System.out.println("\n+---- Customer menu -----+");			
		System.out.println("Enter option: ");
		System.out.println("[0] Back ");	
		System.out.println("[1] All products ");
		System.out.println("[2] Top 5 cheapest ");
		System.out.println("[3] Search product ");
		System.out.println("[4] My cart ");
		
		Scanner in = new Scanner(System.in);
		int action = in.nextInt();
		switch (action) {
		case 0:
			mainMenu();
			break;
		case 1:
			System.out.println("Product offer:");
			Warehouse.getWarehouse().print();
			break;
		
		case 2:
			System.out.println("Top 5 cheapest products:");
			Warehouse.getWarehouse().printItems(
					new ArrayList<Item>(Warehouse.getWarehouse().getItemsSortedByPrice().subList(0, 5)));
			break;
				
		case 3:
			searchItem();			
			break;
		
		case 4:
			cart.menu();		
			break;	
			
		default:
			System.out.println("Wrong input");
			break;
		}
		
		customerMenu();
	}
	
	private static void staffMenu(){
		if (!auth.isLogged()) {
			if (!auth.login().isLogged()) {
				mainMenu();
			}
		}
		System.out.println("\n+---- Staff menu (" + auth.getLoggedPerson().getName() 
			+ " " + auth.getLoggedPerson().getSurname() + ") -----+");			
		System.out.println("Enter option: ");
		System.out.println("[0] Back");	
		System.out.println("[1] All products");
		System.out.println("[2] Search product");
		System.out.println("[3] Add item");
		System.out.println("[4] Remove item");
		System.out.println("[5] Logout");	
		
		Scanner in = new Scanner(System.in);
		int action = in.nextInt();
		switch (action) {
		case 0:
			mainMenu();
			break;
		case 1:
			System.out.println("Product offer:");
			Warehouse.getWarehouse().print();
			break;
		
		case 2:
			searchItem();			
			break;
			
		case 3:
			createItem();
			break;
		
		case 4:
			removeItem();
			break;
			
		case 5:
			auth.logout();
			mainMenu();
			break;	
			
		default:
			System.out.println("Wrong option selected\n");
			break;
		}
		
		staffMenu();
	}
	
	private static void createItem() {
		String temp;
		int ID = 0;
		Scanner in = new Scanner(System.in);
		System.out.println("Item modifier:");
				
		boolean error;
		do {
	        error = false;
	        System.out.print("Insert item ID : ");
	        temp = in.nextLine();
			try {
				ID = Integer.parseInt(temp);
				
				if (Warehouse.getWarehouse().itemIdExists(ID)) {
					System.out.println("Item ID " + ID + " already exists");
				    error = true;
				}
			} catch (NumberFormatException e) {
				System.out.println("Wrong input, integer type needed");
			    error = true;
			}

		} while(error);
				
		System.out.print("Insert item name: ");
		String name = in.nextLine();
		double price = 0;			
		do {
	        error = false;
	        System.out.print("Insert item price: ");			        
			try {
				price = Double.valueOf(in.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Wrong input, number needed");
			    error = true;
			}

		} while(error);			
		
		Item item = new Item(ID, name, price);
		
		System.out.print("Insert item producer: ");		
		item.setProducer(in.nextLine());
				
		do {
	        error = false;
	        System.out.print("Insert item volume: ");			        
			try {
				item.setVolume(Integer.valueOf(in.nextLine()));	
			} catch (NumberFormatException e) {
				System.out.println("Wrong input, decimal number needed");
			    error = true;
			}

		} while(error);
		
		
		
		Warehouse.getWarehouse().addItem(item).save().print();		
	}
				
	private static void removeItem() {
		System.out.print("Enter ID of item for remove: ");
		Scanner in = new Scanner(System.in);
		try {
			int id = Integer.parseInt(in.nextLine());
			
			try {
				Item item = Warehouse.getWarehouse().getItemById(id);
				Warehouse.getWarehouse().removeItem(item).save();
			} catch(ItemNotFoundException e) {
				System.out.println("Item ID " + id + " doesn't exist");
			    staffMenu();
			}
		} catch (NumberFormatException e) {
			System.out.println("Wrong input, integer type needed");
			staffMenu();
		}
	}
	
	private static void searchItem() {
		System.out.print("Enter search string: ");
		Scanner in = new Scanner(System.in);
		
		ArrayList<Item> results = Warehouse.getWarehouse().search(in.nextLine());
		Warehouse.getWarehouse().printItems(results);
	}
}
