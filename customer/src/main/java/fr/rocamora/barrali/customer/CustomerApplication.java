package fr.rocamora.barrali.customer;

import fr.rocamora.barrali.customer.Entities.Customer;
import fr.rocamora.barrali.customer.Repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class CustomerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomerApplication.class, args);
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository) {
        return args -> {
            List<Customer> customerList = List.of(
                    Customer.builder()
                            .firstName("John")
                            .lastName("Doe")
                            .email("john.doe@etu.unice.fr")
                            .build(),
                    Customer.builder()
                            .firstName("Jane")
                            .lastName("Doe")
                            .email("jane.doe@etu.unice.fr")
                            .build()
            );
            customerRepository.saveAll(customerList);
        };
    }
}
