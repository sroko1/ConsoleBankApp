package revature.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;

import revature.dao.PersonDao;

import revature.model.Person;

import java.sql.Connection;
import java.util.List;


public class PersonController {

    Javalin app;
    Connection con;
    PersonDao pd;

    public PersonController(Javalin app, Connection con) {
        this.app = app;
        this.con = con;
        pd = new PersonDao(con);

        app.get("/persons/{login}", getPersonByLogin);
        app.post("/persons", addPerson);
        app.put("/persons/{login}", updatePerson);
        app.delete("/persons/{person_id}", deletePerson);
        app.get("/persons", getAllPersons);
    }

    public Handler getAllPersons = ctx -> {
        List<Person> personList = pd.getAllPersons();
        if (pd == null) {
            ctx.result("404 DB is empty");
            ctx.status(404);
        }

        ctx.json(personList);
        // Status code 200 means "OK"
        ctx.status(200);
    };

    public Handler addPerson = ctx -> {
        Person person = ctx.bodyAsClass(Person.class);
        pd.addPerson(person);

        ctx.status(201);
    };

    public Handler getPersonByLogin = ctx -> {
        Person p = pd.getPersonByLogin(ctx.pathParam("login"));
        ctx.json(p);

        ctx.status(200);
    };



    public Handler updatePerson = ctx -> {
        Person p = ctx.bodyAsClass(Person.class);
        if (pd.updatePerson(p))
            // Status code 204 means "Successfully updated"
            ctx.status(204);
            // Status code 400 means "Error occurred"
        else ctx.status(400);
    };

    public Handler deletePerson = ctx -> {
        Person p = ctx.bodyAsClass(Person.class);
        if (pd.deleteUser(p))
            ctx.status(204);
        else ctx.status(400);
    };


}
