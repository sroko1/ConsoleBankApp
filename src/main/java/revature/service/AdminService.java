package revature.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import revature.console.AdminConsole;
import revature.dao.AccountDao;
import revature.dao.PersonDao;
import revature.exception.InvalidAccountNumberException;
import revature.model.Account;
import revature.model.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static revature.console.MenuLogin.findAccount;
import static revature.console.MenuLogin.getInteger;

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


        if (acct > 0 && found) {
            int per = accountDao.getAccountById(acct).getPersonId();
            personDao.deletePerson(per);
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
        personList =allPersons.getAllPersons();
        accountList = allAccounts.getAllAccounts();

        System.out.println("AllBank Accounts \n");
        System.out.println("  Acct#    Balance     Status     Number   Type   First Name    Last Name");

        for (Account a : accountList)  {
            System.out.printf("  %d     $ %6.2f    %8s   %8s   %8s", a.getAccountId(), a.getBalance(),
                    a.getStatus(),a.getNumber(),a.getType());
           for (Person p : personList) {
               if (p.getPersonId() == a.getPersonId()) {
                   System.out.printf("  %10s  %10s  %n", p.getLastName(),p.getFirstName());

               }
           }
        }
        System.out.println();
        log.info(" LOG: All Accounts were listed from Database successfully");

    }


}

