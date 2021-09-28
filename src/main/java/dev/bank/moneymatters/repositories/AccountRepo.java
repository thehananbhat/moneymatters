package dev.bank.moneymatters.repositories;

import dev.bank.moneymatters.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {

    Optional<Account> findById(long accountNumber);
    @Query(value = "SELECT * FROM account WHERE customer.customerId = ? ", nativeQuery = true)
    Optional<List<Account>> findByCustomerId(int customerId);
}
