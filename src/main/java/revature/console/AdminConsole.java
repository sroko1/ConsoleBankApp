package revature.console;

import revature.dao.PersonDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.service.AdminService;
import revature.service.EmployeeService;

import java.sql.SQLException;
import java.util.Scanner;

import static revature.console.MenuLogin.getInteger;
import static revature.console.MenuLogin.log;


public class AdminConsole {
    PersonDao personDao = new PersonDao();
    static AdminService as = new AdminService();
    static EmployeeService es = new EmployeeService();
    static Scanner scanner = new Scanner(System.in);
    static AdminConsole adminConsole = new AdminConsole();

    static void showAdmin() throws NegativeAmountException, SQLException, InsufficientBalanceException, InvalidAccountNumberException {

        int choice = 1;

        while (choice != 2) {

            System.out.println("Welcome to Good Bank Dear Admin ! Would you like to...");
            System.out.println("****************************");
            System.out.println("   1 - Log in ");
            System.out.println("   2 - Back");

            boolean menu = true;
            while (menu) {
                System.out.println("SELECT OPTION");
                String entry = scanner.next();
                choice = MenuLogin.getInteger(entry);
                if (choice == 2) menu = false;
                if (choice == 1) menu = false;
                if (choice <= 0 || choice > 2)
                    System.out.println("The specified value is not supported");

            }
            System.out.println();
            switch (choice) {
                case 1:
                    adminConsole.loggInAdmin();

            }
        }
    }

    void loggInAdmin() throws SQLException, InvalidAccountNumberException, NegativeAmountException, InsufficientBalanceException {


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
            choice = 8;
        }
            while (choice != 8) {
                System.out.println("Good day to You dear Admin");
                System.out.println("********************************");
                System.out.println(" 1. View All Accounts");
                System.out.println(" 2. Edit Account <- IN PROGRESS");
                System.out.println(" 3. Approve/Deny Account");
                System.out.println(" 4. Close Account");
                System.out.println(" 5. Deposit Amount");
                System.out.println(" 6. Withdraw Amount");
                System.out.println(" 7. Transfer Amount");
                System.out.println(" 8. Back");

                boolean menu = true;
                while (menu) {
                    System.out.println("********************************");
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
                        as.editAccount();  //TODO
                        break;
                    case 3:
                        es.approveAccount();
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




