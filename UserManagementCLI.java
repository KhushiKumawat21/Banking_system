package transaction;

import transaction.UserService;
import transaction.UserServiceImpl;

import java.util.Scanner;

public class UserManagementCLI {

    private Scanner scanner;
    private UserService userService;

    public UserManagementCLI(UserService userService) {
        this.scanner = new Scanner(System.in);
        this.userService = userService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("User Management System");
            System.out.println("1. Create User");
            System.out.println("2. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    createUserPage();
                    break;
                case 2:
                    running = false;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private void createUserPage() {
        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        // Validate Account Number
        while (accountNumber.isEmpty()) {
            System.out.println("Account Number cannot be empty. Please enter again.");
            accountNumber = scanner.nextLine();
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        // Validate Name
        while (name.isEmpty()) {
            System.out.println("Name cannot be empty. Please enter again.");
            name = scanner.nextLine();
        }

        int pin = -1;
        boolean validPin = false;

        // Validate PIN
        while (!validPin) {
            System.out.print("Enter PIN (4 digits only): ");
            String pinInput = scanner.nextLine();

            if (pinInput.length() == 4 && pinInput.matches("\\d+")) {
                pin = Integer.parseInt(pinInput);
                validPin = true;
            } else {
                System.out.println("PIN must be exactly 4 digits long. Please try again.");
            }
        }

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        // Validate Address
        while (address.isEmpty()) {
            System.out.println("Address cannot be empty. Please enter again.");
            address = scanner.nextLine();
        }

        System.out.print("Enter PAN: ");
        String pan = scanner.nextLine();

        // Validate PAN
        while (pan.isEmpty() || !pan.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}")) {
            System.out.println("PAN must be in the format XXXXX9999X (5 letters, 4 digits, 1 letter). Please enter again.");
            pan = scanner.nextLine();
        }

        long aadhar = -1;
        boolean validAadhar = false;

        // Validate Aadhar Number
        while (!validAadhar) {
            System.out.print("Enter Aadhar Number: ");
            String aadharInput = scanner.nextLine();

            if (aadharInput.length() == 12 && aadharInput.matches("\\d+")) {
                aadhar = Long.parseLong(aadharInput);
                validAadhar = true;
            } else {
                System.out.println("Aadhar Number must be exactly 12 digits long. Please try again.");
            }
        }

        long phoneNumber = -1;
        boolean validPhoneNumber = false;

        // Validate Phone Number
        while (!validPhoneNumber) {
            System.out.print("Enter Phone Number: ");
            String phoneNumberInput = scanner.nextLine();

            if (phoneNumberInput.length() == 10 && phoneNumberInput.matches("\\d+")) {
                phoneNumber = Long.parseLong(phoneNumberInput);
                validPhoneNumber = true;
            } else {
                System.out.println("Phone Number must be exactly 10 digits long. Please try again.");
            }
        }

        userService.createUser(accountNumber, name, pin, address, aadhar, pan, phoneNumber);
    }
}
