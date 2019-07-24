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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetQuote;
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
	
	/**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * the list of asset is requested
	 */
    @Test
    public void getAllAssets() throws Exception {
    	List<Asset> assetList = new ArrayList<Asset>();
    	assetList.add(new Asset((long) 1,"Action 1"));
    	assetList.add(new Asset((long) 2,"Action 2"));
    	
    	Mockito.when(assetRepository.findAll())
	      .thenReturn(assetList);
    	
        this.mockMvc.perform(get("/assets/"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].name", is("Action 1")));
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset ID is requested 
	 */
    @Test
    public void getExistingAsset() throws Exception {
    	Asset asset = new Asset((long) 1,"Action 1");
   	 
	    Mockito.when(assetRepository.findById((long) 1))
	      .thenReturn(Optional.ofNullable(asset));
    	
    	this.mockMvc.perform(get("/assets/1"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$.name", is("Action 1")));
    }
    
    /**
	 * Check if the application return a HTTP status equal to 404 (Not Found) if
	 * an non existing asset ID is requested
	 */
    @Test
    public void getNonExistingAsset() throws Exception {
    	Mockito.when(assetRepository.findById((long) 99))
	      .thenReturn(Optional.empty());
    	
    	this.mockMvc.perform(get("/assets/99"))
    	  .andExpect(status().isNotFound());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset is requested using its Isin Code 
	 */
    @Test
    public void getExistingAssetByIsinCode() throws Exception {
    	Asset asset = new Asset((long) 1, "Action 1", "FR0000120404");
    	
	    Mockito.when(assetRepository.findByIsinCode("FR0000120404"))
	      .thenReturn(Optional.ofNullable(asset));
    	
    	this.mockMvc.perform(get("/assets?isin=FR0000120404"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$.name", is("Action 1")));
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * quotes are requested for an existing asset
	 */
    @Test
    public void getExistingAssetQuotes() throws Exception {
    	Asset asset = new Asset((long) 1,"Action 1");
    	List<AssetQuote> assetQuoteList = new ArrayList<AssetQuote>();
    	assetQuoteList.add(new AssetQuote((long) 1, asset, LocalDate.now(), 15));
    	assetQuoteList.add(new AssetQuote((long) 1, asset, LocalDate.now(), 20));
    	
	    Mockito.when(assetRepository.findById((long) 1))
	      .thenReturn(Optional.ofNullable(asset));
    	
	    Mockito.when(assetQuoteRepository.findByAsset(asset))
	      .thenReturn(assetQuoteList);
    	
    	this.mockMvc.perform(get("/assets/1/quotes"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$", hasSize(2)))
    	  .andExpect(jsonPath("$[0].price", is(15.0)));
    }
    
}
