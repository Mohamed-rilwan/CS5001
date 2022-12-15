public class Test {

    public static void main(String[] args) {

        Person jon = new Person("Jon Lewis", 32, "Blonde");
        jon.printDetails();

        jon.setName("Angus Macdonald");
        System.out.println(jon.getName());
    }
}
