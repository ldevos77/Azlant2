package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.StockExchange;
import org.springframework.data.repository.CrudRepository;

public interface StockExchangeRepository extends CrudRepository<StockExchange, Long>, StockExchangeRepositoryCustom {

}
