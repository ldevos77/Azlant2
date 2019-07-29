package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.AssetClass;
import org.springframework.data.repository.CrudRepository;

public interface AssetClassRepository extends CrudRepository<AssetClass, Long>, AssetClassRepositoryCustom {

}
