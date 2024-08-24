package transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import db.DatabaseConnection;

public class UserServiceImpl implements UserService {

    @Override
    public boolean createUser(String accountNumber, String name, int pin, String address, long aadhar, String pan, long phone_number) {
        if (userExists(accountNumber)) {
            System.out.println("User already exists.");
            return false;
        }

        String query = "INSERT INTO users_tb (account_number, name, pin, address, pan, aadhar, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            stmt.setString(2, name);
            stmt.setInt(3, pin);
            stmt.setString(4, address);
            stmt.setString(5, pan);
            stmt.setLong(6, aadhar);
            stmt.setLong(7, phone_number);



            stmt.executeUpdate();
            System.out.println("User created successfully.");
            return true;
        } catch (SQLException e) {
            System.out.println("Error creating user.");
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean userExists(String accountNumber) {
        String query = "SELECT * FROM users_tb WHERE account_number = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error checking if user exists.");
            e.printStackTrace();
            return false;
        }
    }
}
