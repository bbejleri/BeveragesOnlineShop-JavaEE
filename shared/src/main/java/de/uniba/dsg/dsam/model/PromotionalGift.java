package de.uniba.dsg.dsam.model;

/*
 * author: Bora Bejleri
 */

public class PromotionalGift extends Incentive {
	
	private String name;
	
	public PromotionalGift(String name){
		super(name);
	}
		
	public String getName(){
			return name;
		}

	public void setName(String name){
			this.name = name;
		}
	}

