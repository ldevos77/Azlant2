package org.ldevos77.azlant.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class PortfolioControllerTest {

    private MockMvc mockMvc;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
    
    @Test
    public void getAllPortfolios() throws Exception {
        mockMvc.perform(get("/portfolios/")).andExpect(status().isOk());
    }
    
    @Test
    public void getExistingPortfolio() throws Exception {
        mockMvc.perform(get("/portfolios/1")).andExpect(status().isOk());
    }
    
    @Test
    public void getNonExistingPortfolio() throws Exception {
        mockMvc.perform(get("/portfolios/99")).andExpect(status().isNotFound());
    }
}
