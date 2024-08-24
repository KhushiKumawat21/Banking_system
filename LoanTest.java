package loan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoanTest {

    public void processLoan() {
        Scanner sc = new Scanner(System.in);

        try {
            // Prompt user for loan details
            System.out.print("Enter your account number: ");
            long account_number = sc.nextLong();

            if (!isAccountNumberValid(account_number)) {
                System.err.println("Invalid account number. Please ensure you are registered.");
                return;
            }

            System.out.print("Enter principal amount: ");
            double principal = sc.nextDouble();

            System.out.print("Enter annual interest rate (in %): ");
            double interestRate = sc.nextDouble();

            System.out.print("Enter term in years: ");
            int termInYears = sc.nextInt();

            // Create PersonalLoan instance and save details to database

            PersonalLoan personalLoan = new PersonalLoan(account_number, principal, interestRate, termInYears);

            personalLoan.displayLoanDetails();

        } catch (Exception e) {
            System.err.println("Invalid input. Please try again.");
        } finally {
            sc.close();
        }
    }

    private boolean isAccountNumberValid(long accountNumber) {
        boolean isValid = false;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsystem", "root", "Tonk@123")) {
            String sql = "SELECT COUNT(*) FROM users_tb WHERE account_number = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
