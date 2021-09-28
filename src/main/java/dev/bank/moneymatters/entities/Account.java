package dev.bank.moneymatters.entities;

import javax.persistence.*;
import java.util.List;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="seqAccount", initialValue=1000000000, allocationSize=1)
    @Column(name = "account_id", nullable = false)
    private Long accountNumber;

    @ManyToOne
    @JoinColumn(name = "customer_customer_id")
    private Customer customer;

    @Column(name = "transactions")
    @OneToMany(cascade = {CascadeType.ALL})
    private List<Transaction> transactions;

    @Column(name = "current_balance")
    private double currentBalance;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }


    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }
}
