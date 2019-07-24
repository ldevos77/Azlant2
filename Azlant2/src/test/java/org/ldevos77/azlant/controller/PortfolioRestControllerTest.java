package org.ldevos77.azlant.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;
import org.ldevos77.azlant.repository.AssetQuoteRepository;
import org.ldevos77.azlant.repository.AssetRepository;
import org.ldevos77.azlant.repository.PortfolioLineRepository;
import org.ldevos77.azlant.repository.PortfolioRepository;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
@WebMvcTest
public class PortfolioRestControllerTest {

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

	MediaType contentType = new MediaType(
											MediaType.APPLICATION_JSON.getType(),
											MediaType.APPLICATION_JSON.getSubtype(),
											Charset.forName("utf8"));
	
	/**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * the list of portfolio is requested
	 */
    @Test
    public void getAllPortfolios() throws Exception {
    	List<Portfolio> portfolioList = new ArrayList<Portfolio>();
    	portfolioList.add(new Portfolio((long) 1,"Unit Test 1"));
    	portfolioList.add(new Portfolio((long) 2,"Unit Test 2"));
    	
    	Mockito.when(portfolioRepository.findAll())
	      .thenReturn(portfolioList);
    	
        this.mockMvc.perform(get("/portfolios/"))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$", hasSize(2)))
          .andExpect(jsonPath("$[0].name", is("Unit Test 1")));
    }
    
    /**
	 * Check if the application return a HHTP status equal to 200 (OK) if
	 * an existing portfolio ID is requested 
	 */
    @Test
    public void getExistingPortfolio() throws Exception {
    	Portfolio portfolio = new Portfolio((long) 1,"Unit Test 1");
	 
	    Mockito.when(portfolioRepository.findById((long) 1))
	      .thenReturn(Optional.ofNullable(portfolio));
	    
    	this.mockMvc.perform(get("/portfolios/1"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$.name", is("Unit Test 1")));
    }
    
    /**
	 * Check if the application return a HTTP status equal to 404 (Not Found) if
	 * an non existing portfolio ID is requested
	 */
    @Test
    public void getNonExistingPortfolio() throws Exception {
	    Mockito.when(portfolioRepository.findById((long) 99))
	      .thenReturn(Optional.empty());
	 
    	this.mockMvc.perform(get("/portfolios/99"))
    	  .andExpect(status().isNotFound());
    }
    
    /**
	 * Check if the application return a HTTP status equal to 201 (Created) if
	 * a new empty portfolio is created
	 */
    @Test
    public void saveNonExistingEmptyPortfolio() throws Exception {
    	Portfolio portfolio = new Portfolio("Unit Test 1");
   	 
	    Mockito.when(portfolioRepository.save(Mockito.any(Portfolio.class)))
	      .thenReturn(new Portfolio((long) 1, "Unit Test 1"));
	    
    	this.mockMvc.perform(post("/portfolios/")
                .contentType(contentType)
                .content(convertToJson(portfolio)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }
    
    /**
	 * Check if the application return a HTTP status equal to 200 (OK) if
	 * lines are requested for an existing portfolio
	 */
    @Test
    public void getExistingPortfolioLines() throws Exception {
		Portfolio portfolio = new Portfolio((long) 1,"Unit Test");
	    Asset asset = new Asset((long) 1,"Action 1");
    	List<PortfolioLine> portfolioLineList = new ArrayList<PortfolioLine>();
    	portfolioLineList.add(new PortfolioLine(portfolio, asset, 5, 10, 1));
    	portfolioLineList.add(new PortfolioLine(portfolio, asset, 2, 10, 2));
    	
	    Mockito.when(portfolioRepository.findById((long) 1))
	      .thenReturn(Optional.ofNullable(portfolio));
    	
	    Mockito.when(portfolioLineRepository.findByPortfolio(portfolio))
	      .thenReturn(portfolioLineList);
    	
    	this.mockMvc.perform(get("/portfolios/1/lines"))
    	  .andExpect(status().isOk())
    	  .andExpect(jsonPath("$", hasSize(2)))
    	  .andExpect(jsonPath("$[0].quantity", is(5)));
    }
    
    /**
	 * Check if the application return a HTTP status equal to 201 (Created) if
	 * a new portfolio line is created in an existing portfolio
	 */
    @Test
    public void savePortfolioLine() throws Exception {
    	Portfolio portfolio = new Portfolio((long) 1,"Unit Test");
	    Asset asset = new Asset((long) 1,"Action 1");
    	PortfolioLine portfolioLine = new PortfolioLine(portfolio, asset, 5, 10, 1);
    	
    	Mockito.when(portfolioLineRepository.save(Mockito.any(PortfolioLine.class)))
	      .thenReturn(new PortfolioLine((long) 1, portfolio, asset, 5, 10, 1));
    	
    	this.mockMvc.perform(post("/portfolios/"+portfolio.getId()+"/lines")
                .contentType(contentType)
                .content(convertToJson(portfolioLine)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
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
