import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogExtractor {
    public static void main(String[] args) {
        System.out.println("LogExtractor");
        String filePath = "/var/log/apache2/headers.log"; // Change this to your log file path

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String logEntry;
            while ((logEntry = br.readLine()) != null) {
                List<String> headersList = extractHeaders(logEntry);
                
                // Print the headers for the current log entry
                System.out.println("Headers for log entry:");
                if(headersList != null) for (String header : headersList) {
                    System.out.println(header);
                }
                System.out.println(); // Print a blank line for separation between log entries
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> extractHeaders(String logEntry) {
        List<String> headersList = new ArrayList<>();
        StringBuilder currentHeader = new StringBuilder();
        boolean inQuotes = false;
        // System.out.println("logEntry:" + logEntry);
        for (char c : logEntry.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes; // Toggle the inQuotes flag
            }
            if (c == '|' && !inQuotes) {
                headersList.add(currentHeader.toString().trim());
                currentHeader.setLength(0); // Clear the StringBuilder for the next header
            } else {
                currentHeader.append(c);
            }
        }
        // Add the last header if exists
        if (currentHeader.length() > 0) {
            headersList.add(currentHeader.toString().trim());
        }

        return headersList.size() > 2 ? headersList.subList(2, headersList.size()) : null;
    }
}
