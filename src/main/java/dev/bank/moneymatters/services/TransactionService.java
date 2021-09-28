package dev.bank.moneymatters.services;

import dev.bank.moneymatters.constants.TransactionConstants;
import dev.bank.moneymatters.entities.Account;
import dev.bank.moneymatters.entities.Customer;
import dev.bank.moneymatters.entities.Transaction;
import dev.bank.moneymatters.repositories.AccountRepo;
import dev.bank.moneymatters.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class TransactionService {

    @Autowired
    TransactionConstants constants;

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    TransactionRepository transactionRepository;

    public double getCurrentBalance(long accountNumber){
        // get the account number
        Account account = (Account) accountRepo.getById(accountNumber);

        double currentBalance = (double) account.getCurrentBalance();
        return currentBalance;
    }

    public String credit(Long accountNumber, double amount){
        Transaction transaction = new Transaction();

        //get customer by ID
        Account account = (Account) accountRepo.getById(accountNumber);

        transaction.setTransactionAmt(amount);
        transaction.setTransactionType(constants.CREDIT_TRANSACTION);
        transaction.setLocalDateTime(LocalDateTime.now());
        transaction.setTransactionReferenceId("TID-"+transaction.getTransactionId()+"-CREDIT-"+"CustomerID");

        transactionRepository.save(transaction);
        //Add this transaction to the customer's List of Transaction
        account.getTransactions().add(transaction);

        double currentBalance = getCurrentBalance(accountNumber);
        double updatedBalance = currentBalance + amount;
        account.setCurrentBalance(updatedBalance);

        // save in account repo
        accountRepo.save(account);

        return constants.CREDIT_SUCCESSFUL;
    }

    public String debit(Long accountNumber, double amount){

        Transaction transaction = new Transaction();
        //get customer by ID
        Account account = (Account) accountRepo.getById(accountNumber);

        transaction.setTransactionAmt(amount);
        transaction.setTransactionType(constants.DEBIT_TRANSACTION);
        transaction.setTransactionReferenceId("TID-"+transaction.getTransactionId()+"-DEBIT-"+"CustomerID");

        transactionRepository.save(transaction);
        //Add this transaction to the customer's List of Transaction
        account.getTransactions().add(transaction);

        double currentBalance = getCurrentBalance(accountNumber);
        double updatedBalance = currentBalance - amount;
        account.setCurrentBalance(updatedBalance);

        // save in account repo
        accountRepo.save(account);

        return constants.DEBIT_SUCCESSFUL;

    }

    @Autowired
    private JavaMailSender mailSender;


    public void sendEmail(Account account, Transaction transaction) {
        SimpleMailMessage message = new SimpleMailMessage();

        Customer customer = account.getCustomer();
        String emailId = customer.getEmail();
        System.out.println(emailId);
        message.setFrom(constants.EMAIL_ADDRESS);
        message.setTo(emailId);

        String firstEncryptedAccountNumber = Long.toString(account.getAccountNumber()).substring(0, 3);
        String lastEncryptedAccountNumber = Long.toString(account.getAccountNumber()).substring(5, 9);
        String encryptedAccountNumber = firstEncryptedAccountNumber +"XXX"+ lastEncryptedAccountNumber;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String now = dtf.format(transaction.getLocalDateTime());

        String subjectStart = "Your account";
        String subjectEnd;

        if(transaction.getTransactionType().equals("Credit")) {
            subjectEnd = "has been credited.";
        }else {
            subjectEnd = "has been debited.";
        }

        message.setText("Transaction successful! \nTransaction reference number : " + transaction.getTransactionReferenceId() +
                "\nTransaction Date : " + now +
                "\nTransaction Type : " + transaction.getTransactionType() +
                "\nAmount transacted : $" + transaction.getTransactionAmt());
        message.setSubject(subjectStart + " " + encryptedAccountNumber + " " +subjectEnd);

        mailSender.send(message);
        System.out.println("Mail sent...");
    }
}
