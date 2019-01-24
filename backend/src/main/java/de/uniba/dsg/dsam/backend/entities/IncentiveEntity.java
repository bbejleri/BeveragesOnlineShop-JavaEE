package de.uniba.dsg.dsam.backend.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorType;

import java.io.Serializable;

/*
 * @author Bora Bejleri
 */


@Entity
@Table(name="iniciative")
//each class in the hierarchy is mapped to its table. 
//the only column which repeatedly appears in all the tables is the identifier, which will be used for joining them when needed.
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@NamedQueries({@NamedQuery(name="updateIncentive", query="UPDATE iniciative i SET i.init_name = :newname WHERE i.init_name = :actualname"),
               @NamedQuery(name="deleteIncentive", query="DELETE FROM iniciative i WHERE i.init_name = :actualname"),
               @NamedQuery(name="incentiveBelongsToBeverage", query="SELECT b.beverage_id FROM iniciative i JOIN i.beverage b WHERE b.init_name = :init_name_iniciative"),
               @NamedQuery(name="beverageBelongsToOrder", query="SELECT b.beverage_id FROM beverage b JOIN b.ordering_table o WHERE o.beverage_id = :beverage_id_beverage")})


public abstract class IncentiveEntity implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
    @Id
	@Column(name = "init_name")
	private String init_name;
	
	
	//Bidirectional association
    //Fetch type LAZY so the data won't be loaded at once
	@OneToMany(mappedBy = "init_name", fetch = FetchType.LAZY)
	private List<BeverageEntity> beverages;
	
	public String getName(){
		return init_name;
	}
	
	public void setName(String name){
		this.init_name = name;
	}
	
	public List<BeverageEntity> getBeverages(){
		return beverages;
	}
}
