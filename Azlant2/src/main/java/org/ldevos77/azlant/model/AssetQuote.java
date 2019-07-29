package org.ldevos77.azlant.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Asset quote
 * 
 * -------------------------
 * AssetQuote
 * -------------------------
 * Asset asset
 * LocalDate quotationDate
 * float price
 * -------------------------
 * 
 * @author Ludovic Devos
 */
@Entity
public class AssetQuote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "asset_id")
	@JsonIgnore
	private Asset asset;
	
	private LocalDate quotationDate;
	
	private float price;
	
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
	 * Needed for JPA.
	 */
	protected AssetQuote() {}
	
	public AssetQuote(Asset asset, LocalDate quotationDate, float price) {
		if (asset != null && quotationDate != null && price > 0) {
			this.asset = asset;
			this.quotationDate = quotationDate;
			this.price = price;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public java.time.LocalDate getQuotationDate() {
		return quotationDate;
	}

	public void setQuotationDate(java.time.LocalDate quotationDate) {
		this.quotationDate = quotationDate;
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
