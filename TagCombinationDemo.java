package com.example.demo;

public class TagCombinationDemo {

    // CWE only
    public void example1(String input) {
        System.out.println(input.toString()); // CWE
    }

    // OWASP only
    public void example2(String input) {
        if ("password".equals(input)) { } // OWASP
    }

    // MISRA only
    public void example3() {
        int x = 0; x++; // MISRA
    }

    // WASC only (improper input handling)
    public void example4(String input) {
        System.out.println(input); // WASC
    }

    // CWE + OWASP
    public void example5(String input) {
        String sql = "SELECT * FROM users WHERE name = '" + input + "'"; // CWE;OWASP
    }

    // CWE + MISRA
    public void example6() {
        String s = null; System.out.println(s); // CWE;MISRA
    }

    // OWASP + WASC
    public void example7(String input) {
        System.out.println(input.toLowerCase()); // OWASP;WASC
    }

    // MISRA + WASC
    public void example8(String input) {
        System.out.println(input); // MISRA;WASC
    }

    // Two combinations of three tags
    // CWE + OWASP + MISRA
    public void example9(String input) {
        String sql = "DELETE FROM account WHERE id=" + input; // CWE;OWASP;MISRA
    }

    // OWASP + MISRA + WASC
    public void example10(String input) {
        System.out.println(input); // OWASP;MISRA;WASC
    }

    // All four tags
    public void exampleAll(String input) {
        System.out.println(input);  // CWE;OWASP;MISRA;WASC
    }
}
