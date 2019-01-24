package de.uniba.dsg.dsam.model;

/*
 * @author: Bora Bejleri
 */

public class Beverage {
    private String manufacturer;
    private String name;
    private int quantity;
    private double price;
    private String incentive;

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getIncentive() {
		return incentive;
	}

	public void setIncentive(String init_name) {
		this.incentive = init_name;
	}
    
    
    
    
}
