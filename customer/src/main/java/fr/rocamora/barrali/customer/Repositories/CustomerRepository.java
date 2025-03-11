package fr.rocamora.barrali.customer.Repositories;

import fr.rocamora.barrali.customer.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
