package de.uniba.dsg.dsam.backend.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.uniba.dsg.dsam.backend.entities.BeverageEntity;
import de.uniba.dsg.dsam.backend.entities.CustomerOrderEntity;
import de.uniba.dsg.dsam.backend.entities.PromotionalGiftEntity;
import de.uniba.dsg.dsam.backend.entities.TrialPackageEntity;
import de.uniba.dsg.dsam.model.CustomerOrder;
import de.uniba.dsg.dsam.persistence.SalesManagement;

//The bean for the BI functionality
//Stateless:
//1.The bean state should save no data for the client. 
//2.The bean performs a generic task for all clients invoking it.
//Remote since the clients run in different machines and different JVMs from the bean.

@Stateless
@Remote(SalesManagement.class)

//BI functinallity bean
public class SalesManagementBean implements SalesManagement {
	
@PersistenceContext
private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("unit");
private EntityManager em = entityManagerFactory.createEntityManager();

    //overall summarized revenue of all orders
	public double ordersRevenue() {
		
		List<Integer> order_ids = new ArrayList<Integer>();
		List<Double> all_prices = new ArrayList<Double>();
		Double revenue = 0.0;
		
		Query query = em.createNamedQuery("allOrderIDs", CustomerOrderEntity.class);
		order_ids = query.getResultList();
		
		for (int i=0; i< order_ids.size(); i++){
		
			Query query1 = em.createNamedQuery("allPricesOfOrder", CustomerOrderEntity.class);
			query1.setParameter("order_id", i);
			all_prices = query1.getResultList();	
		}
		
		for(int j=0; j< all_prices.size(); j++){
			
			revenue += all_prices.get(j);
		}
		
		return revenue;
		
	}

	//revenue of all sold beverages broken down to the incentive types
	public String brokenDownRevenue() {
		
		List<Integer>beverage_ids_of_orders = new ArrayList<Integer>();
		List<Integer>beverage_ids_with_promo_gift = new ArrayList<Integer>();
		List<Integer>beverage_ids_with_trials = new ArrayList<Integer>();
		List<Integer>beverage_ids_without_incentives = new ArrayList<Integer>();
		List<Double>prices_of_beverages_promo_gift = new ArrayList<Double>();
		List<Double>prices_of_beverages_with_trials = new ArrayList<Double>();
		List<Double>prices_of_beverages_without_incentives = new ArrayList<Double>();
		Double sum1 = 0.0;
		Double sum2 = 0.0;
		Double sum3 = 0.0;
		
		Query query2 = em.createNamedQuery("allBeverageIDsOrdered", CustomerOrderEntity.class);
		beverage_ids_of_orders = query2.getResultList();
		
		for(int i=0; i< beverage_ids_of_orders.size(); i++){
			//checks for all beverage ids which contain a promotional gift
			Query query3 = em.createNamedQuery("beveragesWithPromoGifts", PromotionalGiftEntity.class);
			query3.setParameter("beverage_id", i);
			beverage_ids_with_promo_gift = query3.getResultList();
			if (beverage_ids_with_promo_gift != null){
				for(int j=0;j<beverage_ids_with_promo_gift.size();j++){
				Query query4 = em.createNamedQuery("pricesOfSelectedBeverages", BeverageEntity.class);
				query4.setParameter("beverage_id", j);
				prices_of_beverages_promo_gift = query4.getResultList();
				}
				for(int foo1 = 0; foo1<prices_of_beverages_promo_gift.size();foo1++){
				sum1 += prices_of_beverages_promo_gift.get(foo1);
				}
           }
			
			//checks for all beverage ids which contain a trail package
			Query query5 = em.createNamedQuery("beveragesWithTrails", TrialPackageEntity.class);
			query5.setParameter("beverage_id", i);
			beverage_ids_with_trials = query5.getResultList();
			if(beverage_ids_with_trials != null){
				for(int n=0; n< beverage_ids_with_trials.size(); n++){
					Query query6 = em.createNamedQuery("pricesOfSelectedBeverages", BeverageEntity.class);
					query6.setParameter("beverage_id", n);
					prices_of_beverages_with_trials = query6.getResultList();
					
				}
				for(int foo2 = 0; foo2<prices_of_beverages_with_trials.size();foo2++){
					sum2 += prices_of_beverages_with_trials.get(foo2);
					}
				
			} 
			
			//checks for all beverage ids which dont have any incentive assigned i.e init_name is null
			Query query7 = em.createNamedQuery("beveragesWithoutIncentives", CustomerOrderEntity.class);
			beverage_ids_without_incentives = query7.getResultList();
			if(beverage_ids_without_incentives != null){
				for(int k=0; k< beverage_ids_without_incentives.size(); k++){
					Query query8 = em.createNamedQuery("pricesOfSelectedBeverages", BeverageEntity.class);
					query8.setParameter("beverage_id", k);
					prices_of_beverages_without_incentives = query8.getResultList();
				}
				for(int foo3 = 0; foo3<prices_of_beverages_without_incentives.size();foo3++){
					sum3 += prices_of_beverages_without_incentives.get(foo3);
					}
			}
			
		}
		
		return "Revenue from beverages with incentives: Promotional gift: " + sum1 + "Trail Package:" + sum2 + "Without incentives assigned:" + sum3;
		
		
	}

}
