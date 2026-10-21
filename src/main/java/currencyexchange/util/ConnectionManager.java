package currencyexchange.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {
    public static final String URL_KEY = "db.url";

    private ConnectionManager() { //Конструктор
    }

    public static Connection open() {
        try {
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(PropertiesUtil.get(URL_KEY));
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
