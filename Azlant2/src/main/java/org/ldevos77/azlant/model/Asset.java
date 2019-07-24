package org.ldevos77.azlant.model;

import java.time.LocalDateTime;
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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Asset
 * 
 * -------------------------
 * Asset
 * -------------------------
 * String name
 * AssetClass assetClass
 * String isinCode
 * StockExchange stockExchange
 * Company company
 * List<AssetQuote> quotes
 * -------------------------
 * 
 * @author Ludovic Devos
 */
@Entity
public class Asset {

	/**
	 * Asset ID
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	/**
	 * Asset name
	 */
	private String name;
	
	/**
	 * Asset class
	 */
	@OneToOne
	@JoinColumn(name = "asset_class_id")
	private AssetClass assetClass;
	
	/**
	 * International ISIN Code
	 */
	@Column(name="isin_code")
	private String isinCode;
	
	/**
	 * Stock Exchange where asset is quoted
	 */
	@OneToOne
	@JoinColumn(name = "stock_exchange_id")
	private StockExchange stockExchange;
	
	/**
	 * Company assigned to asset
	 */
	@OneToOne
	@JoinColumn(name = "company_id")
	private Company company;
	
	/**
	 * Quotation list
	 * 
	 * BEST PRACTICE
	 * Whenever a bidirectional association is formed, the application developer must make sure both sides are 
	 * in-sync at all times. 
	 * The addQuote() and removeQuote() are utilities methods that synchronize both ends whenever a child element 
	 * is added or removed.
	*/
	@OneToMany(mappedBy = "asset", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnore
	private List<AssetQuote> quotes = new ArrayList<>();

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
	protected Asset() {}
	
	public Asset(Long id, String name) {
		if (id > 0 && name != "") {
			this.id = id;
			this.name = name;
		}
		else {
			throw new IllegalArgumentException();
		}
	}
	
	public Asset(Long id, String name, String isinCode) {
		if (id > 0 && name != "" && isinCode != "") {
			this.id = id;
			this.name = name;
			this.isinCode = isinCode;
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

	public List<AssetQuote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<AssetQuote> quotes) {
		this.quotes = quotes;
	}

}
