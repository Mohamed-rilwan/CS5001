public class Person {
    private String name;
    private int age;
    private String hair_colour;

    public Person(String name, int age, String hair_colour) {
        this.name = name;
        this.age = age;
        this.hair_colour = hair_colour;
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

    // All attributes above are private (encapsulated)
    // However, we may want to provide access to some attributes to other classes,
    // we need to define getter and setter methods for those attributes.
    // Here, access to the name attribute is give to other classes by providing public
    // accessor (getter and setter) methods

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
