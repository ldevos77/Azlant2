package org.ldevos77.azlant.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Asset {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	private String name;
	
	@OneToOne
	@JoinColumn(name = "asset_class_id")
	private AssetClass assetClass;
	
	@Column(name="isin_code")
	private String isinCode;
	
	@OneToOne
	@JoinColumn(name = "stock_exchange_id")
	private StockExchange stockExchange;
	
	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	/*
	 * BEST PRACTICE
	 * Whenever a bidirectional association is formed, the application developer must make sure both sides are 
	 * in-sync at all times. 
	 * The addQuote() and removeQuote() are utilities methods that synchronize both ends whenever a child element 
	 * is added or removed.
	 */
	@OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference
	private List<AssetQuote> quotes = new ArrayList<>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsinCode() {
		return isinCode;
	}

	public void setIsinCode(String isinCode) {
		this.isinCode = isinCode;
	}

	public AssetClass getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(AssetClass assetClass) {
		this.assetClass = assetClass;
	}

	public StockExchange getStockExchange() {
		return stockExchange;
	}

	public void setStockExchange(StockExchange stockExchange) {
		this.stockExchange = stockExchange;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
    public void addQuote(AssetQuote quote) {
    	quotes.add(quote);
    	quote.setAsset( this );
    }

    public void removeQuote(AssetQuote quote) {
    	quotes.remove(quote);
    	quote.setAsset( null );
    }

	public List<AssetQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<AssetQuote> quotes) {
		this.quotes = quotes;
	}
}
