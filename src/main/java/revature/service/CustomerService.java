package revature.service;


import revature.dao.AccountDao;
import revature.dao.PersonDao;
import revature.exception.InsufficientBalanceException;
import revature.exception.InvalidAccountNumberException;
import revature.exception.NegativeAmountException;
import revature.model.Account;
import revature.model.AccountType;
import revature.model.Person;
import revature.model.PersonRank;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Scanner;

import static revature.console.MenuLogin.*;
import static revature.model.AccountType.valueOf;
import static revature.service.AdminService.log;
import static revature.service.AdminService.personDao;

public class CustomerService {


    Scanner sc = new Scanner(System.in);
    AccountDao accountDao = new AccountDao();
    BigDecimal oldBalance;
    String acc_number;
    BigDecimal newBalance;
    BigDecimal withdrawal = BigDecimal.valueOf(0);
    BigDecimal deposit = BigDecimal.valueOf(0);
    BigDecimal transfer = BigDecimal.valueOf(0);

    public void depositByCustomer(int acc) throws SQLException, NegativeAmountException {


        oldBalance = accountDao.getAccountById(acc).getBalance();
        acc_number = accountDao.getAccountById(acc).getNumber();
        if (accountDao.getAccountById(acc).getStatus().equals("Approved")) {

            boolean check = true;
            while (check) {
                System.out.println("Balance: " + " [Euro] " + oldBalance + "." + " Provide deposit Amount: ");
                String value = sc.next();
                deposit = BigDecimal.valueOf(getDouble(value));

                if (deposit.doubleValue() > 0) {
                    check = false;

                } else {
                    throw new NegativeAmountException("Negative or Zero Amount");
                }
            }
            newBalance = BigDecimal.valueOf(oldBalance.doubleValue() + deposit.doubleValue());
            accountDao.deposit(acc, deposit);
            System.out.println(" Account ID: " + acc + " ,No: " + acc_number + " Old Balance: " + " [Euro] " + oldBalance + "  =  New Balance: " + " [Euro] " + newBalance);
            log.info(" LOG: Deposit was done and saved into Database successfully");
        } else {
            System.out.println("Account  is  waiting to approved");
        }
    }

    public void withdrawByCustomer(int acc) throws SQLException, NegativeAmountException,
            InsufficientBalanceException {


        oldBalance = accountDao.getAccountById(acc).getBalance();
        if (accountDao.getAccountById(acc).getStatus().equals("Approved")) {
            boolean check = true;
            while (check) {
                System.out.println(" Balance: " + " Euro " + oldBalance + "Provide withdrawal Amount: ");
                String value = sc.next();
                withdrawal = BigDecimal.valueOf(getDouble(value));

                if (withdrawal.doubleValue() > 0) {
                    check = false;
                } else {
                    throw new NegativeAmountException("Negative or Zero Amount");
                }
            }
            if (withdrawal.doubleValue() > oldBalance.doubleValue()) {
                throw new InsufficientBalanceException("Not enough funds in your account");
            } else {
                newBalance = BigDecimal.valueOf(oldBalance.doubleValue() - withdrawal.doubleValue());
                accountDao.withdraw(acc, withdrawal);
                System.out.println(" Account ID" + acc + " Old Balance: " + " Euro " + oldBalance + "  = New Balance: " + " Euro " + newBalance);
                log.info(" LOG: Withdraw was done and saved into Database successfully");
            }
        } else {
            System.out.println("Account  is  waiting to approved");
        }
    }

