package org.ldevos77.azlant.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.ldevos77.azlant.model.StockExchange;
import org.ldevos77.azlant.model.TradingDay;
import org.springframework.data.jpa.repository.Query;

public interface TradingDayRepositoryCustom {

    Optional<TradingDay> findByStockExchangeAndDate(StockExchange stockExchange, LocalDate date);

    @Query(
        value = "select * from trading_day where stock_exchange_id=?1 "
                + "and date=( "
                + "  select max(date) from trading_day where stock_exchange_id=?1 "
                + ")",
        nativeQuery = true
    )
    Optional<TradingDay> findByStockExchangeIdAndDateMax(Long stockExchangeId);
	
}