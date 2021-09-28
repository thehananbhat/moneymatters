package dev.bank.moneymatters.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @Column(name = "transaction_amt")
    private double transactionAmt;

    @Column(name = "transaction_type")
    private String transactionType;

    public String getTransactionReferenceId() {
        return transactionReferenceId;
    }

    public void setTransactionReferenceId(String transactionReferenceId) {
        this.transactionReferenceId = transactionReferenceId;
    }

    @Column(name = "transaction_reference_id")
    private String transactionReferenceId;

    @Column(name = "local_date_time")
    private LocalDateTime localDateTime;

    public Transaction(){ }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public double getTransactionAmt() {
        return transactionAmt;
    }

    public void setTransactionAmt(double transactionAmt) {
        this.transactionAmt = transactionAmt;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public Transaction(Long transactionId, double transactionAmt, String transactionType, String transactionReferenceId, LocalDateTime localDateTime) {
        this.transactionId = transactionId;
        this.transactionAmt = transactionAmt;
        this.transactionType = transactionType;
        this.transactionReferenceId = transactionReferenceId;
        this.localDateTime = localDateTime;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionAmt=" + transactionAmt +
                ", transactionType='" + transactionType + '\'' +
                ", transactionReferenceId='" + transactionReferenceId + '\'' +
                ", localDateTime=" + localDateTime +
                '}';
    }
}
