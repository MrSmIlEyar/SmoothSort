import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenData {

    public static void main(String[] args) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"))) {
            Random rand = new Random();
            double n = 1000;
            double k = 1;
            double p;
            for (int i = 0; i < 91; i++) {
                p = n * k;
                for (int j = 0; j < p; j++) {
                    int num = rand.nextInt(100000);
                    int a = rand.nextInt(10);
                    if (a > 5) {
                        num *= -1;
                    }
                    writer.write(num + " ");
                }
                writer.write("\n");
                k += 0.1;
            }

        } catch (IOException e) {
            System.err.println("Error generating random number file: " + e.getMessage());
        }
    }

}
