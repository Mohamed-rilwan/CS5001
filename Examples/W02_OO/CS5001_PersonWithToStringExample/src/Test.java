public class Test {

    /** @param args */
    public static void main(String[] args) {

        Person p = new Person("Mr Man", 20, "Brown");

        Person y = new Person("Mrs Woman", 20, "Blonde");

        p.setSpouse(y);
        y.setSpouse(p);

        System.out.println("p is " + p.toString());
        System.out.println("p's wife is: " + y);
    }
}
