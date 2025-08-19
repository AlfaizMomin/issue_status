public class VulnerableApp3 {
    public static void main(String[] args) {
int a=1;
        for (int i = 0; i < 100; i++) {
            // Example of unoptimized and redundant code
            // Unnecessary concatenation
            String message = "This is a " + "long and " + "redundant string.";
        }
    }
}
