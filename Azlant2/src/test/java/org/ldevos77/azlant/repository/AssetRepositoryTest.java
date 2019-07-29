package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetClass;
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
 * Unit tests for Asset repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AssetRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AssetRepository assetRepository;

    @Test
    public void whenFindById_thenReturnAsset() {
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
        entityManager.flush();
     
        // when
        Optional<Asset> found = assetRepository.findById(asset.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getCode()).isEqualTo(asset.getCode());
        }
    }

    @Test
    public void whenFindByCode_thenReturnAsset() {
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
        entityManager.flush();
     
        // when
        Optional<Asset> found = assetRepository.findByCode(asset.getCode());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getCode()).isEqualTo(asset.getCode());
        }
    }
}
