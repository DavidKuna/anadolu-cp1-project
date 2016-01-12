package OfflicenceShop;

/**
 * Final Homework to BIM101
 * 
 * @author David Kuna 99713165090 - programmer
 * @author Matěj Novotný 99176179122 - programmer
 */
public class Item {
	private int ID;
	private String name;
	private double price;
	private String producer;
	private int volume;
	
	public Item (int ID, String name, double price){
		this.ID = ID;
		this.name = name;
		this.setPrice(price);
	}
	
	public Item setProducer(String producer) {
		this.producer = producer;
		return this;
	}
	
	public String getProducer() {
		return producer;
	}
 
	@Override
	public String toString() {
		return name + " (" + volume + "ml) " + price + "₺";		
	}

	public double getPrice() {
		return price;
	}

	public Item setPrice(double price) {
		this.price = price;
		return this;
	}

	public int getID() {
		return ID;
	}

	public int getVolume() {
		return volume;
	}

	public Item setVolume(int volume) {
		this.volume = volume;
		return this;
	}
	
	public String getName() {
		return name;
	}
 
	public boolean contains(String query) {
		int id = 0;
		try { 
			id = Integer.valueOf(query);
		} catch (NumberFormatException e) {
			id = 0;
		}
		
		return (name.contains(query) || producer.contains(query) || ID == id);
	}
}
