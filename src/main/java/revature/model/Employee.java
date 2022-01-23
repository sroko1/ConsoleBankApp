package revature.model;

import java.util.ArrayList;
import java.util.List;

public class Employee extends Person{


    protected int id;
    private static int idGenerator = 0;

    public List<Employee> employeeList = new ArrayList<>();

    public Employee() {
        super();
        rank = PersonRank.EMPLOYEE;
        id = idGenerator++;
        employeeList.add(this);
    }
}
