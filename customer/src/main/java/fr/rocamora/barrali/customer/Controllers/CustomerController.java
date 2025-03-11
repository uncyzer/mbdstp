package fr.rocamora.barrali.customer.Controllers;

import fr.rocamora.barrali.customer.Dtos.CustomerDTO;
import fr.rocamora.barrali.customer.Entities.Customer;
import fr.rocamora.barrali.customer.Repositories.CustomerRepository;
import fr.rocamora.barrali.customer.Services.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/customers")
    public List<CustomerDTO> getCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable Long id) {
        return customerService.findById(id);
    }
}
