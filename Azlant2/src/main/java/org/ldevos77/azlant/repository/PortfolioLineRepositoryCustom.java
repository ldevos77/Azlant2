package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.Portfolio;
import org.ldevos77.azlant.model.PortfolioLine;

public interface PortfolioLineRepositoryCustom {

	Iterable<PortfolioLine> findByPortfolio(Portfolio portfolio);
	void deleteByPortfolio(Portfolio portfolio);
	
}
