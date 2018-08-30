package org.ldevos77.azlant.repository;

import org.ldevos77.azlant.model.Asset;
import org.springframework.data.repository.CrudRepository;

public interface AssetRepository extends CrudRepository<Asset, Long>, AssetRepositoryCustom {

}
