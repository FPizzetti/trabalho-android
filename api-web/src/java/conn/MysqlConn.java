package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlConn {

    private static Connection connection = null;

    public static Connection connect() {
        if (connection != null) {
            return connection;
        } else {
            try {
                String driver = "com.mysql.jdbc.Driver";
                String bd = "jdbc:mysql://127.0.0.1:3306/android";
                String usuario = "root";
                String senha = "root";
                Class.forName(driver);
                connection = DriverManager.getConnection(bd, usuario, senha);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
