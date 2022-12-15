import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class ReverseStreams {

    /** 
     * Repeatedly get input from an input stream, reverse it and put it into an
     * output stream.
     */
    public static void handleStreams(InputStream in, OutputStream out) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out)));) {

            String line;
            while ((line = reader.readLine()) != null) {
                String reverse = new StringBuilder(line).reverse().toString();
                writer.println(reverse);
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("ERROR: problem with IO");
        }
    }
    
}
