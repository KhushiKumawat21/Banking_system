package profile;

import businesslogic.Validate;
import businesslogic.Validate.ValidationType;
import businesslogic.ValidateImpl;
import db.DatabaseConnection;
import java.io.Console;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class ProfileUpdateImpl implements ProfileUpdate {

    private Connection connect() throws SQLException {
        return db.DatabaseConnection.getConnection();
    }

    Validate valid = new ValidateImpl();
    private Scanner scanner = new Scanner(System.in); // Initialize Scanner for user input
    private Console console = System.console();

    @Override
    public int update() {
        int pin = 0;
        System.out.print("Enter Pin: ");
        
        try {
            /*if (console != null) {
                // Read PIN using Console
                pin = Integer.parseInt(new String(console.readPassword()));
            } */
        	pin = scanner.nextInt();
        } catch (NumberFormatException e) {
            System.out.println("Invalid Pin format.");
            return 0;
        }
        
        if (!valid.validate(pin, ValidationType.pin)) {
            System.out.println("Invalid Pin.");
            return 0;
        }
        
        Scanner scanner =null;
        scanner =new Scanner(System.in);
        System.out.print("Enter update type (address/email/aadhar/pan): ");
        String type = scanner.nextLine().toLowerCase();
        System.out.print("Enter new value: ");
        String value = scanner.nextLine();
        String column = null;

        switch (type) {
            case "address":
            case "email":
                column = type;
                break;

            case "aadhar":
                try {
                    long aadharValue = Long.parseLong(value);
                    if (valid.validate(aadharValue, ValidationType.aadhar)) {
                        column = "aadhar";
                    } else {
                        System.out.println("Invalid Aadhar number.");
                        return 0;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid Aadhar number format.");
                    return 0;
                }
                break;

            case "pan":
                if (valid.validate(value, ValidationType.pan)) {
                    column = "pan";
                } else {
                    System.out.println("Invalid PAN number.");
                    return 0;
                }
                break;

            default:
                System.out.println("Invalid update type.");
                return 0;
        }

        HandleUpdate handleUpdate = new HandleUpdateImpl();
        return handleUpdate.handleOperations(pin, column, value);
    }

    @Override
    public boolean resetPassword() {
        System.out.print("Enter UserID: ");
        int userId;
        try {
            userId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid UserID format.");
            return false;
        }
        
        if (!valid.validate(userId, ValidationType.id)) {
            System.out.println("Invalid UserID.");
            return false;
        }

        System.out.print("Enter new PIN (4 digits): ");
        String newPin;
        if (console != null) {
            // Read new PIN using Console
            newPin = new String(console.readPassword("Enter new PIN (4 digits): "));
        } else {
            // Fallback for environments without Console
            newPin = scanner.nextLine(); 
        }
        
        if (!valid.validate(newPin, ValidationType.pin)) {
            System.out.println("Invalid PIN. It must be exactly 4 digits.");
            return false;
        }

        String query = "UPDATE users_tb SET pin = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pr = conn.prepareStatement(query)) {
            pr.setInt(1, Integer.parseInt(newPin));
            pr.setInt(2, userId);
            int size = pr.executeUpdate();

            if (size > 0) {
                System.out.println("PIN updated successfully.");
                return true;
            } else {
                System.out.println("No user found with the given UserID.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void close() {
        scanner.close();
    }
}

	
