package revature.model;



public class Person {

    protected int personId;
    protected String lastName;
    protected String firstName;
    protected String login;
    protected String password;
    protected PersonRank rank;


    public Person(int personId, String lastName, String firstName, String login, String password, PersonRank rank) {
        this.personId = personId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.login = login;
        this.password = password;
        this.rank = rank;
    }

    public Person() {

    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonRank getRank() {
        return rank;
    }

    public void setRank(PersonRank rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", rank=" + rank +
                '}';
    }
}

