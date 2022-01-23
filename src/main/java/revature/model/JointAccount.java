package revature.model;

import java.math.BigDecimal;

public class JointAccount extends Account {
    public JointAccount() {
        super();
        type = AccountType.JOINT;
    }
    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public BigDecimal getBalance() {
        return balance;
    }
}