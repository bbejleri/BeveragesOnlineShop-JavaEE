package de.uniba.dsg.dsam.persistence;

import de.uniba.dsg.dsam.model.Incentive;


/*
 * author: Bora Bejleri
 */

public interface IncentiveManagement {
	
	public void createInitiativePromo(String promoname);
	public void createInitiativeTrial(String trialname);

	public void editInitiative(String inc_name, String newname);
	
	public void deleteInitiative(String inc_name);
	
	
	
}
