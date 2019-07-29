package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Long>, CountryRepositoryCustom {

}