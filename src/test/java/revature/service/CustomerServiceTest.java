package revature.service;

import org.junit.jupiter.api.*;
import revature.exception.InsufficientBalanceException;
import revature.exception.NegativeAmountException;

import java.sql.SQLException;

public class CustomerServiceTest {

    static CustomerService cs;
    static EmployeeService es;


    @BeforeAll
    public static void setUpToSetUp() {
        cs = new CustomerService();
    }

    @BeforeEach
    public void setup() {
        cs = new CustomerService();
    }

    @AfterEach
    public void reset() {
        cs = new CustomerService();
    }

    @AfterAll
    public static void allDone() {
        cs = new CustomerService();
    }

}
