package revature.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import revature.exception.InsufficientBalanceException;
import revature.exception.NegativeAmountException;
import revature.model.CurrentAccount;

import java.math.BigDecimal;

public class CurrentAccountTest {

    @Test
    void  shouldInvokeDeposit() throws NegativeAmountException {
        //given
        CurrentAccount account = new CurrentAccount();
        BigDecimal testAmount = BigDecimal.valueOf(1001);

        //when
        account.deposit(testAmount);

        //then
        Assertions.assertEquals(testAmount,account.getBalance());

    }
    @Test
    public void shouldInvokeWithdrawal() throws NegativeAmountException, InsufficientBalanceException {
        //given
        CurrentAccount account  = new CurrentAccount();
        BigDecimal testAmount1 = BigDecimal.valueOf(1100);
        BigDecimal testAmount2 = BigDecimal.valueOf(500);
        account.deposit(testAmount1);
        //when
        account.withdrawal(testAmount2);
        //then
        Assertions.assertEquals(testAmount1.subtract(testAmount2), account.getBalance());
    }

    @Test
    public void shouldInvokeTransfer() throws NegativeAmountException, InsufficientBalanceException {
        //given
        CurrentAccount accountSrc = new CurrentAccount();
        CurrentAccount accountDst = new CurrentAccount();
        BigDecimal testAmount1 = BigDecimal.valueOf(1000);
        BigDecimal testAmount2 = BigDecimal.valueOf(700);
        accountSrc.deposit(testAmount1);

        //when
        accountSrc.transfer(accountDst, testAmount2);

        //then
        Assertions.assertEquals(testAmount1.subtract(testAmount2), accountSrc.getBalance());
        Assertions.assertEquals(testAmount2, accountDst.getBalance());

    }



}

