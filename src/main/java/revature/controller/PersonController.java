package revature.controller.person;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import revature.dao.PersonDao;
import revature.model.Person;

import java.sql.Connection;

public class PersonController {


    public PersonController(Javalin app) {
        app.get("/persons", personHandler);
    }
    public Handler personHandler = ctx -> {
        PersonView view = new PersonView();
        ctx.html(view.render(new Person()));
    };
}
