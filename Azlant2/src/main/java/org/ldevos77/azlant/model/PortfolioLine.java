package org.ldevos77.azlant.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

/*
 * Line of financial portfolio.
 * 
 * ---------------
 * PortfolioLine
 * ---------------
 * id
 * quantity
 * purchasePrice
 * portfolio
 * ---------------
 */
@Entity
public class PortfolioLine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
	@JoinColumn(name = "portfolio_id")
	@JsonBackReference
	private Portfolio portfolio;
	
	@OneToOne
	@JoinColumn(name = "asset_id")
	private Asset asset;
	
	private int quantity;
	
	@Column(name="purchase_price")
	private float purchasePrice;
	
	@Column(name="trading_fees")
	private float tradingFees;

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

}
