package revature.console;


import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.service.AdminService;
import revature.service.CustomerService;
import revature.service.EmployeeService;

import java.sql.SQLException;
import java.util.Scanner;

import static revature.console.MenuLogin.getInteger;


public class AdminConsole {

    static AdminService as = new AdminService();
    static EmployeeService es = new EmployeeService();

    static void showAdmin() throws SQLException, InvalidAccountNumberException, NegativeAmountException, InsufficientBalanceException {
        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        while (choice != 8) {
            System.out.println("Good day to You dear Admin");
            System.out.println("***********************");
            System.out.println(" 1. View All Accounts");
            System.out.println(" 2. Edit Account");
            System.out.println(" 3. Approve/Deny Account");
            System.out.println(" 4. Close Account");
            System.out.println(" 5. Deposit Amount");
            System.out.println(" 6. Withdraw Amount");
            System.out.println(" 7. Transfer Amount");
            System.out.println(" 8. Quit");

            boolean menu = true;
            while (menu) {
                System.out.print("Enter your choice ");
                String entry = scanner.next();
                choice = getInteger(entry);
                if (choice == 8) menu = false;
                if (choice >= 1 && choice <= 7) menu = false;
                if (choice <= 0 || choice > 8)
                    System.out.print("The specified value is not supported");
            }

            System.out.println();
            switch (choice) {
                case 1:
                    as.viewAccounts();
                    break;
                case 2:
                    es.approveAccount();
                    break;
                case 3:
                   // editAccount();
                    break;
                case 4:
                    as.closeAccount();
                    break;
                case 5:
                    es.deposit();
                    break;
                case 6:
                    es.withdrawal();
                    break;
                case 7:
                    es.transfer();
            }
        }

    }


}
