package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

public class DatabaseConnection {

    private static final String SERVER = "serversamsql.database.windows.net";
    private static final String DATABASE = "CLINICA_SAM";
    private static final String USER = "m.marcelino";
    private static final String PASSWORD = "M@rcelo200312";

    private static final String URL = String.format(
            "jdbc:sqlserver://%s:1433;databaseName=%s;user=%s;password=%s;encrypt=true;trustServerCertificate=true;loginTimeout=30;",
            SERVER, DATABASE, USER, PASSWORD
    );

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void main(String[] args) {
    	System.out.println("Conectado exitosamente");
    }
}

