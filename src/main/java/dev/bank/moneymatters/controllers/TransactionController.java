package dev.bank.moneymatters.controllers;

import dev.bank.moneymatters.entities.Transaction;
import dev.bank.moneymatters.repositories.TransactionRepository;
import dev.bank.moneymatters.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository repository;

//    @GetMapping("/balance")
//    private List<Transaction> getbal(){
//        List<Transaction> body = repository.findAll();
//        return body;
//    }

    @GetMapping("getCurrentBalance/{id}")
    public double getCurrentBalance(@PathVariable Long id) {

        double currentBalance = transactionService.getCurrentBalance(id);
        return currentBalance;
    }

    @GetMapping("debit/{id}/{amount}")
    public double updateCurrentBalance(@PathVariable Long id, @PathVariable @Validated long amount) {

        transactionService.debit(id,amount);
        double currentBalance = transactionService.getCurrentBalance(id);
        return currentBalance;
    }

    @PostMapping("credit/{accountId}/{amount}")
    public String DepositMoney(@PathVariable Long accountId,@PathVariable double amount)
    {
        return transactionService.credit(accountId,amount);
    }


}
