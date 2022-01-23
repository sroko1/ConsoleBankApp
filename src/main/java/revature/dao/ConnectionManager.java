package revature.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection;
    private static String connectionString = "jdbc:postgresql://surus.db.elephantsql.com:5432/gmovxgzf",
            username = "gmovxgzf",
            password = "GoGWcr__m2ph4AD1xeZK1PESZIcGU-Bl";

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {

                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(connectionString, username, password);
            }

            return connection;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    // When the program is stopped, this will trigger and close the connection
    // You have to use the stop button in your IDE. Similar to finally clause in main()
    @Override
    public void finalize() {
        try {
            ConnectionManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
