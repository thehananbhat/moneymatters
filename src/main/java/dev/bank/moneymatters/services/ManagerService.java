package dev.bank.moneymatters.services;

import dev.bank.moneymatters.entities.Account;
import dev.bank.moneymatters.entities.Customer;
import dev.bank.moneymatters.entities.Transaction;
import dev.bank.moneymatters.repositories.AccountRepo;
import dev.bank.moneymatters.repositories.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
public class ManagerService {

    @Autowired
    private CustomerRepo cust;

    @Autowired
    private AccountRepo repo;

    public Customer createNewCustomer(Customer newCust) {
        try {
            newCust = cust.save(newCust);
        }catch(Exception e){
            throw e;
        }
        return newCust;
    }

    public Account createNewAccount(int customerId) {
        Account account = new Account();

        System.out.println(customerId);

        Customer customer = cust.findById(customerId).get();

        System.out.println(customer.getName());

        account.setCurrentBalance(0);
        account.setCustomer(customer);

        account.setTransactions(new ArrayList<Transaction>());

        System.out.println(account.getCurrentBalance());

        try {
            account = repo.save(account);
        }catch(Exception e) {
            throw e;
        }

        return account;

    }

    public ResponseEntity<Object> verifyPanCard(String panCardNumber) {
        Customer customer = cust.findCustomerByPanCard(panCardNumber);
        HashMap<String,String> result = new HashMap<String, String>();
        if(customer != null)
        {
            result.put("message","Account exists");
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        else
        {
            result.put("message","Account doest not exists");
            return new ResponseEntity<>(result,HttpStatus.NOT_FOUND);
        }
    }
}
