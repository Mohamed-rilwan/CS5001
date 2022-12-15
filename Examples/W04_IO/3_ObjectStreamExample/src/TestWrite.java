import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class TestWrite {

    public static void main(String[] args) {

        int i = 1234567;
        String s = "Hello World";
        Person p = new Person("Michael Torpey", 29);
        try {
            FileOutputStream fos = new FileOutputStream("mine.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeInt(i);
            oos.writeObject(s);
            oos.writeObject(p);
            oos.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
