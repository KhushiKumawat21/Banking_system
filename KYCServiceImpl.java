package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class KYCServiceImpl implements KYCService {

	@Override
    public void collectCustomerInfo(String customerName, String customerId, String address, String contactNumber, String ssn) {
		
		System.out.println("Inserting Customer Info:");
	    System.out.println("Customer ID: " + customerId);
	    System.out.println("Customer Name: " + customerName);
	    System.out.println("Address: " + address);
	    System.out.println("Contact Number: " + contactNumber);
	    System.out.println("SSN: " + ssn);
	    

	    try (Connection conn = DatabaseUtill.getConnection()) {
	        String sql = "INSERT INTO Customers (customerId, customerName, address, contactNumber, ssn) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement stmt = conn.prepareStatement(sql);
	        stmt.setString(1, customerId);
	        stmt.setString(2, customerName);
	        stmt.setString(3, address);
	        stmt.setString(4, contactNumber);
	        stmt.setString(5, ssn);
	        int rowsAffected = stmt.executeUpdate();
	        if (rowsAffected > 0) {
	            System.out.println("Customer Info Collected for: " + customerName);
	        } else {
	            System.out.println("No rows affected. Insertion may have failed.");
	        }
	    } catch (SQLException e) {
	        System.out.println("SQL Error: " + e.getMessage());
	        e.printStackTrace();
	    }
        
    }


}
