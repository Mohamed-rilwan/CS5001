public class Student extends Person {

    private String degreeSubject;

    public Student(String name, int age, String degree_subject) {
        super(name, age);
        this.degreeSubject = degree_subject;
    }

    public void printDetails() {
        super.printDetails();
        System.out.println("This person is a Student of " + degreeSubject);
    }
}
