public class Test {

    public static void main(String[] args) {

        Person angus = null;
        angus = new Person("Angus Macdonald", 24, "Brown");
        Person jon = new Person("Jon Lewis", 32, "Blonde");

        angus.printDetails();
        jon.printDetails();
        System.out.println();

        angus.updateAge();
        angus.printDetails();
        System.out.println();

        angus = jon;

        angus.dyeHair("Blue");
        System.out.println("jon.hair_colour = " + jon.hair_colour);
        System.out.println();

        System.out.print("angus.printDetails() gives the output: ");
        angus.printDetails();
        System.out.println();

        System.out.println("Number of Person objects created = " + Person.getNumberOfPeople());
    }
}
