package de.uniba.dsg.dsam.persistence;


/*
 * @author Bora Bejleri
 */

public interface SalesManagement {
	
	//overall summarized revenue of all orders
	public double ordersRevenue();
	//revenue of all sold beverages broken down to the incentive types
	public String brokenDownRevenue();
	
}
