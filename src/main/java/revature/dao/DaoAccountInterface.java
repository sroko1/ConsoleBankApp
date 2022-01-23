package revature.dao;

import revature.model.Account;
import revature.model.Person;

import java.sql.SQLException;
import java.util.List;

public interface DaoAccountInterface {

    Account getAccountById(int accountId) throws SQLException;

    Account getAccountsByPersonId(int personId) throws SQLException;

    List<Account> getAllAccounts() throws SQLException;

     List<Account> getWaitingAccounts() throws SQLException;

    void openAccount(Account account) throws SQLException;

    void closeAccount(int accountId) throws SQLException;

    void updateAccount(Account account) throws SQLException;
}
