package OfflicenceShop;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Staff {

	private final String dataFile = "data/persons.csv";
	private final String separator = ";";
	
	private ArrayList<Person> persons = new ArrayList<Person>();
	
	public Staff() {
		loadPersons();
	}
	
	
	private void loadPersons() {
		try {
			Scanner in = new Scanner(new FileReader(dataFile));
			while (in.hasNextLine()) {				
				String[] values = in.nextLine().split(separator);
				persons.add(new Person(values[0], values[1], values[2], values[3]));
			}			
		} catch (FileNotFoundException e) {
			System.out.println("Data source file not found!");
		}			
	}
	
	public Person getPersonByUsername(String username) throws ItemNotFoundException {
		for (Person person : persons) {
			if (person.getUsername().equals(username)) {
				return person;
			}
		}
		
		throw new ItemNotFoundException();
	}

}