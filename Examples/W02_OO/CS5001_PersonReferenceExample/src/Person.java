public class Person {
    public String name;
    public int age;
    public String hair_colour;
    private static int number_of_people;

    public Person(String name, int age, String hair_colour) {
        this.name = name;
        this.age = age;
        this.hair_colour = hair_colour;
        number_of_people = number_of_people + 1;
    }

    public void updateAge() {
        age++;
    }

    public void dyeHair(String hair_colour) {
        this.hair_colour = hair_colour;
    }

    public void printDetails() {
        System.out.println(
                "Person [name=" + name + ", age=" + age + ", hair_colour=" + hair_colour + "]");
    }

    public static int getNumberOfPeople() {
        return number_of_people;
    }
}
