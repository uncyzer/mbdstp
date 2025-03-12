package fr.rocamora.barrali.account.Controllers;

import fr.rocamora.barrali.account.Client.CustomerClient;
import fr.rocamora.barrali.account.Entities.Account;
import fr.rocamora.barrali.account.Repositories.AccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
public class AccountController {
    private final AccountRepository accountRepository;
    private final CustomerClient customerClient;

    public AccountController(AccountRepository accountRepository, CustomerClient customerClient) {
        this.accountRepository = accountRepository;
        this.customerClient = customerClient;
    }

    @GetMapping("/accounts")
    public List<Account> getAccounts() {
        List<Account> accountList = accountRepository.findAll();
        accountList.forEach(
                account -> {
                    account.setCustomer(customerClient.getCustomer(account.getCustomerId()));
                }
        );
        return accountList;
    }

    @GetMapping("/accounts/{id}")
    public Account getAccount(@PathVariable String id) {
        Account accountInstance = accountRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
        accountInstance.setCustomer(customerClient.getCustomer(accountInstance.getCustomerId()));
        return accountInstance;
    }

}
