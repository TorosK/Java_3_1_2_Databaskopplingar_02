import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code GuestbookApp} class serves as the main entry point for the guestbook application.
 * It provides a command-line interface for users to add new entries to the guestbook and view existing entries.
 */
public class GuestbookApp {

    /**
     * Main method which serves as the entry point of the application.
     * It presents a menu to the user and processes user input to perform various actions.
     * 
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GuestbookDAO guestbookDAO = new GuestbookDAO();

        while (true) {
            // Display the main menu to the user
            System.out.println("\nGuestbook Application");
            System.out.println("1. Add New Entry");
            System.out.println("2. View All Entries");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after the integer input

            // Process the user's menu selection
            switch (option) {
                case 1:
                    addEntry(scanner, guestbookDAO); // Add a new guestbook entry
                    break;
                case 2:
                    viewEntries(guestbookDAO); // Display all guestbook entries
                    break;
                case 3:
                    System.out.println("Exiting..."); // Exit the application
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again."); // Handle invalid menu option
            }
        }
    }

    /**
     * Prompts the user for entry details, creates a new {@code GuestbookEntry}, and adds it to the guestbook.
     * 
     * @param scanner      The {@code Scanner} object for reading user input.
     * @param guestbookDAO The {@code GuestbookDAO} object for data access operations.
     */
    private static void addEntry(Scanner scanner, GuestbookDAO guestbookDAO) {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Website (optional): ");
        String website = scanner.nextLine();

        System.out.print("Enter Comment: ");
        String comment = scanner.nextLine();

        // Create a new guestbook entry with the provided details
        GuestbookEntry entry = new GuestbookEntry(name, email, website, comment);

        try {
            // Attempt to add the new entry to the database
            guestbookDAO.addEntry(entry);
            System.out.println("Entry added successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions that may occur during the database operation
            System.out.println("Error adding entry: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all entries from the guestbook.
     * 
     * @param guestbookDAO The {@code GuestbookDAO} object for data access operations.
     */
    private static void viewEntries(GuestbookDAO guestbookDAO) {
        try {
            // Retrieve all entries from the database
            List<GuestbookEntry> entries = guestbookDAO.getAllEntries();

            if (entries.isEmpty()) {
                System.out.println("No entries found."); // Inform the user if no entries exist
            } else {
                // Iterate through the entries and display their details
                for (GuestbookEntry entry : entries) {
                    System.out.println("\n---------------------------------");
                    System.out.println("ID: " + entry.getId());
                    System.out.println("Name: " + entry.getName());
                    System.out.println("Email: " + entry.getEmail());
                    System.out.println("Website: " + entry.getWebsite());
                    System.out.println("Comment: " + entry.getComment());
                    System.out.println("---------------------------------\n");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            // Handle exceptions that may occur during the database operation
            System.out.println("Error fetching entries: " + e.getMessage());
        }
    }
}
