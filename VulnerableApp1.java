import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class VulnerableApp1 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // New Issue: Unused variable
            String unusedVariable = "I am never used";

            // Fixed: Using Prepared Statements
            String userInput = "admin";
            String query = "SELECT * FROM users WHERE username = ?";
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "password");
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, userInput);
                ResultSet rs = pstmt.executeQuery();

                while (rs.next()) {
                    System.out.println("User: " + rs.getString("username"));
                }
            } catch (Exception e) {
                // New Issue: Vague error messages
                System.err.println("An error occurred.");
            }
        }
    }
}
