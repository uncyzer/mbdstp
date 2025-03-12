package fr.rocamora.barrali.account.Services;


import fr.rocamora.barrali.account.Dtos.AccountDTO;

import java.util.List;

public interface AccountService {
    List<AccountDTO> findAll();
    AccountDTO findById(String id);
}
