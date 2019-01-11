package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String SQLCONNECTION = "jdbc:sqlite:C:\\Users\\Fiorella\\AndroidStudioProjects\\GastoAppJava\\src\\GastoAppJava.db";

    public static Connection getConnection(){
        Connection conn = null;

        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(SQLCONNECTION);
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
