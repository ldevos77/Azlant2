package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.PortfolioLine;
import org.springframework.data.repository.CrudRepository;

public interface PortfolioLineRepository extends CrudRepository<PortfolioLine, Long>, PortfolioLineRepositoryCustom {

}
