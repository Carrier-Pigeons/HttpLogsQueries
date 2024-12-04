import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InsertHeaders {
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/wordpress_logs";
    private static final String USER = System.getenv("SQL_USERNAME");
    private static final String PASSWORD = System.getenv("SQL_PASSWORD");

    public void insertHeaders(List<HeaderList> headerList) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establishing a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO header (header, uid, request, proxy, user_agent, headerOrder, headerValue) VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Example data to insert, you can replace this with your data source
            List<String[]> dataToInsert = new ArrayList<>();
            for(HeaderList header: headerList){
                
                for (HeaderList.Header h: header.headerList){
                    if(h.value.length() > 255) System.out.println(h.value.length() +": "+ h.headerName + ": " + h.value);
                    dataToInsert.add(new String[] {h.headerName, header.uid, header.request, "0", header.user_agent, Integer.toString(h.headerOrder), h.value});
                    
                    // Loop through the data and set the values
                    for (String[] data : dataToInsert) {
                        preparedStatement.setString(1, data[0]); // Set headername
                        preparedStatement.setString(2, data[1]); // Set value
                        preparedStatement.setString(3, data[2]);
                        preparedStatement.setString(4, data[3]);
                        preparedStatement.setString(5, data[4]);
                        preparedStatement.setString(6, data[5]);
                        preparedStatement.setString(7, data[6]);
                        preparedStatement.addBatch(); // Add to batch
                    }
                    dataToInsert = new ArrayList<>();
                }
            }

  

            // Execute the batch insert
            int[] affectedRecords = preparedStatement.executeBatch();

            // Optionally, check how many records were inserted
            System.out.println("Inserted records: " + affectedRecords.length);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}