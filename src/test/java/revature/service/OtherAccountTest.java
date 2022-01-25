package revature.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import revature.dao.PersonDao;
import revature.model.Account;
import revature.model.AccountType;
import revature.model.Person;
import revature.model.PersonRank;

import java.math.BigDecimal;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class OtherAccountTest {

    Account testAccount1 = new Account(1, BigDecimal.valueOf(45555), "Approved", "4444", AccountType.CURRENT, "Wife", 2);
    Account testAccount2 = new Account(2, BigDecimal.valueOf(65555), "Approved", "7744", AccountType.SAVINGS, "Son", 1);

    Person testPerson1 = new Person(1, "War", "Rob", "kol", "heineken", PersonRank.CUSTOMER);
    Person testPerson2 = new Person(2, "Bar", "Dylan", "Chain", "groom", PersonRank.CUSTOMER);

    @Test
    void createAccount() {
        Assertions.assertNotNull(testAccount1);
        Assertions.assertNotNull(testAccount2);
    }

    @Test
    void createPerson() {
        Assertions.assertNotNull(testPerson1);
        Assertions.assertNotNull(testPerson2);
    }

    @Test
    void checkNames() {

        PersonDao pd = new PersonDao();
        boolean test = pd.areLastNameAndFirstExist("Ewing", "Patrick");
        assertFalse(test);
    }

    @Test
    void checkNames2() {

        PersonDao pd = new PersonDao();
        boolean test = pd.areLastNameAndFirstExist("Won", "Ton");
        assertTrue(test);
    }

    @Test
    void checkLoginAndPassword() {
        PersonDao pd = new PersonDao();
        boolean test = pd.isAuthenticated("ball", "8888");
        assertTrue(test);
    }
    @Test
    void lastOne() {

       String a = testPerson1.getLastName();
        assertTrue(Objects.equals(a, "War"));


    }


}