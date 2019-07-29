package org.ldevos77.azlant.repository;

import java.util.Optional;

import org.ldevos77.azlant.model.StockExchange;

public interface StockExchangeRepositoryCustom {

    Optional<StockExchange> findByCode(String code);
	
}
