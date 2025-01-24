import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VulnerableApp1 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // Vulnerable to SQL Injection
            String userInput = "' OR 1=1 --";
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "root", "password");
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    System.out.println("User: " + rs.getString("username"));
                }
            } catch (Exception e) {
                e.printStackTrace(); // Poor error handling
            }
        }
    }
}
