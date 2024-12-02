import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertHeaders {
    // Database URL, username, and password
    private static final String URL = "jdbc:mysql://localhost:3306/wordpress_logs";
    private static final String USER = System.getenv("SQL_USERNAME");
    private static final String PASSWORD = System.getenv("SQL_PASSWORD");

    public static void main(String[] args) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        System.out.println(PASSWORD);

        try {
            // Establishing a connection
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // SQL query to insert data
            String sql = "INSERT INTO custom_header (headername, value) VALUES (?, ?)";
            preparedStatement = connection.prepareStatement(sql);

            // Set the values
            preparedStatement.setString(1, "Value1");
            preparedStatement.setString(2, "Value2");

            // Execute the insert operation
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted.");

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