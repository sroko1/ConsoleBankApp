package revature.console;

import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.service.EmployeeService;

import java.sql.SQLException;
import java.util.Scanner;

import static revature.console.AdminConsole.as;
import static revature.console.MenuLogin.getInteger;



public class EmployeeConsole {
  static EmployeeService es = new EmployeeService();

    static void showEmployee() throws NegativeAmountException, SQLException, InsufficientBalanceException, InvalidAccountNumberException {
        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        while (choice != 6) {
            System.out.println("Good day to You Hardworking Employee");
            System.out.println("*************************");
            System.out.println(" 1. View All Accounts");
            System.out.println(" 2. Approve/Deny Account");
            System.out.println(" 3. Deposit Amount");
            System.out.println(" 4. Withdraw Amount");
            System.out.println(" 5. Transfer Amount");
            System.out.println(" 6. Quit");

            boolean menu = true;
            while (menu) {
                System.out.print("Enter your choice ");
                String entry = scanner.next();
                choice = getInteger(entry);
                if (choice == 6) menu = false;
                if (choice >= 1 && choice <= 5) menu = false;
                if (choice <= 0 || choice > 6)
                    System.out.println("The specified value is not supported");
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
                    es.deposit();
                    break;
                case 4:
                     es.withdrawal();
                    break;
                case 5:
                     es.transfer();
            }
        }

    }
}