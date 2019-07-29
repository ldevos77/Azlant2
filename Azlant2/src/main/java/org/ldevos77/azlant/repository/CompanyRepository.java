package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.Company;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<Company, Long>, CompanyRepositoryCustom {

}