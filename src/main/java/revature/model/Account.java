package revature.model;

import revature.exception.InsufficientBalanceException;
import revature.exception.NegativeAmountException;

import java.math.BigDecimal;


public class Account {
    protected int accountId;
    protected BigDecimal balance;
    protected String status;
    protected String number;
    protected AccountType type;
    protected String secondPersonName;
    protected int personId;


    public Account() {
        this.number = "" + System.nanoTime();
        this.balance = BigDecimal.ZERO;
        this.number = "" + System.nanoTime();
    }


    public Account(int accountId, BigDecimal balance, String status, String number, AccountType type, String secondPersonName, int personId) {

        this.accountId = accountId;
        this.balance = balance;
        this.status = status;
        this.number = number;
        this.type = type;
        this.secondPersonName = secondPersonName;
        this.personId = personId;
    }

    public int getAccountId() {
        return accountId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", balance=" + balance +
                ", status='" + status + '\'' +
                ", number='" + number + '\'' +
                ", type=" + type +
                ", secondPersonName='" + secondPersonName + '\'' +
                ", personId=" + personId +
                '}';
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public String getSecondPersonName() {
        return secondPersonName;
    }

    public void setSecondPersonName(String secondPersonName) {
        this.secondPersonName = secondPersonName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public void deposit(BigDecimal amount) throws NegativeAmountException {
        if (amount.doubleValue() <= 0) {
            throw new NegativeAmountException("Negative or Zero Amount");
        }
        balance = balance.add(amount);
    }

    public void withdrawal(BigDecimal amount) throws NegativeAmountException, InsufficientBalanceException {
        if (amount.doubleValue() <= 0) {
            throw new NegativeAmountException("Negative or Zero Amount");
        }
        if ((amount.compareTo(balance)) > 0) {
            throw new InsufficientBalanceException("Not enough funds in your account");
        }
        balance = balance.subtract(amount);
    }

    public void transfer(Account destination, BigDecimal amount) throws NegativeAmountException, InsufficientBalanceException {
        withdrawal(amount);
        destination.deposit(amount);
    }

}
