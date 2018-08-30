package org.ldevos77.azlant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Unit tests for Asset Rest controller
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AssetRestControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	/**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * the list of asset is requested
	 */
    @Test
    public void getAllAssets() throws Exception {
        this.mockMvc.perform(get("/assets/")).andExpect(status().isOk());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset ID is requested 
	 */
    @Test
    public void getExistingAsset() throws Exception {
    	this.mockMvc.perform(get("/assets/1")).andExpect(status().isOk());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 404 (Not Found) if
	 * an non existing asset ID is requested
	 */
    @Test
    public void getNonExistingAsset() throws Exception {
    	this.mockMvc.perform(get("/assets/99")).andExpect(status().isNotFound());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing asset is requested using its Isin Code 
	 */
    @Test
    public void getExistingAssetByIsinCode() throws Exception {
    	this.mockMvc.perform(get("/assets?isin=FR0000120404")).andExpect(status().isOk());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * quotes are requested for an existing asset
	 */
    @Test
    public void getExistingAssetQuotes() throws Exception {
    	this.mockMvc.perform(get("/assets/1/quotes")).andExpect(status().isOk());
    }
    
}
