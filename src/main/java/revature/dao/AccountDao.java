package revature.dao;

import revature.model.Account;
import revature.model.AccountType;
import revature.model.Person;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao implements DaoAccountInterface {

    private Connection connection;

    public AccountDao(Connection connection) {
        this.connection = connection;
    }

    public AccountDao() {

    }

    @Override
    public List<Account> getAllAccounts() throws SQLException {

        List<Account> accountList = new ArrayList<>();
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT * FROM account AS a INNER JOIN person AS p ON a.person_id = p.person_id";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            accountList.add(new Account(rs.getInt("acc_id"), rs.getBigDecimal("balance"),
                    rs.getString("acc_status"), rs.getString("acc_number"),
                    AccountType.valueOf(rs.getString("type_acc")),
                    rs.getString("sec_perName"), rs.getInt("person_id")));
        }
        return accountList;
    }

    @Override
    public List<Account> getWaitingAccounts() throws SQLException {
        List<Account> accounts = new ArrayList<>();
        Connection con = ConnectionManager.getConnection();
        String sql = "SELECT * FROM account WHERE acc_status <> 'Waiting'";

        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            accounts.add(new Account(rs.getInt("acc_id"), rs.getBigDecimal("balance"),
                    rs.getString("acc_status"), rs.getString("acc_number"),
                    AccountType.valueOf(rs.getString("type_acc")),
                    rs.getString("sec_perName"), rs.getInt("person_id")));
        }
        return accounts;
    }

    @Override
    public void updateAccount(Account account) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String sql = "UPDATE account SET acc_status = ?, type_acc = ?, sec_perName = ? "
                + "WHERE acc_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, account.getStatus());
        ps.setString(2, String.valueOf(account.getType()));
        ps.setString(3, account.getSecondPersonName());
        ps.setInt(4, account.getPersonId());

        ps.executeUpdate();

    }

    @Override
    public void openAccount(Account account) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "INSERT INTO account (acc_status, balance, acc_number, type_acc, sec_perName, person_id) "
                + "VALUES ('Pending',0, ?, ?, ?, ?)";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, account.getNumber());
        ps.setString(2, String.valueOf(account.getType()));
        ps.setString(3, account.getSecondPersonName());
        ps.setInt(4, account.getPersonId());

        ps.executeUpdate();

    }

    @Override
    public void closeAccount(int accountId) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        String sql = "delete from \"account\" where \"acc_id\"= ?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, accountId);

        ps.executeUpdate();

    }


    @Override
    public Account getAccountById(int accountId) throws SQLException {
        Connection conn = ConnectionManager.getConnection();
        String sql = " SELECT * FROM account WHERE acc_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, accountId);
        ResultSet rs = ps.executeQuery();

        Account ac;
        if (rs.next()) {
            rs.getInt("acc_id");
        }
        ac = new Account(rs.getInt("acc_id"),
                rs.getBigDecimal("balance"), rs.getString("acc_status"), rs.getString("acc_number"),
                AccountType.valueOf(rs.getString("type_acc")),
                rs.getString("sec_perName"), rs.getInt("person_id"));
        return ac;
    }

    @Override
    public Account getAccountsByPersonId(int personId) throws SQLException {
        Account account = null;
        Connection con = ConnectionManager.getConnection();
        String sql = " SELECT * FROM account WHERE person_id = ?";

        PreparedStatement ps = con.prepareStatement(sql);
        ps.setInt(1, personId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            rs.getInt("acc_id");
        }
        account = new Account(rs.getInt("acc_id"), rs.getBigDecimal("balance"),
                rs.getString("acc_status"), rs.getString("acc_number"),
                AccountType.valueOf(rs.getString("type_acc")),
                rs.getString("sec_perName"), rs.getInt("person_id"));

        return account;

    }

    public void deposit(int accountId, BigDecimal amount) {
        try {
            Connection conn = ConnectionManager.getConnection();
            String sql = "UPDATE account SET balance = balance + ? WHERE acc_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void withdraw(int accountId, BigDecimal amount) {

        try {
            Connection con = ConnectionManager.getConnection();
            String sql = "UPDATE account SET balance = balance - ? WHERE acc_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void transfer(int accountId1, int accountId2, BigDecimal amount) {

        try {
            Connection con = ConnectionManager.getConnection();
            String sql = "UPDATE account SET balance = balance - ? WHERE acc_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId1);

            ps.execute();

            sql = "UPDATE account SET balance = balance + ? WHERE acc_id = ?";

            ps = con.prepareStatement(sql);
            ps.setBigDecimal(1, amount);
            ps.setInt(2, accountId2);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void changeStatus(int accountId, String status) {

        try {
            Connection con = ConnectionManager.getConnection();
            String sql = "UPDATE account SET acc_status = ? WHERE acc_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, accountId);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /********* Method for Javalin Only***********/

    public boolean deleteAccount(Account account) {

        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.execute("DELETE FROM account WHERE acc_id = \'"
                    + account.getAccountId() + "\'");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    /********* Method for Javalin Only***********/

    public boolean modernizeAccount(Account account) {

        Statement statement;
        try {
            statement = connection.createStatement();
            return statement.execute("UPDATE account SET "
                    + "acc_status = \'" + account.getStatus() + "\',"
                    + "balance = \'" + account.getBalance() + "\',"
                    + "type_acc\'" + account.getType() + "\',"
                    + "sec_pername\'" + account.getSecondPersonName() + "\'"
                    + "WHERE acc_number = \'" + account.getNumber() + "\';");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
return false;
    }

}



