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

    public void updateAge(int years) {
        age = age + years;
    }

    public void dyeHair(String hair_colour) {
        this.hair_colour = hair_colour;
    }

    public void printDetails() {
        System.out.println(
                "Person [name=" + name + ", age=" + age + ", hair_colour=" + hair_colour + "]");
    }
}
