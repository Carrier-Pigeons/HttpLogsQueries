import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogExtractor {
    public static void main(String[] args) {
        System.out.println("LogExtractor");
        String filePath = "/var/log/apache2/headers.log"; // Change this to your log file path

        LogExtractor l = new LogExtractor(); // must instantiate LogExtractor in order to access non-static classes.
        List<HeaderList> headerList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String logEntry;
            while ((logEntry = br.readLine()) != null) {
                headerList.add(l.new HeaderList(logEntry));
                // List<String> headersList = extractHeaders(logEntry);
                
                // Print the headers for the current log entry
                // System.out.println("Headers for log entry:");
                // if(headersList != null) for (String header : headersList) {
                //     System.out.println(header);
                // }
                // System.out.println(); // Print a blank line for separation between log entries
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(headerList);
    }

    class HeaderList {
        public String uid;
        public String request;
        public boolean proxy;
        public String user_agent;
        public List<Header> headerList = new ArrayList<>();

        public HeaderList(String logEntry){
            List<String> headers = extractHeaders(logEntry);
            if(headers.size() < 2) return;
            uid = headers.get(0).substring(1); // we do not need the + at the start of this.
            request = headers.get(1);
            user_agent = extractHeaderNameValue(headers.stream()
            .filter(str -> str.startsWith("User-Agent"))
            .findFirst()
            .orElse(null), 2).value;
            List<String>actualHeaders = headers.subList(2, headers.size());
         
            for(int i = 0; i < actualHeaders.size(); ++i){
                Header h = extractHeaderNameValue(actualHeaders.get(i), i);
                if(h.headerName != "User-Agent") headerList.add(h);
            }
            System.out.println(this.user_agent);
        }

        private List<String> extractHeaders(String logEntry) {
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
    
            return headersList;
            // return headersList.size() > 2 ? headersList.subList(2, headersList.size()) : null;
        }

        private Header extractHeaderNameValue(String input, int headerOrder){
            int colonIndex = input.indexOf(':');
            String headerName = input.substring(0, colonIndex);
            String value = input.substring(colonIndex + 1);
            return new Header(headerName, value, headerOrder);
        }
 
    }

    class Header{
        String headerName;
        String value;
        int headerOrder;

        Header(String headerName, String value, int headerOrder){
            this.headerName = headerName;
            this.value = value;
            this.headerOrder = headerOrder;
        }


    }

    private static void handleHeaders(String logEntry){

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

        return headersList;
        // return headersList.size() > 2 ? headersList.subList(2, headersList.size()) : null;
    }
}
