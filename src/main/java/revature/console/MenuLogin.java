package revature.console;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import revature.dao.AccountDao;
import revature.dao.PersonDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.model.Account;
import revature.model.Person;
import revature.service.CustomerService;

import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.List;
import java.util.Scanner;

public class MenuLogin {

    PersonDao personDao = new PersonDao();
    AccountDao accountDao = new AccountDao();
    CustomerService cs = new CustomerService();
    private Logger log = LogManager.getLogger(MenuLogin.class);


    public void loggIn() throws SQLException, NegativeAmountException, InsufficientBalanceException, InvalidAccountNumberException {


        Scanner scanner = new Scanner(System.in);
        String login, password;
        int choice = 1;
        int perId;
        int accId = 0;
        System.out.println("|||||Login In Progress|||||");
        System.out.println("Enter Login: ");
        login = scanner.next();
        System.out.println("Enter password: ");
        password = scanner.next();

        if (password.equals(personDao.getPersonByLogin(login).getPassword())) {
            System.out.println("NICE -ONE! - login accomplished");
            perId = personDao.getPersonByLogin(login).getPersonId();
            accId = accountDao.getAccountsByPersonId(perId).getAccountId();
            log.info("LOG: Success = person login in DB");
        } else {
            System.out.println("Incorrect  login or password");
            log.warn("LOG: FAILURE = attempting to log!");
            choice = 5;
        }
        while (choice != 5) {
            System.out.println("*** Operation Menu ***");
            System.out.println();
            System.out.println(" 1. Check Balance");
            System.out.println(" 2. Deposit Amount");
            System.out.println(" 3. Withdraw Amount");
            System.out.println(" 4. Transfer Amount");
            System.out.println(" 5. Quit");

            boolean menu = true;
            while (menu) {
                System.out.print("Enter your choice ");
                String entry = scanner.next();
                choice = getInteger(entry);
                if (choice == 5) menu = false;
                if (choice >= 1 && choice <= 4) menu = false;
                if (choice <= 0 || choice > 5) System.out.print("The specified value is not supported ");
            }
            System.out.println();
            switch (choice) {
                case 1:
                    checkBalance(accId);
                    break;
                case 2:
                    cs.depositByCustomer(accId);
                    break;
                case 3:
                    cs.withdrawByCustomer(accId);
                    break;
                case 4:
                    cs.transferByCustomer(accId);
            }
        }

    }


    /********************************* Check Integer **********************************/
    public static Integer getInteger(String entry) {

        int intValue = 0;
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-'};
        String number = "";
        int count = 0;

        for (int i = 0; i < entry.length(); i++)
            for (char d : digits)
                if (d == entry.charAt(i)) {
                    ++count;
                    number += d;
                }

        if (count > 0 && count == entry.length()) {
            intValue = Integer.parseInt(number);
        }

        return intValue;

    }

    /********************************* Check Double **********************************/
    public static Double getDouble(String entry) {

        double doubleValue = 0;
        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '.'};
        String number = "";
        int count = 0;

        for (int i = 0; i < entry.length(); i++)
            for (char d : digits)
                if (d == entry.charAt(i)) {
                    ++count;
                    number += d;
                }

        if (count > 0 && count == entry.length()) {
            doubleValue = Double.parseDouble(number);
        }

        return doubleValue;

    }

    /********************************* Find Account **********************************/
    public static Boolean findAccount(Integer acct) throws SQLException {

        List<Account> accountList;

        AccountDao allAccounts = new AccountDao();
        accountList = allAccounts.getAllAccounts();

        boolean found = false;
        for (Account p : accountList) {
            if (p.getAccountId() == acct) {
                found = true;
                break;
            }

        }

        return found;

    }

    public static Boolean findPerson(String person) {

        List<Person> customerList;

        PersonDao allCustomers = new PersonDao();
        customerList = allCustomers.getAllPersons();

        boolean found = true;
        for (Person p : customerList) {
            if (p.getLogin().equals(person)) {
                found = false;
                break;
            }
        }

        return found;

    }

    public void checkBalance(int acc) throws SQLException {
        BigDecimal newBalance;
        String lName, fName, accType;

        int per;

        newBalance = accountDao.getAccountById(acc).getBalance();
        per = accountDao.getAccountById(acc).getPersonId();
        lName = personDao.getPersonById(per).getLastName();
        fName = personDao.getPersonById(per).getFirstName();
        accType= String.valueOf(accountDao.getAccountById(acc).getType());
        System.out.println(" Account(ID): " + acc + " Type Account: " + accType + " Name: " + fName + " " + lName);
        System.out.println(" Balance: " + " Euro " + newBalance);
        log.info(" LOG: Balance was accessed from Database successfully");

    }

//  DO NOT WANT TO  DELETE THIS - I MIGHT NEED IT LATER

   /* public void signUp(Scanner scanner) {
        System.out.println("To sign up, we'll need a username and password.");
        System.out.print("Login: ");

        // Because we are consuming the whole line, the scanner does not need to be flushed

        String login = scanner.nextLine();

        PersonDao personDao = new PersonDao();
        if (personDao.isLoginTaken(login)) {
            System.out.println("Login taken! Returning to main menu...");
            return;
        }

        System.out.print("Password (cover your screen): ");
        String password = scanner.nextLine();
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        System.out.print("Rank : ");
        String rank = scanner.nextLine();
        personDao.signUp( login, password, lastName, firstName, PersonRank.valueOf(rank.toUpperCase()));
        System.out.println("***Great****");
        log.info("Login:" + login + "has been added  to DB.");
        mainMenu(scanner);
    }

    public void loggIn(Scanner scanner) {

        System.out.println("Enter your Login");
        String login = scanner.nextLine();
        System.out.println("Enter your password");
        String password = scanner.nextLine();

        PersonDao personDao = new PersonDao();

        if (personDao.isAuthenticated(login, password)) {

            System.out.println("Logging correct");
        } else {
            System.out.println("Make sure You  have signed in!");
            System.exit(1);
        }
    }*/  /// I LL KEEP IT

}