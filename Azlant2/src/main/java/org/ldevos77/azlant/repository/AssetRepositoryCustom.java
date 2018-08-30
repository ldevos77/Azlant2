package org.ldevos77.azlant.repository;

import java.util.Optional;

import org.ldevos77.azlant.model.Asset;

public interface AssetRepositoryCustom {
	
    Optional<Asset> findByIsinCode(String isinCode);
    
}
