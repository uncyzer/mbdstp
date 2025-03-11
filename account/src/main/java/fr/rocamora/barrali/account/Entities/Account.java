package fr.rocamora.barrali.account.Entities;

import fr.rocamora.barrali.account.CurrencyType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
public class Account {
    @Id
    private String id;
    private Double balance;
    private LocalDate dateCreated;
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
    @Transient
    private Customer customer;
    private Long customerId;
}
