package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Portfolio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Portfolio repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PortfolioRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PortfolioRepository portfolioRepository;
    
    @Test
    public void whenFindById_thenReturnPortfolio() {
        // given
    	Portfolio portfolio = new Portfolio("Unit Test");
        entityManager.persist(portfolio);
        entityManager.flush();
     
        // when
        Optional<Portfolio> found = portfolioRepository.findById(portfolio.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(portfolio.getName());
        }
    }
}
