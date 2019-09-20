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
 * Asset
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
	 * International Securities Identification Number (ISIN)
	 * ISO 6166
	 */
	private String code;
	
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
	
	public Asset(String code, String name,  
		AssetClass assetClass, StockExchange stockExchange, Company company) {
		if (code != "" && name != "" && assetClass != null && stockExchange != null && company != null) {
			this.code = code;
			this.name = name;
			this.assetClass = assetClass;
			this.stockExchange = stockExchange;
			this.company = company;
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

	@Override
    public String toString() {
        return String.format(
                "Asset[id=%d, code='%s', name='%s']",
                id, code, name);
    }

}
