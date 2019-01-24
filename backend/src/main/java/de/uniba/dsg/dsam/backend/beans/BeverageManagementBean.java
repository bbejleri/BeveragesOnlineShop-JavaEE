package de.uniba.dsg.dsam.backend.beans;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.uniba.dsg.dsam.backend.entities.BeverageEntity;
import de.uniba.dsg.dsam.model.Beverage;
import de.uniba.dsg.dsam.persistence.BeverageManagement;

//Stateless:
//1.The bean state should save no data for the client. 
//2.The bean performs a generic task for all clients invoking it.
//Remote since the clients run in different machines and different JVMs from the bean.

@Stateless
@Remote(BeverageManagement.class)

public class BeverageManagementBean implements BeverageManagement {
    
    @PersistenceContext
    private static EntityManagerFactory entityManagerFactory =
    Persistence.createEntityManagerFactory("unit");
    private EntityManager em = entityManagerFactory.createEntityManager();
    
    private UserTransaction ut;
    private static final Logger LOGGER = Logger.getLogger(BeverageManagementBean.class.getName());


	//add new beverage to database
    public void create(String n, String i, String m, Double p, Integer q){
    	try {
       ut.begin();
       BeverageEntity beverage = new BeverageEntity();
       beverage.setName(n);
       beverage.setInitName(i);
       beverage.setManufacturer(m);
       beverage.setPrice(p);
       beverage.setQuantity(q);
       em.persist(beverage);
       ut.commit();
    }catch(Exception e){
    	LOGGER.warning("An Error occurred while creating beverage.");
    	try {
			ut.setRollbackOnly();
		} catch (IllegalStateException e1) {	
		} catch (SystemException e1) {}
    }
    }
    
    //check all properties of a beverage
    //check again for the return type (?)
    public List<Beverage> viewBeverages(){
    	try {
    Query query = em.createNamedQuery("viewAll",BeverageEntity.class);
    List<Beverage> result = query.getResultList();
	return result;
    	}catch(Exception e){
    		LOGGER.warning("No beverages to show!");
    	}
		return null;
    }
    //Assign incentive to a beverage
	public void assign(String inc_name, String bev_name) {
		try {
		ut.begin();
		Query query = em.createNamedQuery("updateBeverageAssignIncentive", BeverageEntity.class);
		query.executeUpdate();
		ut.commit();
		}catch(Exception e){
			LOGGER.warning("An Error occurred while creating beverage.");
	    	try {
				ut.setRollbackOnly();
			} catch (IllegalStateException e1) {	
			} catch (SystemException e1) {}
		}
	}
   

}
