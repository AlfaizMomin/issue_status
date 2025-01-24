public class VulnerableApp3 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // Example of unoptimized and redundant code
            System.out.println("Line " + i + ": This is a redundant print statement.");
            // Unnecessary concatenation
            String message = "This is a " + "long and " + "redundant string.";
            System.out.println(message);
        }
    }
}
