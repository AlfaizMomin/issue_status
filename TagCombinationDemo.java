/**
 * This file contains examples of security vulnerabilities, categorized
 * to demonstrate how different static analysis tags can be combined.
 * The tags used are: CWE, OWASP, SPRING, and WASP.
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

@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // (A AND B)
        // Tag: CWE
        // Tag: OWASP
        // Issue: Disabled Spring Security's CSRF protection.
        http.csrf().disable(); // CWE-352 (Cross-Site Request Forgery), OWASP A01:2021 (Broken Access Control)
    }
}

@RestController
@RequestMapping("/api")
public class VulnerableController {

    @GetMapping("/user/data")
    public String getUserData(@RequestParam String id, HttpServletRequest request) {
        
        // (A AND B)
        // Tag: CWE
        // Tag: OWASP
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
        // Tag: CWE
        // Issue: Hardcoded password in the code.
        String dbPassword = "very-secret-password"; // CWE-798 (Use of Hard-coded Credentials)
        return "Search results for: " + query; // (A AND B)
                                              // Tag: CWE
                                              // Tag: OWASP
                                              // Issue: Cross-Site Scripting (XSS) vulnerability.
    }

    @GetMapping("/profile")
    public String viewProfile(@RequestParam String username) {

        // (B only)
        // Tag: OWASP
        // Issue: Missing input validation for user-provided data.
        // This is a general OWASP issue (like A03:2021 - Injection) that may not
        // have a specific, direct CWE mapping in all cases.
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$"); // Incomplete validation example
        if (!pattern.matcher(username).matches()) {
            return "Invalid username format!";
        }
        return "Profile for user: " + username;
    }
    
    @PostMapping("/admin")
    public String adminCommand(@RequestBody String command) {

        // (C only)
        // Tag: SPRING
        // Issue: Spring-specific vulnerability, e.g., SpEL injection.
        // Spring-related issues often have their own specific tags.
        // This is a pattern that can lead to RCE.
        // ExpressionParser parser = new SpelExpressionParser();
        // Expression exp = parser.parseExpression(command);
        // exp.getValue();
        return "Command executed.";
    }

    @GetMapping("/insecure-redirect")
    public String insecureRedirect(@RequestParam String url) {

        // (C AND D)
        // Tag: SPRING
        // Tag: WASP
        // Issue: Unsafe redirect to a user-provided URL. This is both a general web
        // security issue (tagged with WASP) and a Spring-specific issue.
        return "redirect:" + url; // Example of a Spring-related issue
    }
}

@Controller
class AnotherVulnerableController {
    
    @GetMapping("/session-issue")
    public String brokenSessionManagement(HttpServletRequest request) {

        // (A only)
        // Tag: WASP
        // Issue: Insufficient Session Expiration.
        // This is a general Web Application Security Project (WASP) concern,
        // which can be a different classification system from CWE/OWASP.
        request.getSession().setMaxInactiveInterval(-1); 
        return "session-info";
    }
}
