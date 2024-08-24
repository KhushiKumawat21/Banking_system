package loan;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersonalLoan {
    private int loanId;
    private double principal;
    private double interestRate;
    private int termInYears;
    private double monthlyPayment;
    private long account_number;
    public PersonalLoan(long account_number,double principal, double interestRate, int termInYears) {
        this.account_number = account_number;
        this.principal = principal;
        this.interestRate = interestRate;
        this.termInYears = termInYears;
        this.monthlyPayment = calculateMonthlyPayment();
        saveLoanToDatabase();  // Save the loan to the database
    }

    private double calculateMonthlyPayment() {
        double monthlyInterestRate = (interestRate / 100) / 12;
        int numberOfPayments = termInYears * 12;
        return (principal * monthlyInterestRate) /
                (1 - Math.pow(1 + monthlyInterestRate, -numberOfPayments));
    }

    private void saveLoanToDatabase() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankingsystem", "root", "Tonk@123")) {
            String sql = "INSERT INTO loans (account_number,principal, interest_rate, term_in_years) VALUES (?,?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setLong(1, account_number);
            pstmt.setDouble(2, principal);
            pstmt.setDouble(3, interestRate);
            pstmt.setInt(4, termInYears);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                this.loanId = rs.getInt(1);  // Retrieve the auto-generated loan ID
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayLoanDetails() {
        System.out.println("Personal Loan Details:");
        System.out.println("Loan ID: " + this.loanId);
        System.out.println("Principal: " + this.principal);
        System.out.println("Interest Rate: " + this.interestRate + "%");
        System.out.println("Term: " + this.termInYears + " years");
        System.out.println("Monthly Payment: " + this.monthlyPayment);
    }

    public static PersonalLoan getLoanDetailsById(int loanId) {
        PersonalLoan loan = null;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loan_db", "root", "Tonk@123")) {
            String sql = "SELECT * FROM loans WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, loanId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                long account_number = rs.getLong("Account Number");
                double principal = rs.getDouble("principal");
                double interestRate = rs.getDouble("interest_rate");
                int termInYears = rs.getInt("term_in_years");
                loan = new PersonalLoan(account_number, principal, interestRate, termInYears);
                loan.loanId = loanId;  // Set the loan ID to the one retrieved
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loan;
    }
}
