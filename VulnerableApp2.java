import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VulnerableApp2 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            // New Issue: Hardcoded file path
            String filePath = "/tmp/output" + i + ".txt";

            // Fixed: Removed hardcoded password
            String secret = System.getenv("APP_SECRET");
            if (secret == null) {
                secret = "default-password"; // New Issue: Fallback to a weak password
            }

            FileWriter writer = null;
            try {
                File file = new File(filePath);
                if (file.createNewFile()) {
                    writer = new FileWriter(file);
                    writer.write("This is a test file with secret: " + secret + "\n");
                }
            } catch (IOException e) {
                System.err.println("Error while creating or writing to the file.");
            } finally {
                // New Issue: Forgetting to close the writer
                // Uncommenting the following would fix the issue:
                // if (writer != null) writer.close();
            }
        }
    }
}
