public class Test {

    public static void main(String[] args) {
        Person jeff = new Person("Jeff Jefferson", 27);
        Staff jon = new Staff("Jon Lewis", 32, 123456);
        Student angus = new Student("Angus Macdonald", 27, "Computer Science");

        System.out.println(jeff);
        System.out.println(jon);
        System.out.println(angus);
    }
}
