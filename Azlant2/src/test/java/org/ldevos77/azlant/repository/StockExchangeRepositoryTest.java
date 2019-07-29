package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Country;
import org.ldevos77.azlant.model.StockExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Stock Exchange repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class StockExchangeRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private StockExchangeRepository stockExchangeRepository;
    
    @Test
    public void whenFindById_thenReturnStockExchange() {
        // given
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        entityManager.flush();
     
        // when
        Optional<StockExchange> found = stockExchangeRepository.findById(stockExchange.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(stockExchange.getName());
        }
    }

    @Test
    public void whenFindByCode_thenReturnStockExchange() {
        // given
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        entityManager.flush();
     
        // when
        Optional<StockExchange> found = stockExchangeRepository.findByCode(stockExchange.getCode());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(stockExchange.getName());
        }
    }
}
