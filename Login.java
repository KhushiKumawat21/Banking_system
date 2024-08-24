package uiLayer;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    public void loginWithGUI() {
        System.out.println("Creating GUI components..."); // Debug statement

        JTextField accountNumberField = new JTextField();
        JPasswordField pinField = new JPasswordField(); // PIN field is still present but will not be used for verification

        Object[] message = {
                "Account Number:", accountNumberField,
                "PIN:", pinField
        };

        System.out.println("Displaying login dialog..."); // Debug statement

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);

        System.out.println("Dialog closed. Option selected: " + option); // Debug statement

        if (option == JOptionPane.OK_OPTION) {
            System.out.println("User selected OK"); // Debug statement
            String accountNumber = accountNumberField.getText();
            char[] pinArray = pinField.getPassword(); // PIN is still captured but not used
            String pin = new String(pinArray);

            boolean authenticated = authenticate(accountNumber);

            if (authenticated) {
                JOptionPane.showMessageDialog(null, "Login successful!");
            } else {
                JOptionPane.showMessageDialog(null, "Invalid account number. Please try again.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login canceled.");
        }

        System.out.println("Login process completed."); // Debug statement
    }

    private boolean authenticate(String accountNumber) {
        String query = "SELECT COUNT(*) FROM users_tb WHERE account_number = ?";

        try (Connection conn = db.DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, accountNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0; // Return true if there is at least one matching record
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle SQL exceptions
        }

        return false;
    }
}
