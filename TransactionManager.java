package transaction;

import db.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class TransactionManager {

    // Fetch transactions from the database
    public static List<Transaction> fetchTransactions(long account_number) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM account_transaction WHERE account_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, account_number);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                double amount = rs.getDouble("amount");
                Timestamp date = rs.getTimestamp("transaction_date");
                String type = rs.getString("transaction_type");

                transactions.add(new Transaction(transactionId, account_number, amount, date, type));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching transactions!");
            e.printStackTrace();
        }
        return transactions;
    }

    // Method to display the view transactions page
    public static void viewTransactionsPage(Scanner scanner) {
        long account_number = validateAccountNumber(scanner);

        System.out.println("1. View All Transactions");
        System.out.println("2. Sort by Date");
        System.out.println("3. Filter by Transaction Type");
        System.out.print("Choose an option: ");

        int choice = validateNumericInput(scanner);

        switch (choice) {
            case 1:
                viewAllTransactions(account_number);
                break;
            case 2:
                viewTransactionsSortedByDate(account_number);
                break;
            case 3:
                filterTransactionsByType(account_number, scanner);
                break;
            default:
                System.out.println("Invalid option, please try again.");
        }
        System.out.println("Returning to main menu...");
    }

    // View all transactions (without sorting or filtering)
    private static void viewAllTransactions(long account_number) {
        Thread viewThread = new Thread(new ViewTransactionTask(account_number));
        viewThread.start();
        try {
            viewThread.join(); // wait for the thread to finish
        } catch (InterruptedException e) {
            System.out.println("Thread was interrupted.");
            e.printStackTrace();
        }
    }

    // Thread to view transactions (Multithreading)
    private static class ViewTransactionTask implements Runnable {
        private final long account_number;

        public ViewTransactionTask(long account_number) {
            this.account_number = account_number;
        }

        @Override
        public void run() {
            List<Transaction> transactions = fetchTransactions(account_number);
            if (transactions.isEmpty()) {
                System.out.println("No transactions found for account number: " + account_number);
            } else {
                transactions.forEach(Transaction::viewDetails);
                System.out.println("-----------------------------");
            }
        }
    }

    // View transactions sorted by date
    public static void viewTransactionsSortedByDate(long account_number) {
        List<Transaction> transactions = fetchTransactions(account_number);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for account number: " + account_number);
        } else {
            transactions.stream()
                    .sorted(Comparator.comparing(Transaction::getDate))
                    .forEach(Transaction::viewDetails);
        }
    }

    // Filter transactions by type
    public static void filterTransactionsByType(long account_number, Scanner scanner) {
        String type = validateTransactionType(scanner);
        List<Transaction> transactions = fetchTransactions(account_number);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for account number: " + account_number);
        } else {
            transactions.stream()
                    .filter(transaction -> transaction.getType().equalsIgnoreCase(type))
                    .forEach(Transaction::viewDetails);
        }
    }

    // Validate account number
    public static long validateAccountNumber(Scanner scanner) {
        long account_number;
        while (true) {
            System.out.print("Enter Account Number (numeric only): ");
            String input = scanner.next().trim(); // Read input as String and trim whitespace

            if (input.matches("\\d+")) { // Check if the input is numeric
                try {
                    account_number = Long.parseLong(input); // Convert to long
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid account number. Please enter a valid numeric value.");
                }
            } else {
                System.out.println("Invalid account number. Please enter a numeric value.");
            }
        }
        return account_number;
    }

    // Validate transaction type
    public static String validateTransactionType(Scanner scanner) {
        String type;
        while (true) {
            System.out.print("Enter Transaction Type (Deposit/Withdraw): ");
            type = scanner.nextLine().trim();
            if (type.equalsIgnoreCase("Deposit") || type.equalsIgnoreCase("Withdraw")) {
                break;
            } else {
                System.out.println("Invalid transaction type. Please enter 'Deposit' or 'Withdraw'.");
            }
        }
        return type;
    }

    // Validate numeric input for menu selection
    public static int validateNumericInput(Scanner scanner) {
        int choice;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a numeric value.");
            }
        }
        return choice;
    }
}
