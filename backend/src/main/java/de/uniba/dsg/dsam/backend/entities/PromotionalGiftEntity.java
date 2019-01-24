package de.uniba.dsg.dsam.backend.entities;

import javax.persistence.*;

/*
 * @author Bora Bejleri
 */

@Entity
@Table(name="promotional_gift")
@PrimaryKeyJoinColumn(name="init_name")
@NamedQueries({@NamedQuery(name="beveragesWithPromoGifts", query="SELECT b.beverage_id FROM beverage b JOIN b.promotional_gift p WHERE b.beverage_id = :beverage_id")})

public class PromotionalGiftEntity extends IncentiveEntity {
	
	
	private static final long serialVersionUID = 1L;
	//init_name is the primary key and also the foreign key from iniciative table since it inherits
	@Id
	@Column(name="init_name")
	private String name;
	
	public String getName(){
		return name;
	}
	
	public void setName(String promoname){
		this.name = promoname;
	}
	
}
