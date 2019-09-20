package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.StreamSupport;

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
    public void whenFindAll_thenReturnAllPortfolios() {
        // given
        entityManager.persist(new Portfolio("My portfolio 1"));
        entityManager.persist(new Portfolio("My portfolio 2"));
        entityManager.flush();
     
        // when
        Iterable<Portfolio> found = portfolioRepository.findAll();
     
        // then
        assertThat(found).isNotNull();
        assertThat(StreamSupport.stream(found.spliterator(), false).count())
            .isEqualTo(2);
    }

    @Test
    public void whenFindById_thenReturnPortfolio() {
        // given
    	Portfolio portfolio = new Portfolio("My portfolio");
        entityManager.persist(portfolio);
        entityManager.flush();
     
        // when
        Optional<Portfolio> found = portfolioRepository.findById(portfolio.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName())
                .isEqualTo(portfolio.getName());
        }
    }

    @Test
    public void whenSave_thenSavePortfolio() {
        // given
    	Portfolio portfolio = new Portfolio("My portfolio");
    
        // when
        Portfolio savedPortfolio = portfolioRepository.save(portfolio);
     
        // then
        assertThat(entityManager.find(Portfolio.class, savedPortfolio.getId()))
            .isNotNull();
        assertThat(entityManager.find(Portfolio.class, savedPortfolio.getId()).getName())
            .isEqualTo(portfolio.getName());
    }

    @Test
    public void whenDeleteById_thenDeletePortfolio() {
        // given
    	Portfolio portfolio = new Portfolio("My portfolio");
        entityManager.persist(portfolio);
        entityManager.flush();
     
        // when
        portfolioRepository.deleteById(portfolio.getId());
     
        // then
        assertThat(entityManager.find(Portfolio.class, portfolio.getId()))
            .isNull();
    }

}
