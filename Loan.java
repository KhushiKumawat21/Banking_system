package loan;
public interface Loan {
//    long getAccountNumber();
    double getPrincipal();
    double getInterestRate();
    int getTermInYears();
    double calculateMonthlyPayment();
    void displayLoanDetails();
}