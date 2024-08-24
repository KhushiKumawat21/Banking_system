package transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Deposit implements WithDep {
    private String account_number;
    private Connection conn;

    public Deposit() {
        try {
            // Establish a connection to the database
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsystem", "root", "Tonk@123");
            if (conn != null) {
                System.out.println("Database connection established.");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deposit() {
        long amt;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Account no.: ");
        account_number = scanner.next();
        System.out.print("Enter the amount you want to deposit: ");
        amt = scanner.nextLong();

        try {
            // Check if account exists in users_tb
            if (accountExistsInUsers(account_number)) {
                // Check if account exists in account_transaction
                int transactionId = getLatestTransactionId(account_number);

                if (transactionId == -1) {
                    // Initialize the account in account_transaction with a balance of 0
                    initializeAccountInTransactions(account_number);
                    transactionId = getLatestTransactionId(account_number); // Retrieve the new transaction ID
                }

                // Fetch the current balance
                long currentBalance = getBalance(transactionId);

                // Update the balance
                long newBalance = currentBalance + amt;
                updateBalance(transactionId, newBalance);

                // Record the transaction
                recordTransaction(account_number, amt, "deposit");

                System.out.println("Balance after deposit: " + newBalance);
            } else {
                System.out.println("Account number not found in users: " + account_number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void withdrawal() {
        long amt;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Account no.: ");
        account_number = scanner.next();
        System.out.print("Enter the amount you want to withdraw: ");
        amt = scanner.nextLong();

        try {
            // Check if account exists in users_tb
            if (accountExistsInUsers(account_number)) {
                // Check if account exists in account_transaction
                int transactionId = getLatestTransactionId(account_number);

                if (transactionId == -1) {
                    // Initialize the account in account_transaction with a balance of 0
                    initializeAccountInTransactions(account_number);
                    transactionId = getLatestTransactionId(account_number); // Retrieve the new transaction ID
                }

                // Fetch the current balance
                long currentBalance = getBalance(transactionId);

                if (currentBalance >= amt) {
                    // Update the balance
                    long newBalance = currentBalance - amt;
                    updateBalance(transactionId, newBalance);

                    // Record the transaction
                    recordTransaction(account_number, amt, "withdraw");

                    System.out.println("Balance after withdrawal: " + newBalance);
                } else {
                    System.out.println("Your balance is less than " + amt + "\tTransaction failed...!!");
                }
            } else {
                System.out.println("Account number not found in users: " + account_number);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean accountExistsInUsers(String accountNo) throws SQLException {
        String query = "SELECT COUNT(*) FROM users_tb WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            } else {
                return false;
            }
        }
    }

    private int getLatestTransactionId(String accountNo) throws SQLException {
        String query = "SELECT MAX(transaction_id) FROM account_transaction WHERE account_number = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, accountNo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        }
    }

    private void initializeAccountInTransactions(String accountNo) throws SQLException {
        String query = "INSERT INTO account_transaction (account_number, balance, transaction_type, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Initialize with zero balance and no transaction type or amount
            stmt.setString(1, accountNo);
            stmt.setLong(2, 0);
            stmt.setString(3, "initial");
            stmt.setLong(4, 0);
            stmt.executeUpdate();
        }
    }

    private long getBalance(int transactionId) throws SQLException {
        String query = "SELECT balance FROM account_transaction WHERE transaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transactionId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getLong("balance");
            } else {
                throw new SQLException("Transaction not found");
            }
        }
    }

    private void updateBalance(int transactionId, long newBalance) throws SQLException {
        String query = "UPDATE account_transaction SET balance = ? WHERE transaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, newBalance);
            stmt.setInt(2, transactionId);
            stmt.executeUpdate();
        }
    }

    private void recordTransaction(String accountNo, long amount, String type) throws SQLException {
        String query = "INSERT INTO account_transaction (account_number, balance, transaction_type, amount) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Fetch the current balance from the last transaction
            long currentBalance = getBalance(getLatestTransactionId(accountNo));

            // Insert the new transaction
            stmt.setString(1, accountNo);
            stmt.setLong(2, currentBalance);
            stmt.setString(3, type);
            stmt.setLong(4, amount);
            stmt.executeUpdate();
        }
    }
}
