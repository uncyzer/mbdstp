package fr.rocamora.barrali.account.Mappers;

import fr.rocamora.barrali.account.Dtos.AccountDTO;
import fr.rocamora.barrali.account.Entities.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getBalance(),
                account.getDateCreated(),
                account.getCurrencyType(),
                account.getCustomer(),
                account.getCustomerId()
        );
    }

    public Account toEntity(AccountDTO accountDTO) {
        if (accountDTO == null) {
            return null;
        }
        return new Account(
                accountDTO.getId(),
                accountDTO.getBalance(),
                accountDTO.getDateCreated(),
                accountDTO.getCurrencyType(),
                accountDTO.getCustomer(),
                accountDTO.getCustomerId()
        );
    }
}
