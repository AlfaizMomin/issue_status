/**
 * This file contains examples of common security vulnerabilities
 * that would be flagged by a static analysis tool like Embold.
 * Each vulnerability is commented to show the tags that would be
 * associated with it (CWE and OWASP).
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // This issue is tagged with:
        // Tag: CWE
        // Tag: OWASP
        // (A AND B)
        // Issue: Disabled Spring Security's CSRF protection.
        http.csrf().disable(); // CWE-352 (Cross-Site Request Forgery), OWASP A01:2021 (Broken Access Control)
    }
}

@RestController
@RequestMapping("/api")
public class VulnerableController {

    @GetMapping("/user/data")
    public String getUserData(@RequestParam String id, HttpServletRequest request) {

        // This issue is tagged with:
        // Tag: CWE
        // Tag: OWASP
        // (A AND B)
        // Issue: SQL Injection vulnerability due to using unsanitized input in a SQL query.
        String sqlQuery = "SELECT * FROM users WHERE id = '" + id + "'";
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/testdb", "user", "password");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            // ... process results
            return "Query executed.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) {

        // This issue is tagged with:
        // Tag: CWE
        // (A) only
        // Issue: Hardcoded password in the code. This is a CWE issue,
        // but may not have a direct, explicit OWASP tag.
        String dbPassword = "very-secret-password"; // CWE-798 (Use of Hard-coded Credentials)
        
        // This part also has an issue, showing a combination of tags.
        // It's a Cross-Site Scripting (XSS) vulnerability.
        // This issue is tagged with:
        // Tag: CWE
        // Tag: OWASP
        // (A AND B)
        return "Search results for: " + query; // CWE-79 (Improper Neutralization of Input during Web Page Generation), OWASP A03:2021 (Injection)
    }

    public void brokenSessionManagement(HttpServletRequest request) {

        // This issue is tagged with:
        // Tag: CWE
        // Tag: OWASP
        // (A AND B)
        // Issue: Insufficient Session Expiration. The session cookie never expires.
        request.getSession().setMaxInactiveInterval(-1); // CWE-613 (Insufficient Session Expiration), OWASP A07:2021 (Identification and Authentication Failures)
    }
}
