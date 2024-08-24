//package transaction;
////import Banking.Transaction;
////import Banking.Deposit;
//import java.util.Scanner;
//
//public class Main{
//    public static void main(String[] args) {
//        WithDep depositAccount = new Deposit();
//        Scanner sc = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("Choose an option: ");
//            System.out.println("1. Deposit");
//            System.out.println("2. Withdrawal");
//            System.out.println("3. Exit");
//            int choice = sc.nextInt();
//
//            switch (choice) {
//                case 1:
//                    depositAccount.deposit();
//                    break;
//                case 2:
//                    depositAccount.withdrawal();
//                    break;
//                case 3:
//                    sc.close(); // Close the Scanner
//                    System.out.println("Exiting...");
//                    return; // Exit the loop and program
//                default:
//                    System.out.println("Invalid option, please choose again.");
//            }
//        }
//    }
//}
package transaction;

import java.util.Scanner;

public class scase {
    private WithDep depositAccount;
    private Scanner scanner;

    public scase(WithDep depositAccount) {
        this.depositAccount = depositAccount;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("Choose an option: ");
            System.out.println("1. Deposit");
            System.out.println("2. Withdrawal");
            System.out.println("3. Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    depositAccount.deposit();
                    break;
                case 2:
                    depositAccount.withdrawal();
                    break;
                case 3:
                    scanner.close();
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid option, please choose again.");
            }
        }
    }
}
