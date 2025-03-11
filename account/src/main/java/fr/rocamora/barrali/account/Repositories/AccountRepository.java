package fr.rocamora.barrali.account.Repositories;

import fr.rocamora.barrali.account.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
}
