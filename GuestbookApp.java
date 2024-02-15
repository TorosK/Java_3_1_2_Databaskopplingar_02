import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * The {@code GuestbookApp} class serves as the main entry point for the guestbook application.
 * It provides a command-line interface for users to add new entries to the guestbook and view existing entries.
 * This application sanitizes user inputs to remove any HTML tags, replacing them with the word "censur" to prevent HTML injection.
 */
public class GuestbookApp {

    /**
     * Main method serving as the entry point of the application.
     * Presents a menu to the user and processes input to perform actions such as adding or viewing guestbook entries.
     * 
     * @param args Command-line arguments passed to the application (not used).
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GuestbookDAO guestbookDAO = new GuestbookDAO();

        while (true) {
            System.out.println("\nGuestbook Application");
            System.out.println("1. Add New Entry");
            System.out.println("2. View All Entries");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after integer input to avoid input skipping.

            switch (option) {
                case 1:
                    addEntry(scanner, guestbookDAO); // Adds a sanitized guestbook entry.
                    break;
                case 2:
                    viewEntries(guestbookDAO); // Displays all guestbook entries.
                    break;
                case 3:
                    System.out.println("Exiting..."); // Exits the application.
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again."); // Handles invalid menu options.
            }
        }
    }

    /**
     * Prompts the user for entry details, sanitizes the input to remove HTML, creates a new {@code GuestbookEntry}, and adds it to the guestbook.
     * 
     * @param scanner      The {@code Scanner} object for reading user input.
     * @param guestbookDAO The {@code GuestbookDAO} object for data access operations.
     */
    private static void addEntry(Scanner scanner, GuestbookDAO guestbookDAO) {
        System.out.print("Enter Name: ");
        String name = TextUtils.sanitizeHtml(scanner.nextLine());

        System.out.print("Enter Email: ");
        String email = TextUtils.sanitizeHtml(scanner.nextLine());

        System.out.print("Enter Website (optional): ");
        String website = TextUtils.sanitizeHtml(scanner.nextLine());

        System.out.print("Enter Comment: ");
        String comment = TextUtils.sanitizeHtml(scanner.nextLine());

        GuestbookEntry entry = new GuestbookEntry(name, email, website, comment);

        try {
            guestbookDAO.addEntry(entry);
            System.out.println("Entry added successfully.");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error adding entry: " + e.getMessage());
        }
    }

    /**
     * Retrieves and displays all entries from the guestbook.
     * Each entry is presented with details such as ID, name, email, website, and the sanitized comment.
     * 
     * @param guestbookDAO The {@code GuestbookDAO} object for data access operations.
     */
    private static void viewEntries(GuestbookDAO guestbookDAO) {
        try {
            List<GuestbookEntry> entries = guestbookDAO.getAllEntries();

            if (entries.isEmpty()) {
                System.out.println("No entries found.");
            } else {
                for (GuestbookEntry entry : entries) {
                    System.out.println("\n---------------------------------");
                    System.out.println("ID: " + entry.getId());
                    System.out.println("Name: " + entry.getName());
                    System.out.println("Email: " + entry.getEmail());
                    System.out.println("Website: " + entry.getWebsite());
                    System.out.println("Comment: " + entry.getComment()); // Note: Comments are sanitized before storage.
                    System.out.println("---------------------------------\n");
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error fetching entries: " + e.getMessage());
        }
    }
}
