package revature.console;

import io.javalin.Javalin;
import revature.controller.AccountController;
import revature.controller.PersonController;
import revature.dao.ConnectionManager;
import revature.dao.PersonDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


public class MainStarter {

    public static void main(String[] args) throws NegativeAmountException, SQLException, InsufficientBalanceException, InvalidAccountNumberException {
        PersonDao ps = new PersonDao();
        Javalin app = Javalin.create().start(7070);
        Connection conn = ConnectionManager.getConnection();
        PersonController personController = new PersonController(app, conn);
        AccountController accountController = new AccountController(app, conn);
        Scanner proceed = new Scanner(System.in);

        do {
            System.out.println("Enter Function in The Bank ");
            System.out.println("Entering ...");

            System.out.println("0. Quit");
            System.out.println("1. Customer");
            // logger.info("User  had picked Customers Options");
            System.out.println("2. Employee");
            System.out.println("3. Admin");

            int choice = proceed.nextInt();

            switch (choice) {
                case 0: {
                    ps.getAllPersons();
                    System.exit(0);
                }
                break;
                case 1: {
                    CustomerConsole.showCustomer();
                }
                break;
                case 2: {
                    EmployeeConsole.showEmployee();
                }
                break;
                case 3: {
                    AdminConsole.showAdmin();
                }
                break;
                default:
                    System.out.println("The specified value is not supported");
                    break;
            }
        } while (true);
    }

    {
        System.out.println("Login failed ");
        System.exit(0);

    }
}


