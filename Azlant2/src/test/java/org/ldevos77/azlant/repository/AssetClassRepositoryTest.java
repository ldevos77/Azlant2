package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.AssetClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Asset class repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AssetClassRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private AssetClassRepository assetClassRepository;
    
    @Test
    public void whenFindById_thenReturnAssetClass() {
        // given
    	AssetClass assetClass = new AssetClass("MAC", "My Asset Class");
        entityManager.persist(assetClass);
        entityManager.flush();
     
        // when
        Optional<AssetClass> found = assetClassRepository.findById(assetClass.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(assetClass.getName());
        }
    }

    @Test
    public void whenFindByCode_thenReturnAssetClass() {
        // given
    	AssetClass assetClass = new AssetClass("MAC", "My Asset Class");
        entityManager.persist(assetClass);
        entityManager.flush();
     
        // when
        Optional<AssetClass> found = assetClassRepository.findByCode(assetClass.getCode());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(assetClass.getName());
        }
    }
}
