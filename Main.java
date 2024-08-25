import transaction.WithDep;
import transaction.Deposit;
import transaction.scase;
import transaction.UserManagementCLI;
import transaction.BankingSystemCLI;
import service.Services;
import profile.ProfileUpdate;
import profile.ProfileUpdateImpl;
import transaction.UserService;
import transaction.UserServiceImpl;
import uiLayer.Login;
import loan.LoanTest;
import service.BankingServiceManager;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            try {
                System.out.println("Welcome to the Bank");
                System.out.println("1. Login");
                System.out.println("2. Create account");
                System.out.println("3. Withdraw or Deposit");
                System.out.println("4. Loan");
                System.out.println("5. View Transaction");
                System.out.println("6. Services");
                System.out.println("7. Profile Management");
                System.out.println("8. Exit");
                System.out.println("Enter your choice");

                int choice = sc.nextInt();
                sc.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        System.out.println("Starting login process...");
                        Login login = new Login();
                        login.loginWithGUI();  // Correct method name
                        System.out.println("Login process completed.");
                        break;

                    case 2:
                        UserService userService = new UserServiceImpl();
                        UserManagementCLI userManagementCLI = new UserManagementCLI(userService);
                        userManagementCLI.run();
                        break;
                    case 3:
                        // Initialize Transactions
                        WithDep depositAccount = new Deposit();
                        scase transactionProcessor = new scase(depositAccount);
                        transactionProcessor.start();
                        break;
                    case 4:
                        // Initialize Loan Processing
                        LoanTest loanTest = new LoanTest();
                        loanTest.processLoan();
                        break;
                    case 5:
                        // Initialize Banking System CLI
                        BankingSystemCLI bankingSystemCLI = new BankingSystemCLI();
                        bankingSystemCLI.startBankingSystemCLI(sc);
                        break;
                    case 6:
                        BankingServiceManager bankingServiceManager = new BankingServiceManager();
                        bankingServiceManager.manageServices(sc);
                        break;
                    case 7:
                        // Initialize Profile Management
                        ProfileUpdate profileUpdate = new ProfileUpdateImpl(); // Initialize here
                        System.out.println("Profile Management:\n1. Update Profile\n2. Reset Password");
                        System.out.print("Enter your choice (1/2): ");

                        int profileChoice = Integer.parseInt(sc.nextLine().trim());

                        if (profileChoice == 1) {
                            System.out.println("Update result: " + (profileUpdate.update() > 0 ? "Success" : "Failed"));
                        } else if (profileChoice == 2) {
                            System.out.println("Password reset successful: " + profileUpdate.resetPassword());
                        } else {
                            System.out.println("Invalid choice. Returning to main menu.");
                        }
                        break;
                    case 8:
                        running = false;
                        System.out.println("Exiting...");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NoSuchElementException e) {
                System.out.println("No input available. Exiting...");
                running = false;  // End loop if input is unavailable
            } catch (IllegalStateException e) {
                System.out.println("Scanner is closed or in illegal state.");
                running = false;  // End loop if the scanner is in an illegal state
            }
        }

        sc.close(); // Close the scanner when done
    }
}
