package org.ldevos77.azlant.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class AssetQuote {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private java.time.LocalDate quotationDate;
	
	private float price;
	
	@ManyToOne
	@JoinColumn(name = "asset_id")
	@JsonBackReference
	private Asset asset;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
}
