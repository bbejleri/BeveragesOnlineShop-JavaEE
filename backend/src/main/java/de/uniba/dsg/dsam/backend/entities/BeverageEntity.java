package de.uniba.dsg.dsam.backend.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import java.io.Serializable;

/*
 * @author Bora Bejleri
 */

@Entity
@Table(name="beverages")
@NamedQueries({@NamedQuery(name="viewAll", query="SELECT b FROM beverages AS b"),
	           @NamedQuery(name="pricesOfSelectedBeverages", query="SELECT b.price FROM beverage b WHERE b.beverage_id = :beverage_id"),
               @NamedQuery(name="updateBeverageAssignIncentive", query="UPDATE beverage b SET init_name = :inc_name WHERE b.beverage_name = :bevname")
})


public class BeverageEntity implements Serializable {
	
	public BeverageEntity() {}

	
	private static final long serialVersionUID = 1L;
	@Id //Primary Key
	@GeneratedValue(strategy=GenerationType.AUTO)

	
	@Column(name = "beverage_id", nullable = false)
	private Long id;
    @Column (name = "beverage_name")
    private String name;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    private double price;
    @Column(name = "manufacturer")
    private String manufacturer;
    
    
    //Bidirectional association
    //Fetch type l = LAZY so the data won't be loaded at once
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="init_name", referencedColumnName="init_name")
    private String init_name;
    
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ordering_table",
	joinColumns = { @JoinColumn(name = "beverage_id") },
	inverseJoinColumns = { @JoinColumn(name = "order_id") })
    private List<CustomerOrderEntity> orders = new ArrayList<CustomerOrderEntity>();
    
    
   public Long getId(){
	   return id;
   }
   
   public void setId(Long id){
	   this.id = id;
   }
   
   public String getName(){
	   return name;
   }
   
   public void setName(String beverage_name){
	   this.name = beverage_name;
   }
   
   public int getQuantity(){
	   return quantity;
   }
   
   public void setQuantity(int quantity){
	   this.quantity = quantity;
   }
   
   public String getManufacturer(){
	   return manufacturer;
   }
   
   public void setManufacturer(String manufacturer){
	   this.manufacturer = manufacturer;
   }
   
   public double getPrice(){
	   return price;
   }
   
   public void setPrice(Double p){
	   this.price = p;
   }
   
   public String getInitName(){
	   return init_name;
   }
   
   public void setInitName(String init_name){
	   this.init_name = init_name;
   }
}
