package de.uniba.dsg.dsam.model;

/*
 * author: Bora Bejleri
 */

public class TrialPackage extends Incentive {
	
	public TrialPackage(String name) {
		super(name);
		
	}
	
	private String name;
	
		
	public String getName(){
			return name;
		}

	public void setName(String name){
			this.name = name;
		}
}
