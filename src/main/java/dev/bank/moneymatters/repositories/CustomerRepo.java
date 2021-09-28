package dev.bank.moneymatters.repositories;

import dev.bank.moneymatters.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findCustomerByPanCard(String panCardNumber);
}
