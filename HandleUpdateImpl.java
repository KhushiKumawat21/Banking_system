package profile;

import db.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleUpdateImpl implements HandleUpdate {
    
    @Override
    public int handleOperations(int pin, String column, String value) {
        String query = "UPDATE users_tb SET " + column + " = ? WHERE pin = ?";
        int rowsAffected = 0;
        
        try (Connection conn = db.DatabaseConnection.getConnection();
             PreparedStatement pr = conn.prepareStatement(query)) {

            if (column.equals("pin")) 
            {
                // PIN is an integer
                pr.setInt(1, Integer.parseInt(value));
            } 
            else if (column.equals("aadhar") || column.equals("phone_number")) 
            {
                // Aadhar and phone_number are BIGINT
                pr.setLong(1, Long.parseLong(value));
            } 
            else 
            {
                // For other fields (like address and email) -> setString
                pr.setString(1, value);
            }
            
            pr.setInt(2, pin);
            rowsAffected = pr.executeUpdate();
            System.out.println(column.substring(0, 1).toUpperCase() + column.substring(1) + " updated successfully.");
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        } 
        
        catch (NumberFormatException e) {
            System.out.println("Invalid number format for the field: " + column);
        }
        
        return rowsAffected;
    }
}



