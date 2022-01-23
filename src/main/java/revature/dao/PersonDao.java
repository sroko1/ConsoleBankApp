package revature.dao;

import revature.model.Customer;
import revature.model.Person;
import revature.model.PersonRank;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDao implements DaoPersonInterface {

    private Connection con;

    public PersonDao(Connection con) {
        this.con = con;
    }

    public PersonDao() {

    }

    @Override
    public List<Person> getAllPersons() {
        List<Person> personList = new ArrayList<>();

        try {
            Connection connection = ConnectionManager.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM person");

            while (rs.next()) {
                Person person = new Person(rs.getInt("person_id"), rs.getString("last_name"),
                        rs.getString("first_name"), rs.getString("login"),
                        rs.getString("password"), PersonRank.valueOf(rs.getString("rank_person")));
                personList.add(person);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personList;
    }

    @Override
    public Person getPersonByLogin(String login) {

        Person per = null;
        try {
            Connection conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM person WHERE login =?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, login);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                rs.getInt("person_id");
            }
            per = new Person(
                    rs.getInt("person_id"),
                    rs.getString("last_name"),
                    rs.getString("first_name"),
                    rs.getString("login"),
                    rs.getString("password"),
                    PersonRank.valueOf(rs.getString("rank_person")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return per;
    }

    @Override
    public Person getPersonById(int personId) {
        Person person = null;
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM person WHERE person_id =?");
            pr.setInt(1, personId);

            ResultSet rs = pr.executeQuery();

            if (rs.next()) {
                rs.getInt("person_id");
            }
            person = new Person(rs.getInt("person_id"), rs.getString("last_name"),
                    rs.getString("first_name"), rs.getString("login"),
                    rs.getString("password"), PersonRank.valueOf(rs.getString("rank_person")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return person;
    }

    @Override
    public void addPerson(Person person) throws SQLException {
        Connection con = ConnectionManager.getConnection();
        PreparedStatement pr = con.prepareStatement("INSERT INTO person(last_name,first_name,login, password, rank_person) VALUES (?,?,?,?,?)");
        pr.setString(1, person.getLastName());
        pr.setString(2, person.getFirstName());
        pr.setString(3, person.getLogin());
        pr.setString(4, person.getPassword());
        pr.setString(5, String.valueOf(person.getRank()));

        pr.executeUpdate();

    }

    @Override
    public void deletePerson(int personId) throws SQLException {
        Connection connection = ConnectionManager.getConnection();
        PreparedStatement pr = connection.prepareStatement("DELETE FROM person WHERE person_id = ?");
        pr.setInt(1, personId);
        pr.executeUpdate();

    }


    @Override
    public void updatePerson(int personId, String firstName, String lastName) throws SQLException {

        Connection conn = ConnectionManager.getConnection();

        String sql = "UPDATE customer SET first_name = ?, last_name = ? "
                + "WHERE person_id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, lastName);
        ps.setString(2, firstName);
        ps.setInt(3, personId);

        ps.execute();

    }

    /********Interesting method  were used i previous  version********/
    public boolean isLoginTaken(String login) {

        try {
            Connection c = ConnectionManager.getConnection();
            PreparedStatement preparedStatement = c.prepareStatement("SELECT * FROM person WHERE login = ?");
            preparedStatement.setString(1, login);
            ResultSet results = preparedStatement.executeQuery();

            if (results.next())
                return true;

            return false;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /********Interesting method  were used i previous  version********/
    public boolean isAuthenticated(String login, String password) {
        try {
            Connection con = ConnectionManager.getConnection();
            PreparedStatement pr = con.prepareStatement("SELECT * FROM person WHERE login =? AND password =?");
            pr.setString(1, login);
            pr.setString(2, password);
            ResultSet rs = pr.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    /********Interesting method  were used i previous  version********/
    public void signUp(String lastName, String firstName, String login, String password, PersonRank rank) {

        try {
            Connection c = ConnectionManager.getConnection();
            PreparedStatement statement = c.prepareStatement(" INSERT INTO " +
                    "person( last_name, first_name, login, password, rank_person) VALUES (?, ?,?,?,?)");
            statement.setString(1, lastName);
            statement.setString(2, firstName);
            statement.setString(3, login);
            statement.setString(4, password);
            statement.setString(5, String.valueOf(rank.ordinal()));


            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int person_id, String password) {
        try {
            Connection con = ConnectionManager.getConnection();
            String sql = "UPDATE person SET password =? WHERE person_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, password);
            ps.setInt(2, person_id);

            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /********Interesting method  were used i previous  version********/

    public boolean areLastNameAndFirstExist(String lastName, String firstName) {

        try {
            Connection connection = ConnectionManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM person WHERE last_name = ? AND first_name = ?");
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);

            // 2. Run a query using the statement to find any records with the given username
            //ResultSet results = statement.executeQuery("SELECT * FROM customer WHERE lastName = \'" + lastname + "\'");
            ResultSet results = preparedStatement.executeQuery();

            return results.next();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    /********* Method for Javalin Only***********/
    public boolean updatePerson(Person person) {

        Statement statement;
        try {
            statement = con.createStatement();
            return statement.execute("UPDATE persons SET "
                    + "last_name = \'" + person.getLastName() + "\',"
                    + "first_name = \'" + person.getFirstName() + "\',"
                    + "password\'" + person.getPassword() + "\',"
                    + "rank_person\'" + person.getRank() + "\'"
                    + "WHERE login = \'" + person.getLogin() + "\';");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    /********* Method for Javalin Only***********/

    public boolean deleteUser(Person person) {

        Statement statement;
        try {
            statement = con.createStatement();
            return statement.execute("DELETE FROM person WHERE person_id = \'"
                    + person.getPersonId() + "\'");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }
}
