
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private final String database_name = "hotel_management_system";
    private final String connection_url = "jdbc:mysql://localhost:3306/" + database_name;
    private final String user = "root";
    private final String password = "rohan1234";
    private Connection connection;

    public Connection connect() {
        try {
            this.connection = DriverManager.getConnection(connection_url, user, password);
            return this.connection;
        } catch (SQLException exception) {
            System.out.println("failed to connected to the datatase " + exception.getMessage());
            return null;
        }
    }

    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException exception) {
            System.out.println("failed to close the connection to the database " + exception.getMessage());
        }
    }

}
