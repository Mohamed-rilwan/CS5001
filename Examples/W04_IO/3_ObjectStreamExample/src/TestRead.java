import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class TestRead {

    public static void main(String[] args) {

        try {
            FileInputStream fis = new FileInputStream("mine.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            int i = ois.readInt();
            String s = (String) ois.readObject();
            Person p = (Person) ois.readObject();
            System.out.println("i=" + i + ", s=" + s + ", p=" + p);
            ois.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException cne) {
            System.err.println(cne.getMessage());
        }
    }
}
