package de.uniba.dsg.dsam.backend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import java.io.Serializable;

/*
 * @author Bora Bejleri
 */


@Entity
@Table(name = "orders")
@NamedQueries({@NamedQuery(name="allOrderIDs", query="SELECT o.order_id FROM ordering_table AS o"),
	           @NamedQuery(name="allPricesOfOrder", query="SELECT b.price FROM beverage b JOIN b.ordering_table o WHERE o.order_id = :order_id"),
	           @NamedQuery(name="allBeverageIDsOrdered", query="SELECT o.beverage_id FROM ordering_table AS o"),
	           @NamedQuery(name="beveragesWithoutIncentives", query="SELECT b.beverage_id FROM beverage b JOIN b.ordering_table o WHERE b.init_name IS NULL")
})

public class CustomerOrderEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id //Primary Key
	@GeneratedValue(strategy=GenerationType.AUTO)
	
	@Column(name = "order_id")
	private Long id;
	@Column(name = "issue_date")
	private java.sql.Date date;
	@Column(name= "nr_sold_beverages")
	private int nr_beverages;
	
	//Bidirectional association
	//Fetch type l = LAZY so the data won't be loaded at once
	@ManyToMany(mappedBy="orders", fetch = FetchType.LAZY)
	private List<BeverageEntity> beverages = new ArrayList<BeverageEntity>();
	
	
	public Long getId(){
		return id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	
	public java.sql.Date getDate() {
	    return date;
	  }

	public void setDate(java.sql.Date date) {
	    this.date = date;
	  }
	
	public int getSoldBeverages(){
		return nr_beverages;
	}
	
	public void setSoldBeverages(int nr_beverages){
		this.nr_beverages = nr_beverages;
		

		}
	}
	
