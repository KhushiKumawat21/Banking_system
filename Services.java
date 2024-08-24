package service;
import java.util.Scanner;

public class Services {

    private final KYCService kycService = new KYCServiceImpl();
    private final CardService cardService = new CardServiceImpl();
    private final ChequeBookRequestService chequeBookService = new ChequeBookRequestServiceImpl();

    public void startServices(Scanner sc) {
        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. KYC");
            System.out.println("2. Card Service");
            System.out.println("3. Issue Checkbook");
            System.out.println("4. Exit");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    handleKYC(sc);
                    break;
                case 2:
                    handleCardService(sc);
                    break;
                case 3:
                    handleChequeBookRequest(sc);
                    break;
                case 4:
                    return; // Exit the service menu
                default:
                    System.out.println("Invalid option, please choose again.");
            }
        }
    }

    private void handleKYC(Scanner sc) {
        System.out.println("Enter Your name");
        String name = sc.nextLine();

        System.out.println("Enter your id");
        String id = sc.nextLine();

        System.out.println("Enter your address");
        String address = sc.nextLine();

        System.out.println("Enter your contact number");
        String contactNumber = sc.nextLine();

        System.out.println("Enter your ssn");
        String ssn = sc.nextLine();

        kycService.collectCustomerInfo(id, name, address, contactNumber, ssn);
    }

    private void handleCardService(Scanner sc) {
        System.out.println("Select an option: 1- Create Debit Card, 2- Create Credit Card, 3- Block Debit Card, 4- Block Credit Card");
        int cardOption = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (cardOption) {
            case 1:
                System.out.println("Enter Account Number, Customer ID, Card Holder Name:");
                String debitAccountNumber = sc.nextLine();
                String debitCustomerId = sc.nextLine();
                String debitCardHolderName = sc.nextLine();
                cardService.createDebitCard(debitAccountNumber, debitCustomerId, debitCardHolderName);
                break;

            case 2:
                System.out.println("Enter Account Number, Customer ID, Card Holder Name:");
                String creditAccountNumber = sc.nextLine();
                String creditCustomerId = sc.nextLine();
                String creditCardHolderName = sc.nextLine();
                cardService.createCreditCard(creditAccountNumber, creditCustomerId, creditCardHolderName);
                break;

            case 3:
                System.out.println("Enter Card Number, Customer ID:");
                String blockDebitCardNumber = sc.nextLine(); // Changed to String
                String blockDebitCustomerId = sc.nextLine();
                cardService.blockDebitCard(blockDebitCardNumber, blockDebitCustomerId);
                break;

            case 4:
                System.out.println("Enter Card Number, Customer ID:");
                String blockCreditCardNumber = sc.nextLine(); // Changed to String
                String blockCreditCustomerId = sc.nextLine();
                cardService.blockCreditCard(blockCreditCardNumber, blockCreditCustomerId);
                break;

            default:
                System.out.println("Invalid card option selected.");
                break;
        }
    }

    private void handleChequeBookRequest(Scanner sc) {
        System.out.println("Enter Account Number:");
        String accountNumber = sc.nextLine(); // Changed to String
        chequeBookService.issue(accountNumber);
    }
}
