// package HttpLogsQueries;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogExtractor {
    public static void main(String[] args) {
        System.out.println("LogExtractor");
        String filePath = "/var/log/apache2/headers.log"; // Change this to your log file path

        List<HeaderList> headerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String logEntry;
            while ((logEntry = br.readLine()) != null) {
                headerList.add(new HeaderList(logEntry));
                // List<String> headersList = extractHeaders(logEntry);
                
                // Print the headers for the current log entry
                // System.out.println("Headers for log entry:");
                // if(headersList != null) for (String header : headersList) {
                //     System.out.println(header);
                // }
                // System.out.println(); // Print a blank line for separation between log entries
            }
            new InsertHeaders().insertHeaders(headerList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(headerList);
    }

}
