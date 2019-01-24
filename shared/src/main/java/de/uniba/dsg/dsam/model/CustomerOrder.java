package de.uniba.dsg.dsam.model;

import java.time.LocalDate;
import java.util.Date;

/*
 * author: Bora Bejleri
 */

import java.util.List;

public class CustomerOrder {

    private LocalDate issueDate;
    private List<Beverage> orderItems;
    
	public LocalDate getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(LocalDate localDate) {
		this.issueDate = localDate;
	}
	public List<Beverage> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<Beverage> orderItems) {
		this.orderItems = orderItems;
	}
    
    
}
