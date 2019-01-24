package de.uniba.dsg.dsam.backend.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import de.uniba.dsg.dsam.backend.entities.IncentiveEntity;
import de.uniba.dsg.dsam.backend.entities.PromotionalGiftEntity;
import de.uniba.dsg.dsam.backend.entities.TrialPackageEntity;
import de.uniba.dsg.dsam.model.Incentive;
import de.uniba.dsg.dsam.model.PromotionalGift;
import de.uniba.dsg.dsam.model.TrialPackage;
import de.uniba.dsg.dsam.persistence.IncentiveManagement;



//Stateless:
//1.The bean state should save no data for the client. 
//2.The bean performs a generic task for all clients invoking it.
//Remote since the clients run in different machines and different JVMs from the bean.


@Stateless
@Remote(IncentiveManagement.class)
public class IncentiveManagementBean implements IncentiveManagement {
	
    @PersistenceContext
    private static EntityManagerFactory entityManagerFactory =
    Persistence.createEntityManagerFactory("unit");
    private EntityManager em = entityManagerFactory.createEntityManager();
    
    private UserTransaction ut;
    private static final Logger LOGGER = Logger.getLogger(IncentiveManagementBean.class.getName());
    
    //creates a promotional gift
	public void createInitiativePromo(String promoname) {
	
    try {
    ut.begin();
	IncentiveEntity inc_promo = new PromotionalGiftEntity();
	inc_promo.setName(promoname);
	em.persist(inc_promo);
	ut.commit();
    }catch(Exception e){
    	LOGGER.warning("An Error occurred while creating Promotional Gift.");
    	try {
			ut.setRollbackOnly();
		} catch (IllegalStateException e1) {	
		} catch (SystemException e1) {}
    }
	
	}
	//creates a trial package
	public void createInitiativeTrial(String trialname){
		  try {
			    ut.begin();
				IncentiveEntity inc_trial = new TrialPackageEntity();
				inc_trial.setName(trialname);
				em.persist(inc_trial);
				ut.commit();
			    }catch(Exception e){
			    	LOGGER.warning("An Error occurred while creating Trial Package.");
			    	try {
						ut.setRollbackOnly();
					} catch (IllegalStateException e1) {	
					} catch (SystemException e1) {}
			    }
	}
    
	//takes the name of the incentive which we want to edit and the new name we want to set
	public void editInitiative(String inc_name, String newname) {
		
		try {	
		ut.begin();
		Query query = em.createNamedQuery("updateIncentive", IncentiveEntity.class);
		query.setParameter("newname", newname);
		query.setParameter("actualname", inc_name);
		query.executeUpdate();
		ut.commit();
		}catch(Exception e){
			LOGGER.warning("An Error occurred while editing Incentive.");
	    	try {
				ut.setRollbackOnly();
			} catch (IllegalStateException e1) {	
			} catch (SystemException e1) {}
		}
		
	}

	//takes the name of the incentive we want to delete
	public void deleteInitiative(String inc_name) {
		//to check if this initiative belongs to an order
		//check if that beloings to a beverage and if that beverage belongs to an order
		List<Integer>init_belongs_beverage = new ArrayList<Integer>();
		List<Integer>beverage_belongs_order = new ArrayList<Integer>();
		
		Query check1 = em.createNamedQuery("incentiveBelongsToBeverage", IncentiveEntity.class);
		check1.setParameter("init_name_iniciative", inc_name);
		init_belongs_beverage = check1.getResultList();
		
		if(init_belongs_beverage != null){
			for(int i=0; i< init_belongs_beverage.size(); i++){
	            Query check2 = em.createNamedQuery("beverageBelongsToOrder", IncentiveEntity.class);
		        check1.setParameter("beverage_id_beverage", i);
		        beverage_belongs_order = check2.getResultList();
		}
			
		if(beverage_belongs_order != null){
		
			System.out.println("Initiative: " + inc_name + "cannot be deleted because it belongs to an order.");
		    }
		}
		else {
			try{
		ut.begin();
		Query query = em.createNamedQuery("deleteIncentive", IncentiveEntity.class);
		query.setParameter("actualname",inc_name);
		query.executeUpdate();
		ut.commit();
		}catch(Exception e){
			LOGGER.warning("An Error occurred while deleting Incentive.");
	    	try {
				ut.setRollbackOnly();
			} catch (IllegalStateException e1) {	
			} catch (SystemException e1) {}
		}
	}
  }
}
	

