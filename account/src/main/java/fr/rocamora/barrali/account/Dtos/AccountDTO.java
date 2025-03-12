package fr.rocamora.barrali.account.Dtos;

import fr.rocamora.barrali.account.CurrencyType;
import fr.rocamora.barrali.account.Entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor
public class AccountDTO {
    private String id;
    private Double balance;
    private LocalDate dateCreated;
    private CurrencyType currencyType;
    private Customer customer;
    private Long customerId;
}
