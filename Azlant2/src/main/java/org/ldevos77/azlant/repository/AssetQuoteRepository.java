package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.AssetQuote;
import org.springframework.data.repository.CrudRepository;

public interface AssetQuoteRepository extends CrudRepository<AssetQuote, Long>, AssetQuoteRepositoryCustom {

}
