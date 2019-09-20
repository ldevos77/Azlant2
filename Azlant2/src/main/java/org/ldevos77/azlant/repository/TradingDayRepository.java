package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.TradingDay;
import org.springframework.data.repository.CrudRepository;

public interface TradingDayRepository extends CrudRepository<TradingDay, Long>, TradingDayRepositoryCustom {

}
