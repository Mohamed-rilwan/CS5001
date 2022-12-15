public abstract class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person with name " + name + " and age " + age + " has access to " + getResources();
    }

    public abstract String getResources();
}
