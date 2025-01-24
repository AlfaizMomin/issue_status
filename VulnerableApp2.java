import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class VulnerableApp2 {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            try {
                // Example of a hardcoded sensitive value
                String secret = "hardcoded-password";
                File file = new File("output" + i + ".txt");

                if (file.createNewFile()) {
                    FileWriter writer = new FileWriter(file);
                    writer.write("This is a test file with secret: " + secret + "\n");
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace(); // Insufficient logging
            }
        }
    }
}
