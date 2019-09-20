package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Country;
import org.ldevos77.azlant.model.StockExchange;
import org.ldevos77.azlant.model.TradingDay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Trading day repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class TradingDayRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private TradingDayRepository tradingDayRepository;
    
    @Test
    public void whenFindById_thenReturnTradingDay() {
        // given
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        TradingDay tradingDay = new TradingDay(stockExchange, LocalDate.now().minusDays(1));
        entityManager.persist(tradingDay);
        entityManager.flush();
     
        // when
        Optional<TradingDay> found = tradingDayRepository.findById(tradingDay.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getDate()).isEqualTo(tradingDay.getDate());
        }
    }

    @Test
    public void whenFindByStockExchangeAndDate_thenReturnTradingDay() {
        // given
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        TradingDay tradingDay = new TradingDay(stockExchange, LocalDate.now().minusDays(1));
        entityManager.persist(tradingDay);
        entityManager.flush();
     
        // when
        Optional<TradingDay> found = tradingDayRepository.findByStockExchangeAndDate(
            stockExchange, 
            tradingDay.getDate());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getDate()).isEqualTo(tradingDay.getDate());
        }
    }

    @Test
    public void whenFindDateMaxByStockExchange_thenReturnTradingDay() {
        // given
        Country country = new Country("MC", "My Country");
        entityManager.persist(country);
        StockExchange stockExchange = new StockExchange("MSE", "My Stock Exchange", country);
        entityManager.persist(stockExchange);
        entityManager.persist(new TradingDay(stockExchange, LocalDate.now().minusDays(3)));
        entityManager.persist(new TradingDay(stockExchange, LocalDate.now().minusDays(2)));
        TradingDay tradingDay = new TradingDay(stockExchange, LocalDate.now().minusDays(1));
        entityManager.persist(tradingDay);
        entityManager.flush();
     
        // when
        Optional<TradingDay> found = tradingDayRepository.findByStockExchangeIdAndDateMax(
            stockExchange.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getDate()).isEqualTo(tradingDay.getDate());
        }
    }

}
