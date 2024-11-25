package DB_Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:mysql://localhost:3306/o_shop";
        String user = "root";
        String password = "250922";
        return DriverManager.getConnection(url, user, password);

    }
}
