package OfflicenceShop;

import java.util.Scanner;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Auth {
	
	private Staff staff = new Staff();
	
	private Person loggedPerson = null;

	public Auth() {		
	}
	
	public Auth login() {
		System.out.println("\n+---- Login form -----+");
		System.out.println("(Test acccess: davidkuna/1234)");
		System.out.print("Enter username: ");
		Scanner in = new Scanner(System.in);
		try {
			Person person = staff.getPersonByUsername(in.nextLine());
			String password = new String(System.console().readPassword("Enter your password: "));
			if (person.getPassword().equals(password)) {
				loggedPerson = person;
				System.out.println("+---- " + person.getName() + " " + person.getSurname() + " logged -----+");	
			} else {
				System.out.println("Wrong password");
			}
			
		} catch (ItemNotFoundException e) {
			System.out.println("\n Person with this username not found");			
		} catch (NullPointerException e) {
			System.out.println("Reported bug occurred (295177). Try to execute program in command line.");
		}
		
		return this;
	}
	
	public boolean isLogged() {
		return (loggedPerson != null);
	}
	
	public Auth logout() {
		loggedPerson = null;
		System.out.println("\n+---- You were logged out -----+");	
		return this;
	}
	
	public Person getLoggedPerson() {
		return loggedPerson;
	}
}
