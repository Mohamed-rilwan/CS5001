import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {

    public static void main(String[] args) throws FileNotFoundException {

        InputStream in;
        OutputStream out;
        if (args[0].equals("terminal")) {
            // Read and write on the terminal
            in = System.in;
            out = System.out;
        } else {
            // Read and write specified files
            in = new FileInputStream(args[0]);
            out = new FileOutputStream(args[1]);
        }

        ReverseStreams.handleStreams(in, out);

        in.close();
        out.close();
    }

}
