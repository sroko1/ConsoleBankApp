package revature.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import revature.dao.AccountDao;
import revature.dao.PersonDao;
import revature.exception.InvalidAccountNumberException;
import revature.model.Account;
import revature.model.AccountType;
import revature.model.Person;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static revature.console.MenuLogin.findAccount;
import static revature.console.MenuLogin.getInteger;
import static revature.model.AccountType.valueOf;

public class AdminService {

    static Logger log = LogManager.getLogger(AdminService.class);
    static PersonDao personDao = new PersonDao();
    static AccountDao accountDao = new AccountDao();
    Scanner sc = new Scanner(System.in);

    public void closeAccount() throws SQLException, InvalidAccountNumberException {


        System.out.print(" Enter Account to be deleted: ");
        String entry = sc.next();
        int acct = getInteger(entry);

        boolean found = findAccount(acct);
        AccountDao findAcc = new AccountDao();
        PersonDao deletePer = new PersonDao();


        if (acct > 0 && found) {
            int per = findAcc.getAccountById(acct).getPersonId();
            deletePer.deletePerson(per);
            System.out.println("Account# [" + acct + "] deleted successfully ");
            log.info(" LOG: Account was deleted successfully");
        } else {
            throw new InvalidAccountNumberException("Wrong account");
        }

    }

    public void viewAccounts() throws SQLException {

        List<Account> accountList;
        List<Person> personList;
        PersonDao allPersons = new PersonDao();
        AccountDao allAccounts = new AccountDao();
        personList = allPersons.getAllPersons();
        accountList = allAccounts.getAllAccounts();

        System.out.println("AllBank Accounts \n");
        System.out.println("  Acct#    Balance     Status     Number   Type   First Name    Last Name");

        for (Account a : accountList) {
            System.out.printf("  %d     EURO  %6.2f    %8s   %8s   %8s", a.getAccountId(), a.getBalance(),
                    a.getStatus(), a.getNumber(), a.getType());
            for (Person p : personList) {
                if (p.getPersonId() == a.getPersonId()) {
                    System.out.printf("  %10s  %10s  %n", p.getLastName(), p.getFirstName());

                }
            }
        }
        System.out.println();
        log.info(" LOG: All Accounts were listed from Database successfully");

    }

    public void editAccount() throws SQLException {
        System.out.println(" Enter an Account To be edited: ");
        String entry = sc.next();
        int acc = getInteger(entry);
        int i = 0;

        boolean found = findAccount(acc);
        if (acc > 0 && found) {
            BigDecimal balance = accountDao.getAccountById(acc).getBalance();
            int perId = accountDao.getAccountById(acc).getPersonId();
            String lName = personDao.getPersonById(perId).getLastName();
            String fName = personDao.getPersonById(perId).getFirstName();
            String status = accountDao.getAccountById(acc).getStatus();
            String number = accountDao.getAccountById(acc).getNumber();
            AccountType type = accountDao.getAccountById(acc).getType();
            String common = accountDao.getAccountById(acc).getSecondPersonName();

            System.out.println("\n Customer (ID) : " + perId + " Customer Name: " + fName + " " + lName);
            System.out.println(" No.Account: " + number + " Account Balance:  " + "[Euro] " + balance + ") \n");

            boolean check = true;
            while (check) {
                System.out.print(" Account Type: " + "[" + type + "]" + " 1. Current  2. Savings  3. Joint: ");
                entry = sc.next();
                i = getInteger(entry);
                if (i >= 1 && i <= 3) {
                    check = false;
                } else {
                    System.out.println(" You have provided wrong account");
                }
            }
            switch (i) {
                case 0:
                    type = accountDao.getAccountById(acc).getType();
                    common = accountDao.getAccountById(acc).getSecondPersonName();
                    break;
                case 1:
                    type = valueOf("current".toUpperCase());
                    break;
                case 2:
                    type = valueOf("savings".toUpperCase());
                    break;
                case 3:
                    type = valueOf("joint".toUpperCase());
                    System.out.print(" Second Owner Name " + "[" + common + "]" + ": ");
                    common = sc.next();
                    if (common.equals("0")) {
                        common = accountDao.getAccountById(acc).getSecondPersonName();
                    }

            }
            check = true;
            while (check) {
                System.out.print(" Account Status: " + "[" + status + "]" + " 1. Approved  2. Denied  3. Pending: ");
                entry = sc.next();
                i = getInteger(entry);
                if (i >= 1 && i <= 3) {
                    check = false;
                } else {
                    System.out.println("Wrong Status Account!");
                }

            }
            switch (i) {
                case 0:
                    status = accountDao.getAccountById(acc).getStatus();
                    break;
                case 1:
                    status = "Approved";
                    break;
                case 2:
                    status = "Denied";
                    break;
                case 3:
                    status = "Pending";
            }

            Account perAcc = new Account(acc, balance, status, number, type, common, perId);
            AccountDao updateAccount = new AccountDao();
            updateAccount.updateAccount(perAcc);
            System.out.println("\n Account(ID): " + acc + " has been updated for Customer(ID) " + perId +  "\n");
            log.info(" LOG:  Account was updated successfully!");
        }else {
            System.out.println("Invalid account");
        }
    }


}

