import java.util.ArrayList;

public class Main {

    public static void main(String argv[]) {
        ArrayList<Animal> list = new ArrayList<Animal>();

        list.add(new Dog());
        list.add(new Cat());
        list.add(new Cow());

        for (Animal animal : list) {
            System.out.println("The " + animal.whatAmI() + " says " + animal.says());
        }
    }
}
