package revature.model;


public class Customer extends Person {


    public Customer() {
        super();
        rank = PersonRank.CUSTOMER;
    }

    @Override
    public int getPersonId() { return super.getPersonId(); }

    @Override
    public String getLastName() {
        return super.getLastName();
    }

    @Override
    public String getFirstName() {
        return super.getFirstName();
    }

    @Override
    public String getLogin() {
        return super.getLogin();
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }
}