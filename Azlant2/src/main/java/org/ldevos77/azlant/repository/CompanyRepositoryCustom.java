package org.ldevos77.azlant.repository;

import java.util.Optional;

import org.ldevos77.azlant.model.Company;

public interface CompanyRepositoryCustom {
	
    Optional<Company> findByCode(String code);
    
}