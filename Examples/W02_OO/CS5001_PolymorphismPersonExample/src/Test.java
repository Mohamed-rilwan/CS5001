import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        // Can define new person variable and initialise with instances of subclass
        Person p1 = new Person("Jeff Jones", 19);
        Person p2 = new Staff("Jon Lewis", 32, 123456);
        Person p3 = new Student("Angus Macdonald", 27, "Computer Science");

        // When we call printDetails() on p1 and p2, the Person printDetails() method is called
        // When we call printDetails() on p3, the Student printDetails() method is called because
        // Person.printDetails is overridden in the Student class but not the Staff class.
        p1.printDetails();
        p2.printDetails();
        p3.printDetails();

        // We can even have a list of Person objects and treat the all the same - we know
        // every entry in the list must have a printDetails method, either the Person printDetails
        // method or one that was overridden in the object's class definition.
        ArrayList<Person> list = new ArrayList<Person>();
        list.add(p1);
        list.add(p2);
        list.add(p3);

        for (Person p : list) {
            p.printDetails();
        }
    }
}
