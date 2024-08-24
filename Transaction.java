package transaction;

import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private long account_number;
    private double amount;
    private Timestamp date;
    private String type; // Credit or Debit

    public Transaction(int transactionId, long account_number, double amount, Timestamp date, String type) {
        this.transactionId = transactionId;
        this.account_number = account_number;
        this.amount = amount;
        this.date = date;
        this.type = type;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public long getAccountNumber() {
        return account_number;
    }

    public double getAmount() {
        return amount;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void viewDetails() { // Abstraction
        System.out.println("Transaction ID: " + getTransactionId());
        System.out.println("Account Number: " + getAccountNumber());
        System.out.println("Amount: " + getAmount());
        System.out.println("Date: " + getDate());
        System.out.println("Type: " + getType());
    }
}
