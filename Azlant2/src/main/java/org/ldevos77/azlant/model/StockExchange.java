package org.ldevos77.azlant.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Stock exchange
 * 
 * -------------------------
 * StockExchange
 * -------------------------
 * String name
 * Country country
 * -------------------------
 * 
 * @author Ludovic Devos
 */
@Entity
public class StockExchange {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	/**
	 * Market Identifier Code (MIC)
	 * ISO 10383
	 */
	private String code;
	
	private String name;
	
	@OneToOne
	@JoinColumn(name = "country_id")
	private Country country;
	
	/**
	 * Creation date is technical field.
	 * It's update only one time : when the record is created.
	 */
	private LocalDateTime creationDate;
	
	/**
	 * Modification date is technical field.
	 * It's updated each time the record is updated.
	 */
	private LocalDateTime modificationDate;
	
	/**
	 * Private constructor, not for direct instantiation.
	 */
	protected StockExchange() {}

	public StockExchange(String code, String name, Country country) {
		if (code != "" && name != "" && country != null) {
			this.code = code;
			this.name = name;
			this.country = country;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	@PreUpdate
    private void setModificationDate() {
		this.setModificationDate(LocalDateTime.now());
	}
	
    @PrePersist
    private void setCreationDate() {
    	LocalDateTime localDateTime = LocalDateTime.now();
    	this.setCreationDate(localDateTime);
    	this.setModificationDate(localDateTime);
	}

	public long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public LocalDateTime getModificationDate() {
		return modificationDate;
	}

	public void setModificationDate(LocalDateTime modificationDate) {
		this.modificationDate = modificationDate;
	}
}
