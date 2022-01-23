package revature.account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import revature.exception.NegativeAmountException;
import revature.model.SavingsAccount;

import java.math.BigDecimal;

public class SavingsAccountTest {

    @Test
    public void shouldInvokePayment() throws NegativeAmountException {
        //given

        SavingsAccount account = new SavingsAccount();
        BigDecimal testAmount = BigDecimal.valueOf(1000);

        //when
        account.deposit(testAmount);

        //then
        BigDecimal expected = testAmount.multiply(BigDecimal.valueOf(account.getSavingFactor()));
        Assertions.assertEquals(expected, account.getBalance());
    }

}

