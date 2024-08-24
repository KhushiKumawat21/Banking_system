package transaction;

import java.util.Scanner;

public class BankingSystemCLI {

    public void startBankingSystemCLI(Scanner scanner) {
        boolean running = true;

        while (running) {
            System.out.println("Banking Management System - View Transactions");
            System.out.println("1. View Transactions by Account Number");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = TransactionManager.validateNumericInput(scanner);

            switch (choice) {
                case 1:
                    TransactionManager.viewTransactionsPage(scanner); // Navigate to the view transactions page
                    break;

                case 2:
                    running = false;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
