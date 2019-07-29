package org.ldevos77.azlant.repository;

import java.util.Optional;

import org.ldevos77.azlant.model.AssetClass;;

public interface AssetClassRepositoryCustom {
	
    Optional<AssetClass> findByCode(String code);
    
}