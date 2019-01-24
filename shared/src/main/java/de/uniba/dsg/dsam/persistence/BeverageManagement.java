package de.uniba.dsg.dsam.persistence;

import java.util.List;

import de.uniba.dsg.dsam.model.Beverage;


/*
 * author: Bora Bejleri
 */

public interface BeverageManagement {
    //Abstract methods do not specify a body
    	
    public void create(String n, String i, String m, Double p, Integer q);
    
    //Overview of all beverages
    public List<Beverage> viewBeverages();
    
    //Assign incentive to a beverage
    public void assign(String inc_name, String bev_name);
    
}
