package Data_Access.Connection;

import java.sql.*;

/**
 * Handles connection with the database
 *
 * @author Socaci Radu Andrei
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "radutare";

    /**
     * Loads the mysql jdbc driver. The driver is loaded only once (Singleton pattern)
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Calls create connection on the singleton factory
     *
     * @return database connection
     */
    public static Connection getConnection() {
        return SingletonHelper.singleInstance.createConnection();
    }

    /**
     * Closes a connection to the database
     *
     * @param connection database connection
     */
    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes a statement
     *
     * @param statement statement
     */
    public static void close(Statement statement) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Closes a result set
     *
     * @param resultSet result set
     */
    public static void close(ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a connection to the specified database
     *
     * @return database connection
     */
    private Connection createConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Implements the singleton design pattern. Using an inner class is more efficient
     */
    private static class SingletonHelper {
        private static final ConnectionFactory singleInstance = new ConnectionFactory();
    }
}
