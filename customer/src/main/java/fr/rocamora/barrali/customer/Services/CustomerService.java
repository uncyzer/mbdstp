package fr.rocamora.barrali.customer.Services;

import fr.rocamora.barrali.customer.Dtos.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> findAll();
    CustomerDTO findById(Long id);
}