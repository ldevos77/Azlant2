package org.ldevos77.azlant;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.controller.PortfolioRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
public class ApplicationTests {
	
	@Autowired
    private PortfolioRestController portfolioController;

	@Test
	public void contextLoads() {
		
		/*
		 * Check if portfolio controller is created by Spring
		 */
		assertThat(portfolioController).isNotNull();
	}

}
