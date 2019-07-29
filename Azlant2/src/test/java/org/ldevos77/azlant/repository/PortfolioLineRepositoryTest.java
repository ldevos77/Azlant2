package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetClass;
import org.ldevos77.azlant.model.Company;
import org.ldevos77.azlant.model.Country;
import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;
import org.ldevos77.azlant.model.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Portfolio line repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class PortfolioLineRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private PortfolioLineRepository portfolioLineRepository;
    
    @Test
    public void whenFindById_thenReturnPortfolioLine() {
        // given
        Company company = new Company("MC", "My Company");
        entityManager.persist(company);
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        AssetClass assetClass = new AssetClass("AC", "My Asset Class");
        entityManager.persist(assetClass);
        Asset asset = new Asset("FR00000000000", "My stock", assetClass, stockExchange, company);
        entityManager.persist(asset);
        Portfolio portfolio = new Portfolio("My portfolio");
        entityManager.persist(portfolio);
        PortfolioLine portfolioLine = new PortfolioLine(portfolio, asset, 5, 100, 2);
        entityManager.persist(portfolioLine);
        entityManager.flush();
     
        // when
        Optional<PortfolioLine> found = portfolioLineRepository.findById(portfolioLine.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getPurchasePrice()).isEqualTo(portfolioLine.getPurchasePrice());
        }
    }

    @Test
    public void whenFindByPortfolio_thenReturnPortfolioLine() {
        // given
        Company company = new Company("MC", "My Company");
        entityManager.persist(company);
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        AssetClass assetClass = new AssetClass("AC", "My Asset Class");
        entityManager.persist(assetClass);
        Asset asset = new Asset("FR00000000000", "My stock", assetClass, stockExchange, company);
        entityManager.persist(asset);
        Portfolio portfolio = new Portfolio("My portfolio");
        entityManager.persist(portfolio);
        PortfolioLine portfolioLine = new PortfolioLine(portfolio, asset, 5, 100, 2);
        entityManager.persist(portfolioLine);
        entityManager.flush();
     
        // when
        Iterable<PortfolioLine> found = portfolioLineRepository.findByPortfolio(portfolio);
     
        // then
        assertThat(found).isNotNull();
        assertThat(StreamSupport.stream(found.spliterator(), false).count()).isEqualTo(1);
        assertThat(found.iterator().next().getPurchasePrice()).isEqualTo(portfolioLine.getPurchasePrice());
    }
}
