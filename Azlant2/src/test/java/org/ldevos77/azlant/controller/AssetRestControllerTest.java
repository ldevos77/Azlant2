package org.ldevos77.azlant.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetClass;
import org.ldevos77.azlant.model.AssetQuote;
import org.ldevos77.azlant.model.Company;
import org.ldevos77.azlant.model.Country;
import org.ldevos77.azlant.model.StockExchange;
import org.ldevos77.azlant.model.TradingDay;
import org.ldevos77.azlant.repository.AssetQuoteRepository;
import org.ldevos77.azlant.repository.AssetRepository;
import org.ldevos77.azlant.repository.PortfolioLineRepository;
import org.ldevos77.azlant.repository.PortfolioRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for Asset Rest controller
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class AssetRestControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@MockBean
	private AssetRepository assetRepository;
	
	@MockBean
	private AssetQuoteRepository assetQuoteRepository;
	
	@MockBean
	private PortfolioRepository portfolioRepository;
	
	@MockBean
	private PortfolioLineRepository portfolioLineRepository;

    private AssetClass assetClass;
    private Company company;
    private Country country;
    private StockExchange stockExchange;

    @Before
    public void init() {
        this.assetClass = new AssetClass("ST","Stock");
        this.company = new Company("AC","ACCOR");
        this.country = new Country("FR", "France");
        this.stockExchange = new StockExchange("XPAR","Euronext Paris", country);
    }

	/**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * the list of asset is requested
	 */
    @Test
    public void whenGetAllAsset_thenReturnAssetList() throws Exception {
    	List<Asset> assetList = new ArrayList<Asset>();
    	assetList.add(new Asset("FR0000120404", "Stock 1", assetClass, stockExchange, company));
    	assetList.add(new Asset("FR0000120405", "Stock 2", assetClass, stockExchange, company));
    	
    	Mockito.when(assetRepository.findAll())
	      .thenReturn(assetList);
    	
        this.mockMvc.perform(get("/assets/"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].name", is("Stock 1")));
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset ID is requested 
	 */
    @Test
    public void whenGetAsset_thenReturnAsset() throws Exception {
    	Asset asset = new Asset("FR0000120404", "Stock 1", assetClass, stockExchange, company);
   	 
	    Mockito.when(assetRepository.findById(asset.getId()))
	      .thenReturn(Optional.ofNullable(asset));
    	
    	this.mockMvc.perform(get("/assets/"+Long.toString(asset.getId())))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$.name", is("Stock 1")));
    }
    
    /**
	 * Check if the application return a HTTP status equal to 404 (Not Found) if
	 * an non existing asset ID is requested
	 */
    @Test
    public void whenGetNonExistingAsset_thenReturnNotFoundStatus() throws Exception {
		long assetId = 99;

    	Mockito.when(assetRepository.findById(assetId))
	      .thenReturn(Optional.empty());
    	
    	this.mockMvc.perform(get("/assets/"+Long.toString(assetId)))
    	  .andExpect(status().isNotFound());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset is requested using its code 
	 */
    @Test
    public void whenGetAssetByCode_thenReturnAsset() throws Exception {
		// given
		Asset asset = new Asset("FR0000120404", "Stock 1", assetClass, stockExchange, company);
		
		// when
	    Mockito.when(assetRepository.findByCode("FR0000120404"))
	      .thenReturn(Optional.ofNullable(asset));
		
		// then
    	this.mockMvc.perform(get("/assets?code=FR0000120404"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$.name", is("Stock 1")));
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * quotes are requested for an existing asset
	 */
    @Test
    public void whenGetAssetQuotes_thenReturnAssetQuotesList() throws Exception {
		Asset asset = new Asset("FR0000120404", "Stock 1", assetClass, stockExchange, company);
		TradingDay tradingDay1 = new TradingDay(stockExchange, LocalDate.now().minusDays(1));
		TradingDay tradingDay2 = new TradingDay(stockExchange, LocalDate.now().minusDays(2));
    	List<AssetQuote> assetQuoteList = new ArrayList<AssetQuote>();
    	assetQuoteList.add(new AssetQuote(asset, tradingDay2, 15));
    	assetQuoteList.add(new AssetQuote(asset, tradingDay1, 20));
    	
	    Mockito.when(assetRepository.findById(asset.getId()))
	      .thenReturn(Optional.ofNullable(asset));
    	
	    Mockito.when(assetQuoteRepository.findByAsset(asset))
	      .thenReturn(assetQuoteList);
    	
    	this.mockMvc.perform(get("/assets/"+Long.toString(asset.getId())+"/quotes"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$", hasSize(2)))
    	  .andExpect(jsonPath("$[0].price", is(15.0)));
	}
    
}
