public class TextUtils {

    /**
     * Checks if a string contains HTML tags and replaces them with the word "censur".
     * This method is intended to sanitize user input to prevent potential HTML injection attacks by removing any embedded HTML.
     * 
     * @param text The original string to be checked for HTML content.
     * @return A string where any HTML tags have been replaced with "censur".
     */
    public static String sanitizeHtml(String text) {
        // Use a simple regex pattern to identify HTML tags
        String htmlTagPattern = "<[^>]*>";
        
        // Replace all occurrences of HTML tags with the word "censur"
        return text.replaceAll(htmlTagPattern, "censur");
    }
}
