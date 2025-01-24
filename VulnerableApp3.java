public class VulnerableApp3 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                // New Issue: Unnecessary thread sleep
                Thread.sleep(100);

                // Fixed: Use StringBuilder for concatenation
                StringBuilder message = new StringBuilder();
                message.append("Line ").append(i).append(": This is optimized string concatenation.");
                System.out.println(message.toString());
            } catch (InterruptedException e) {
                // New Issue: Generic exception handling
                System.err.println("Something went wrong. Ignoring...");
            }
        }
    }
}
