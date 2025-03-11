package fr.rocamora.barrali.account.Client;

import fr.rocamora.barrali.account.Entities.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "customers", url = "http://localhost:8080/")
public interface CustomerClient {
    @GetMapping("/customers/{id}")
    public Customer getCustomer(@PathVariable Long id);
    @GetMapping("/customers")
    public List<Customer> getCustomers();
}