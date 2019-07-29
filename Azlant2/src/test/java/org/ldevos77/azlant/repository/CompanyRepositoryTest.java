package org.ldevos77.azlant.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.ldevos77.azlant.model.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests for Company repository
 * 
 * @author Ludovic Devos
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CompanyRepositoryTest {
	@Autowired
    private TestEntityManager entityManager;
 
    @Autowired
    private CompanyRepository companyRepository;
    
    @Test
    public void whenFindById_thenReturnCompany() {
        // given
    	Company company = new Company("MC", "My Companie");
        entityManager.persist(company);
        entityManager.flush();
     
        // when
        Optional<Company> found = companyRepository.findById(company.getId());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(company.getName());
        }
    }

    @Test
    public void whenFindByCode_thenReturnCompany() {
        // given
    	Company company = new Company("MC", "My Companie");
        entityManager.persist(company);
        entityManager.flush();
     
        // when
        Optional<Company> found = companyRepository.findByCode(company.getCode());
     
        // then
        assertThat(found).isNotNull();
        if (found.isPresent()) {
            assertThat(found.get().getName()).isEqualTo(company.getName());
        }
    }
}
