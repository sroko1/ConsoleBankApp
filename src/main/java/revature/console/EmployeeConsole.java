package revature.console;


import revature.dao.PersonDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.service.EmployeeService;

import java.sql.SQLException;
import java.util.Scanner;

import static revature.console.AdminConsole.as;
import static revature.console.MenuLogin.getInteger;
import static revature.console.MenuLogin.log;


public class EmployeeConsole {


    PersonDao personDao = new PersonDao();
    static EmployeeService es = new EmployeeService();
    static EmployeeConsole ec = new EmployeeConsole();
   static Scanner scanner = new Scanner(System.in);

    static void showEmployee() throws NegativeAmountException, SQLException, InsufficientBalanceException, InvalidAccountNumberException {

        int choice = 1;

        while (choice != 2) {

            System.out.println("Welcome to Good Bank Hardworking Employee  ! Would you like to...");
            System.out.println("****************************");
            System.out.println("   1 - Log in ");
            System.out.println("   2 - Quit");

            boolean menu = true;
            while (menu) {
                System.out.println("SELECT OPTION");
                String entry = scanner.next();
                choice = MenuLogin.getInteger(entry);
                if (choice == 2) menu = false;
                if (choice == 1 ) menu = false;
                if (choice <= 0 || choice > 2)
                    System.out.println("The specified value is not supported");

            }
            System.out.println();
            switch (choice) {
                case 1:
                    ec.loggInEmployee();

            }
        }
    }

    public void loggInEmployee() throws SQLException, NegativeAmountException, InsufficientBalanceException, InvalidAccountNumberException {


        Scanner scanner = new Scanner(System.in);
        String login, password;
        int choice = 1;

        System.out.println("|||||Login In Progress|||||");
        System.out.println("Enter Login: ");
        login = scanner.next();
        System.out.println("Enter password: ");
        password = scanner.next();

        if (password.equals(personDao.getPersonByLogin(login).getPassword())) {
            System.out.println("NICE -ONE! - login accomplished");


            log.info("LOG: Success = person login in DB");
        } else {
            System.out.println("Incorrect  login or password");
            log.warn("LOG: FAILURE = attempting to log!");
            choice = 6;
        }
        while (choice != 6) {
            System.out.println("Good day to You Hardworking Employee");
            System.out.println("*************************");
            System.out.println(" 1. View All Accounts");
            System.out.println(" 2. Approve/Deny Account");
            System.out.println(" 3. Deposit Amount");
            System.out.println(" 4. Withdraw Amount");
            System.out.println(" 5. Transfer Amount");
            System.out.println(" 6. Back");

            boolean menu = true;
            while (menu) {
                System.out.println("*************************");
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

