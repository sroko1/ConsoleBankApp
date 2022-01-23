package revature.controller;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import revature.dao.AccountDao;
import revature.model.Account;
import revature.model.Person;

import java.sql.Connection;
import java.util.List;

public class AccountController {

    Javalin app;
    Connection connection;
    AccountDao accountDao;

    public AccountController(Javalin app, Connection connection) {
        this.app = app;
        this.connection = connection;
        accountDao = new AccountDao(connection);

        app.get("/accounts/{acc_id}", getAccountById);
        app.post("/accounts", createNewAccount);
        app.put("/accounts/{login}", modernizeAccount);
        app.delete("/accounts/{acc_id}", deleteAccount);
        app.get("/accounts", getAllAccounts);
    }

    public Handler getAllAccounts = ctx -> {
        List<Account> accountList = accountDao.getAllAccounts();
        if (accountList == null) {
            ctx.result("404 DB is empty");
            ctx.status(404);
        }
        assert accountList != null;
        ctx.json(accountList);
        // Status code 200 means "OK"
        ctx.status(200);
    };


    public Handler getAccountById = ctx -> {
        Account a = accountDao.getAccountById(Integer.parseInt(ctx.pathParam("acc_id")));
        ctx.json(a);

        // Status code 200 means "OK"
        ctx.status(200);
    };

    public Handler createNewAccount = ctx -> {
        // This line deserializes a JSON object from the body and creates a Java object out of it
        Account a = ctx.bodyAsClass(Account.class);
        accountDao.openAccount(a);

        // Status code 201 means "created"
        ctx.status(201);
    };


    public Handler modernizeAccount = ctx -> {
        Account ac = ctx.bodyAsClass(Account.class);
        if (accountDao.modernizeAccount(ac))
            // Status code 204 means "Successfully updated"
            ctx.status(204);
            // Status code 400 means "Error occurred"
        else ctx.status(400);
    };
    public Handler deleteAccount = ctx -> {
        Account a = ctx.bodyAsClass(Account.class);
        if (accountDao.deleteAccount(a))
            ctx.status(204);
        else ctx.status(400);
    };

}
