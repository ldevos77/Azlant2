package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetClass;
import org.ldevos77.azlant.model.AssetQuote;
import org.ldevos77.azlant.model.Company;
import org.ldevos77.azlant.model.Country;
import org.ldevos77.azlant.model.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Asset quote repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AssetQuoteRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AssetQuoteRepository assetQuoteRepository;

    @Test
    public void whenFindById_thenReturnAssetQuote() {
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
    	AssetQuote assetQuote = new AssetQuote(asset, LocalDate.now(), 100);
        entityManager.persist(assetQuote);
        entityManager.flush();
     
        // when
        Optional<AssetQuote> found = assetQuoteRepository.findById(assetQuote.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getPrice()).isEqualTo(assetQuote.getPrice());
        }
    }

    @Test
    public void whenFindByAsset_thenReturnAssetQuote() {
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
    	AssetQuote assetQuote = new AssetQuote(asset, LocalDate.now(), 100);
        entityManager.persist(assetQuote);
        entityManager.flush();
     
        // when
        Iterable<AssetQuote> found = assetQuoteRepository.findByAsset(asset);
     
        // then
        assertThat(found).isNotNull();
        assertThat(StreamSupport.stream(found.spliterator(), false).count()).isEqualTo(1);
        assertThat(found.iterator().next().getPrice()).isEqualTo(assetQuote.getPrice());
    }
}