    public void transferByCustomer(int acc) throws SQLException, InvalidAccountNumberException, NegativeAmountException, InsufficientBalanceException {
        int acc2;
        int acc1;
        boolean check = false;
        oldBalance = accountDao.getAccountById(acc).getBalance();
        if (accountDao.getAccountById(acc).getStatus().equals("Approved")) {
            System.out.println("Provide an account You  would like  to transfer to: ");
            String entry = sc.next();
            acc1 = getInteger(entry);

            boolean found = findAccount(acc1);
            if (acc1 > 0 && found) {
                acc2 = accountDao.getAccountById(acc1).getAccountId();
                if (accountDao.getAccountById(acc2).getStatus().equals("Waiting")) {
                    check = true;
                }
            } else {

                throw new InvalidAccountNumberException(" You have provided wrong account");
                // previous = 1;

            }
            if (acc2 > 0 && check) {
                while (check) {
                    System.out.print("Balance (Euro " + oldBalance + ")" + ", Provide the transfer amount: ");
                    entry = sc.next();
                    transfer = BigDecimal.valueOf(getDouble(entry));

                    if (transfer.doubleValue() > 0) {
                        check = false;
                    } else {
                        throw new NegativeAmountException("Negative or Zero Amount");
                    }
                }
            }
            if (transfer.doubleValue() > oldBalance.doubleValue()) {
                throw new InsufficientBalanceException("Not enough funds in your account");
            } else if (transfer.doubleValue() > 0) {
                newBalance = BigDecimal.valueOf(oldBalance.doubleValue() - transfer.doubleValue());
                accountDao.transfer(acc, acc2, transfer);
                System.out.println(" Account ID " + acc + "   Old Balance: " +" [Euro] " + oldBalance + " = New Balance: " + " [Euro] " + newBalance);
                log.info(" LOG: Transfer was done and saved into Database successfully");
            } else if (!check && acc > 0) {
                System.out.println("Account(ID) " + acc1 + "is waiting for approval");
            }
        } else {
            System.out.println("Account  is  waiting to approved");
        }

    }


    public void openAccount() throws SQLException {


        boolean check = true;
        int choice = 1, accId, perId;
        String fName, lName, secPer = "", login = "", number, password, passwordConfirmed;
        AccountType acType = null;


        while (check) {
            System.out.println("Login: ");
            login = sc.next();
            boolean customer = findPerson(login);
            if (customer) {
                check = false;
            } else {
                System.out.println(" Provided login is already taken");
            }
        }
        System.out.println(" Password: ");
        password = sc.next();
        check = true;
        while (check) {
            System.out.println(" Please confirm password");
            passwordConfirmed = sc.next();

            if (passwordConfirmed.equals(password)) {
                check = false;
            } else {
                System.out.println(" Confirmed password must be the same as password");
            }
        }
        System.out.println(" Last Name: ");
        lName = sc.next();
        System.out.println(" First name:  ");
        fName = sc.next();
        System.out.println("Account Number");
        number = sc.next();
        check = true;

        System.out.println(" What Kind of account it gonna be? ");


        while (check) {
            System.out.print(" Account Type (1. CURRENT 2. SAVINGS  3.JOINT): ");
            String entry = sc.next();
            choice = getInteger(entry);

            if (choice >= 1 && choice <= 3) {
                check = false;
            } else {
                System.out.println(" The specified value is not supported");
            }
        }

        switch (choice) {
            case 1:
                acType = valueOf("current".toUpperCase());
                break;
            case 2:
                acType = valueOf("savings".toUpperCase());
                break;
            case 3:
                acType = valueOf("joint".toUpperCase());
        }
        if (choice == 3) {
            System.out.println(" Information about Second Person is needed");
            secPer = sc.next();
        }

        Person person = new Person(0, lName, fName, login, password, PersonRank.CUSTOMER);
        PersonDao newPerson = new PersonDao();
        newPerson.addPerson(person);

        int perSize = newPerson.getAllPersons().size();
        if (perSize == 0) {
            perId = perSize + 1;

        } else {
            perId = perSize;
        }

        Account account = new Account(0, BigDecimal.ZERO, "Pending", number, acType, secPer, perId);
        AccountDao newAcc = new AccountDao();
        newAcc.openAccount(account);

        int accSize = newAcc.getAllAccounts().size();
        if (accSize == 0) {
            accId = accSize + 1;
        } else {
            accId = accSize;
        }
        System.out.println(" *** New Account has been created with [Pending] status for the management review and approval. ***");
        log.info(" LOG: New customer account has been created successfully");
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
}

























