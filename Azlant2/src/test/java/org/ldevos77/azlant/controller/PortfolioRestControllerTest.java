package org.ldevos77.azlant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.exception.AssetNotFoundException;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;
import org.ldevos77.azlant.repository.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Unit tests for Portfolio Rest controller
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PortfolioRestControllerTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private AssetRepository assetRepository;
	
	private MediaType contentType = new MediaType(
											MediaType.APPLICATION_JSON.getType(),
											MediaType.APPLICATION_JSON.getSubtype(),
											Charset.forName("utf8"));
	
	/**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * the list of portfolio is requested
	 */
    @Test
    public void getAllPortfolios() throws Exception {
        this.mockMvc.perform(get("/portfolios/")).andExpect(status().isOk());
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing portfolio ID is requested 
	 */
    @Test
    public void getExistingPortfolio() throws Exception {
    	this.mockMvc.perform(get("/portfolios/1")).andExpect(status().isOk());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 404 (Not Found) if
	 * an non existing portfolio ID is requested
	 */
    @Test
    public void getNonExistingPortfolio() throws Exception {
    	this.mockMvc.perform(get("/portfolios/99")).andExpect(status().isNotFound());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 201 (Created) if
	 * a new empty portfolio is created
	 */
    @Test
    public void saveNonExistingEmptyPortfolio() throws Exception {
    	Portfolio portfolio = new Portfolio("Unit Test");
    	    	
    	this.mockMvc.perform(post("/portfolios/")
                .contentType(contentType)
                .content(convertToJson(portfolio)))
                .andExpect(status().isCreated());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 201 (Created) if
	 * a new portfolio is created
	 */
    @Ignore("In a logic of micro services, create a portfolio with his lines "
    		+ "are not supported anymore.")
    @Test
    public void saveNonExistingPortfolio() throws Exception {
    	Portfolio portfolio = new Portfolio("Unit test 2");
    	portfolio.addLine(new PortfolioLine(portfolio, assetRepository.findById((long) 1).orElseThrow(() -> new AssetNotFoundException()), 1, 0, 0));
    	portfolio.addLine(new PortfolioLine(portfolio, assetRepository.findById((long) 2).orElseThrow(() -> new AssetNotFoundException()), 1, 0, 0));
    	portfolio.addLine(new PortfolioLine(portfolio, assetRepository.findById((long) 3).orElseThrow(() -> new AssetNotFoundException()), 1, 0, 0));
    	
    	this.mockMvc.perform(post("/portfolios/")
                .contentType(contentType)
                .content(convertToJson(portfolio)))
                .andExpect(status().isCreated());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 200 (OK) if
	 * lines are requested for an existing portfolio
	 */
    @Test
    public void getExistingPortfolioLines() throws Exception {
    	this.mockMvc.perform(get("/portfolios/1/lines")).andExpect(status().isOk());
    }
    
    /**
	 * Convert Object to JSON String
	 * 
	 * Jackson library (in particular ObjectMapper class) is used to serialize java objects 
	 * to JSON.
	 * 
	 * TODO : export this method into external tool class
	 */
    private String convertToJson(Object o) throws JsonProcessingException {
    	ObjectMapper objectMapper = new ObjectMapper();
    	// Required to manage LocalDateTime (Java 8) 
    	objectMapper.registerModule(new JavaTimeModule());
    	return objectMapper.writeValueAsString(o);
    }

}