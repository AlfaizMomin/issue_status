/**
 * This file contains examples of security vulnerabilities specifically
 * categorized with the "gammaJava" and "BrowserStack Standards" tags,
 * as requested.
 */
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.regex.Pattern;
import java.io.File;

@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // (A AND B)
        // Tag: gammaJava
        // Tag: BrowserStack Standards
        // Issue: Disabled Spring Security's CSRF protection.
        http.csrf().disable();
    }
}

@RestController
@RequestMapping("/api")
public class VulnerableController {

    @GetMapping("/user/data")
    public String getUserData(@RequestParam String id) {
        
        // (A AND B)
        // Tag: gammaJava
        // Tag: BrowserStack Standards
        // Issue: SQL Injection vulnerability due to unsanitized input.
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

        // (A only)
        // Tag: gammaJava
        // Issue: Hardcoded password in the code.
        String dbPassword = "very-secret-password";
        
        // (A AND B)
        // Tag: gammaJava
        // Tag: BrowserStack Standards
        // Issue: Cross-Site Scripting (XSS) vulnerability.
        return "Search results for: " + query;
    }

    @GetMapping("/profile")
    public String viewProfile(@RequestParam String username) {

        // (B only)
        // Tag: BrowserStack Standards
        // Issue: Missing input validation for user-provided data.
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        if (!pattern.matcher(username).matches()) {
            return "Invalid username format!";
        }
        return "Profile for user: " + username;
    }

    @GetMapping("/file-access")
    public String accessFile(@RequestParam String filename) {
        
        // (A AND B)
        // Tag: gammaJava
        // Tag: BrowserStack Standards
        // Issue: Path Traversal vulnerability.
        File file = new File("/var/www/data/" + filename);
        if (file.exists()) {
            return "File found.";
        }
        return "File not found.";
    }

    @GetMapping("/insecure-http")
    public String insecureHttp() {
        // (B only)
        // Tag: BrowserStack Standards
        // Issue: Use of an insecure protocol (HTTP) for sensitive data transfer.
        // This is a common standard for web application security.
        return "login form";
    }
}
