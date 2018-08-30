package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.Asset;
import org.ldevos77.azlant.model.AssetQuote;

public interface AssetQuoteRepositoryCustom {
	
	Iterable<AssetQuote> findByAsset(Asset asset);

}
