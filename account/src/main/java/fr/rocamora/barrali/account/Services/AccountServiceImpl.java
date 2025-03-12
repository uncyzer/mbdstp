package fr.rocamora.barrali.account.Services;

import fr.rocamora.barrali.account.Dtos.AccountDTO;
import fr.rocamora.barrali.account.Mappers.AccountMapper;
import fr.rocamora.barrali.account.Repositories.AccountRepository;
import fr.rocamora.barrali.account.Entities.Account;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, AccountMapper accountMapper) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
    }

    @Override
    public List<AccountDTO> findAll() {
        return accountRepository
                .findAll()
                .stream()
                .map(accountMapper::toDTO)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public AccountDTO findById(String id) {
        Account account = accountRepository.findById(id).orElse(null);
        if (account != null)
            return accountMapper.toDTO(account);
        return null;
    }
}
