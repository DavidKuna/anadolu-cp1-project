package OfflicenceShop;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Person {

	protected String name;
	protected String surname;
	protected String password;
	protected String username;
	
	public Person(String name, String surname, String username, String password) {
		this.name = name;
		this.surname = surname;
		this.username = username;
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
}