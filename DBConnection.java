import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

/**
 * The {@code DBConnection} class provides a method to establish a connection to a MySQL database.
 * It uses the MySQL JDBC driver to establish a database connection.
 * This approach enhances security by avoiding hardcoded credentials and allows for easier configuration changes
 * by loading database connection details from a properties file.
 */
public class DBConnection {

    /**
     * Establishes and returns a connection to the database by reading database connection details from a properties file.
     * The properties file must include keys for DB_URL, DB_USERNAME, and DB_PASSWORD.
     *
     * @return A {@code Connection} object to the specified database.
     * @throws ClassNotFoundException if the JDBC driver class is not found.
     * @throws SQLException if a database access error occurs, the URL is {@code null}, or the properties file cannot be loaded.
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        // Load and register the JDBC driver for MySQL
        Class.forName("com.mysql.jdbc.Driver");

        // Load database properties from a properties file
        Properties prop = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new SQLException("Unable to load 'config.properties'. Ensure the file is in the classpath.");
            }
            prop.load(input); // Load properties from the input stream
        } catch (Exception ex) {
            throw new SQLException("Error loading 'config.properties': ", ex);
        }

        // Retrieve database credentials from the properties object (for safety reasons)
        String url = prop.getProperty("DB_URL"); // Database URL in the format of jdbc:mysql://{HOST}:{PORT}/{DATABASE_NAME}
        String username = prop.getProperty("DB_USERNAME"); // Username to connect to the database
        String password = prop.getProperty("DB_PASSWORD"); // Password for the database user

        // Establish and return a connection to the database using the retrieved credentials
        return DriverManager.getConnection(url, username, password);
    }
}
