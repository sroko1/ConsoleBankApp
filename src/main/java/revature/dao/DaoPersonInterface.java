package revature.dao;

import revature.model.Person;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DaoPersonInterface {

    Person getPersonById(int personId) throws SQLException;

    Person getPersonByLogin(String login) throws SQLException;

    void addPerson(Person person) throws SQLException;

    void deletePerson(int personId) throws SQLException;

    List<Person> getAllPersons() throws SQLException;

    void updatePerson(int personId, String lastName, String firstName) throws SQLException;


}