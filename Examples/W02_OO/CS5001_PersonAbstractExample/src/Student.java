public class Student extends Person {

    private String degree_subject;

    public Student(String name, int age, String degree_subject) {
        super(name, age);
        this.degree_subject = degree_subject;
    }

    @Override
    public String toString() {
        return super.toString() + " and is a Student of " + degree_subject;
    }

    public String getResources() {
        return "Studres";
    }
}
