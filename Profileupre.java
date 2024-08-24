package profile;

import java.util.Scanner;

public class Profileupre {

    private final ProfileUpdate profileUpdate = new ProfileUpdateImpl();

    public void manageProfile(Scanner scanner) {
        System.out.println("Welcome to the Profile Management System!");

        boolean continueRunning = true;

        while (continueRunning) {
            System.out.println("Choose an operation:");
            System.out.println("1. Update Profile");
            System.out.println("2. Reset Password");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1/2/3): ");
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1:
                    System.out.println("You have chosen to update a profile.");
                    int updateResult = profileUpdate.update();
                    if (updateResult > 0) {
                        System.out.println("Update operation was successful.");
                    } else {
                        System.out.println("Update operation failed. Please check the details and try again.");
                    }
                    break;

                case 2:
                    System.out.println("You have chosen to reset a password.");
                    boolean success = profileUpdate.resetPassword();
                    System.out.println("Password reset successful: " + success);
                    break;

                case 3:
                    System.out.println("Exiting the system. Goodbye!");
                    continueRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please choose either 1 for Update Profile, 2 for Reset Password, or 3 to Exit.");
                    break;
            }
        }
    }
}
