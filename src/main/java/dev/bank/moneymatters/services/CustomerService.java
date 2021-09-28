package dev.bank.moneymatters.services;

import dev.bank.moneymatters.entities.Account;
import dev.bank.moneymatters.repositories.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService{

    @Autowired
    AccountRepo accountRepository;

    public Set<HashMap<String,String>> getAccountDetailsByCustomerId(int customerId){

        Set<HashMap<String,String>> accounts = new HashSet<HashMap<String,String>>();

        Optional<List<Account>> accs = accountRepository.findByCustomerId(customerId);

        if(accs.isPresent()) {

            for(Account acc: accs.get()) {

                HashMap<String, String> accAndBalance = new HashMap<String, String>();

                accAndBalance.put("account_number", Long.toString(acc.getAccountNumber()));

                accAndBalance.put("current_balance", Double.toString(acc.getCurrentBalance()));

                accounts.add(accAndBalance);

            }

        }

        return accounts;

    }

}
