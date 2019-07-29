package org.ldevos77.azlant.repository;

import java.util.Optional;

import org.ldevos77.azlant.model.Country;

public interface CountryRepositoryCustom {
	
    Optional<Country> findByCode(String code);
    
}
