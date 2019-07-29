package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Country repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CountryRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CountryRepository countryRepository;
    
    @Test
    public void whenFindById_thenReturnCountry() {
        // given
    	Country country = new Country("MC", "My Companie");
        entityManager.persist(country);
        entityManager.flush();
     
        // when
        Optional<Country> found = countryRepository.findById(country.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(country.getName());
        }
    }

    @Test
    public void whenFindByCode_thenReturnCountry() {
        // given
    	Country country = new Country("MC", "My Companie");
        entityManager.persist(country);
        entityManager.flush();
     
        // when
        Optional<Country> found = countryRepository.findByCode(country.getCode());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(country.getName());
        }
    }
}
