package revature.console;

import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.service.CustomerService;

import java.sql.SQLException;
import java.util.Scanner;

public class CustomerConsole {

     static CustomerService cs = new CustomerService();
     static MenuLogin ml = new MenuLogin();

    static void showCustomer() throws NegativeAmountException, SQLException, InsufficientBalanceException, InvalidAccountNumberException {
        Scanner scanner = new Scanner(System.in);
        int choice = 1;
        while (choice != 3) {

            System.out.println("Welcome to Good Bank  ! Would you like to...");
            System.out.println("***************************************");
            System.out.println("   1 - Log in Existing Account");
            System.out.println("   2 - Register and Open New Account");
            System.out.println("   3 - Back");
            System.out.println("***************************************");

            boolean menu = true;
            while (menu) {
                System.out.println("SELECT OPTION");
                String entry = scanner.next();
                choice = MenuLogin.getInteger(entry);
                if (choice == 3) menu = false;
                if (choice >= 1 && choice <= 2) menu = false;
                if (choice <= 0 || choice > 3)
                    System.out.println("The specified value is not supported");

            }
            System.out.println();
            switch (choice) {
                case 1:
                    ml.loggIn();
                    break;
                case 2:
                    cs.openAccount();
            }
        }
    }
}