package revature.service;

import org.apache.logging.log4j.LogManager;
import revature.console.MenuLogin;
import revature.dao.AccountDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.model.Account;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static revature.service.AdminService.log;

public class EmployeeService {

    CustomerService cs = new CustomerService();
    Scanner sc = new Scanner(System.in);

    //  Transaction  might  be done by BANK STAFF

    public void deposit() throws SQLException, NegativeAmountException {

        System.out.println("Provide Account to DO DEPOSIT: ");
        String entry = sc.next();
        int account = MenuLogin.getInteger(entry);

        boolean found = MenuLogin.findAccount(account);
        if (account > 0 && found) {
            cs.depositByCustomer(account);
        } else {
            System.out.println("Invalid Account!");
        }
    }

    public void withdrawal() throws SQLException, NegativeAmountException, InsufficientBalanceException {
        System.out.println("Provide Account to DO WITHDRAWAL: ");
        String entry = sc.next();
        int account = MenuLogin.getInteger(entry);

        boolean found = MenuLogin.findAccount(account);
        if (account > 0 && found) {
            cs.withdrawByCustomer(account);
        } else {
            System.out.println("Invalid Account!");
        }
    }

    public void transfer() throws SQLException, NegativeAmountException, InsufficientBalanceException, InvalidAccountNumberException {
        System.out.println(" Provide Account to transfer From");
        String entry = sc.next();
        int account = MenuLogin.getInteger(entry);

        boolean found = MenuLogin.findAccount(account);
        if (account > 0 && found) {
            cs.transferByCustomer(account);
        } else {
            System.out.println("Invalid Account");
        }
    }

    public void approveAccount() throws SQLException {

        List<Account> accounts = new ArrayList<>();
        AccountDao incomingAccount = new AccountDao();
        accounts = incomingAccount.getWaitingAccounts();

        int acc;
        int choice = 0;

        int getSize = accounts.size();
        if (getSize > 0) {
            System.out.println(" Incoming Account List");
            System.out.println(" Account    TYPE    STATUS");
            System.out.println("****************************");
            for (Account a : accounts) {
                System.out.println(a.getAccountId() + "  " + a.getType() + "   " + a.getStatus());
                System.out.println();
            }
            String entry;
            System.out.println(" Enter Account ");
            entry = sc.next();
            acc = MenuLogin.getInteger(entry);

            boolean found = false;
            for (Account ac : accounts) {
                if (ac.getAccountId() == acc)
                    found = true;
            }
            if (found) {
                boolean check = true;
                while (check) {
                    System.out.println("\n Action (1. Approved  2. Denied): ");
                    entry = sc.next();
                    choice = MenuLogin.getInteger(entry);
                    if (choice>=1 && choice<=2) {
                        check = false;
                    }else {
                        System.out.println("The specified value is not supported");
                    }
                }
                String status = "";
                switch (choice) {
                    case 1:
                        status = "Approved";
                        break;
                    case 2:
                        status = "Denied";
                }
                if (choice == 1 || choice ==2) {
                    AccountDao approveAccount = new AccountDao();
                    approveAccount.changeStatus(acc,status);
                    System.out.println("Account Status: " + acc + "has changed to " + status);
                    log.info(" LOG: Account changed its status successfully.");
                }else {
                    System.out.println("The specified value is not supported.");
                }
            }else {
                System.out.println("Account is NOT on Pending/Denied List.");
            }

        }else {
            System.out.println("No  incoming accounts at this moment.");
        }

    }
}

