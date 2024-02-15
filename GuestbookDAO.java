import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The {@code GuestbookDAO} class provides data access object functionalities for the guestbook entries.
 * It allows adding new entries to the guestbook and retrieving all existing entries from the database.
 */
public class GuestbookDAO {
    
    /**
     * Adds a new entry to the guestbook in the database.
     * 
     * @param entry The {@code GuestbookEntry} object containing the details of the new guestbook entry.
     * @throws SQLException If there is an error in executing the SQL query.
     * @throws ClassNotFoundException If the MySQL JDBC Driver class is not found.
     */
    public void addEntry(GuestbookEntry entry) throws SQLException, ClassNotFoundException {
        // SQL query to insert a new guestbook entry with placeholders for values
        String query = "INSERT INTO guestbook (name, email, website, comment) VALUES (?, ?, ?, ?)";

        // Try-with-resources to ensure closure of database resources
        try (Connection conn = DBConnection.getConnection(); // Get a connection to the database
             PreparedStatement stmt = conn.prepareStatement(query)) { // Prepare the SQL statement

            // Set the values for the placeholders in the SQL query
            stmt.setString(1, entry.getName());
            stmt.setString(2, entry.getEmail());
            stmt.setString(3, entry.getWebsite());
            stmt.setString(4, entry.getComment());

            stmt.executeUpdate(); // Execute the SQL query to insert the new entry
        }
    }

    /**
     * Retrieves all entries from the guestbook stored in the database.
     * 
     * @return A {@code List} of {@code GuestbookEntry} objects representing all guestbook entries.
     * @throws SQLException If there is an error in executing the SQL query.
     * @throws ClassNotFoundException If the MySQL JDBC Driver class is not found.
     */
    public List<GuestbookEntry> getAllEntries() throws SQLException, ClassNotFoundException {
        List<GuestbookEntry> entries = new ArrayList<>(); // List to hold retrieved guestbook entries
        // SQL query to select all entries from the guestbook
        String query = "SELECT * FROM guestbook";

        // Try-with-resources to ensure closure of database resources
        try (Connection conn = DBConnection.getConnection(); // Get a connection to the database
             Statement stmt = conn.createStatement(); // Create a statement object
             ResultSet rs = stmt.executeQuery(query)) { // Execute the query and get the result set

            // Iterate over the result set to extract guestbook entry details
            while (rs.next()) {
                GuestbookEntry entry = new GuestbookEntry();
                // Set the properties of the GuestbookEntry object from the result set
                entry.setId(rs.getInt("id"));
                entry.setName(rs.getString("name"));
                entry.setEmail(rs.getString("email"));
                entry.setWebsite(rs.getString("website"));
                entry.setComment(rs.getString("comment"));

                entries.add(entry); // Add the entry to the list of entries
            }
        }
        return entries; // Return the list of guestbook entries
    }
}
