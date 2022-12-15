public class Test {

    public static void main(String[] args) {

        Person jon = new Person("Jon Lewis", 32, "Blonde");
        jon.printDetails();
        jon.updateAge();
        jon.printDetails();
        jon.updateAge(5);
        jon.printDetails();
    }
}
