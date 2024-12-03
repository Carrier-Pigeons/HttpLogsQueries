// package HttpLogsQueries;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeaderList {
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

        String userAgent = "User-Agent";
        List<String> excludeHeaders = new ArrayList<>(Arrays.asList(userAgent, "Cookie"));
        
        // Since the user agent is not stored in the headerlist, we cannot extract
        // it from the headerlist.
        // The final headerlist grabs the subarray from two onwards thus
        // skips the user agent.
        user_agent = extractHeaderNameValue(headers.stream()
        .filter(str -> str.startsWith(userAgent))
        .findFirst()
        .orElse(null), 2).value;
        List<String>actualHeaders = headers.subList(2, headers.size());
     
        for(int i = 0; i < actualHeaders.size(); ++i){
            Header h = extractHeaderNameValue(actualHeaders.get(i), i+1); //The headerOrder starts at 1 not zero.
            if(!excludeHeaders.contains(h.headerName)) headerList.add(h);
        }

        proxy = null != headerList.stream().filter(h -> h.headerName == "Evilginx").findFirst();
    }

    private Header extractHeaderNameValue(String input, int headerOrder){
        int colonIndex = input.indexOf(':');
        String headerName = input.substring(0, colonIndex);
        String value = input.substring(colonIndex + 1);
        return new Header(headerName, value, headerOrder);
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

}