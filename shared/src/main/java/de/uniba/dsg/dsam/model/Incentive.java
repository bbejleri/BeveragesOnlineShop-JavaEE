package de.uniba.dsg.dsam.model;

/*
 * author: Bora Bejleri
 */

public abstract class Incentive {
    
	private String name;
	
	public Incentive(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
