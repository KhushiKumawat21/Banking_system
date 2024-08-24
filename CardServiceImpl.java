package service;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CardServiceImpl implements CardService{
	   @Override
	    public void createDebitCard(String accountNumber, String customerId, String cardHolderName) {
	        createCard(accountNumber, customerId, "DEBIT");
	    }

	    @Override
	    public void createCreditCard(String accountNumber, String customerId, String cardHolderName) {
	        createCard(accountNumber, customerId, "CREDIT");
	    }
	   
	    private void createCard(String accountNumber, String customerId, String cardType) {

	        try (Connection conn = DatabaseUtill.getConnection()) {
	            String sql = "INSERT INTO Cards (accountNumber, customerId, cardType, isBlocked) VALUES (?, ?, ?, ?)";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setString(1, accountNumber);
	            stmt.setString(2, customerId);
	            stmt.setString(3, cardType);
	            stmt.setBoolean(4, false);
	            stmt.executeUpdate();
	            System.out.println(cardType + " Card Created for Account: " + accountNumber);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    @Override
	    public void blockDebitCard(String accountNumber, String customerId) {
	        blockCard(accountNumber, customerId);
	    }

	    @Override
	    public void blockCreditCard(String accountNumber, String customerId) {
	        blockCard(accountNumber, customerId);
	    }

	    private void blockCard(String accountNumber, String customerId) {
	    	try (Connection conn = DatabaseUtill.getConnection()) {
	            String sql = "UPDATE Cards SET isBlocked = ? WHERE accountNumber = ? AND customerId = ?";
	            PreparedStatement stmt = conn.prepareStatement(sql);
	            stmt.setBoolean(1, true);
	            stmt.setString(2, accountNumber);
	            stmt.setString(3, customerId);
	            stmt.executeUpdate();
	            System.out.println("Card Blocked for Account: " + accountNumber + " for Customer: " + customerId);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
