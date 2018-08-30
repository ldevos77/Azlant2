package org.ldevos77.azlant.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Line of financial portfolio.
 * 
 * ---------------
 * PortfolioLine
 * ---------------
 * portfolio
 * asset
 * quantity
 * purchasePrice
 * tradingFees
 * ---------------
 * 
 * @author Ludovic Devos
 */
@Entity
public class PortfolioLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id")
	@JsonIgnore
	private Portfolio portfolio;
	
	@OneToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	
	private int quantity;
	
	@Column(name="purchase_price")
	private float purchasePrice;
	
	@Column(name="trading_fees")
	private float tradingFees;
	
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
	protected PortfolioLine() {}
	
	/**
	 * Portfolio constructor
	 * 
	 * @param portfolio : must not be null
	 * @param asset : must not be null
	 * @param quantity : must be higher than zero
	 * @param purchasePrice : must be higher or equal than zero
	 * @param tradingFees : must be higher or equal than zero
	 * @throws IllegalArgumentException if portfolio is null, or asset is null, or quantity <= 0,
	 * 		or purchasePrice < 0, or tradingFees < 0.
	 */
	public PortfolioLine(Portfolio portfolio, Asset asset, int quantity, 
			float purchasePrice, float tradingFees) {
		
		if (portfolio != null && asset != null && quantity > 0 && purchasePrice >= 0 && tradingFees >=0) {
			this.portfolio = portfolio;
			this.asset = asset;
			this.quantity = quantity;
			this.purchasePrice = purchasePrice;
			this.tradingFees = tradingFees;
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

	public void setId(long id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(float purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

	public float getTradingFees() {
		return tradingFees;
	}

	public void setTradingFees(float tradingFees) {
		this.tradingFees = tradingFees;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
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
