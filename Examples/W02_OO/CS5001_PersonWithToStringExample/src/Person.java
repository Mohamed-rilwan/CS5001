public class Person {
    public String name;
    public int age;
    public String hair_colour;

    public Person spouse = null;

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

    public void setSpouse(Person spouse) {
        this.spouse = spouse;
    }

    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", hair_colour=" + hair_colour + "]";
    }
}
