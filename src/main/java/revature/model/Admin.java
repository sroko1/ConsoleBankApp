package revature.model;

import java.util.ArrayList;
import java.util.List;

public class Admin extends Person {

    protected int id;
    private static int idGenerator = 0;


    public List<Admin> adminList = new ArrayList<>();

    public Admin() {
        super();
        rank = PersonRank.ADMIN;
        id = idGenerator++;
        adminList.add(this);

    }


}
