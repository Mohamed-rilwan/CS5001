public class Dog implements Animal {

    @Override
    public String says() {
        return "woof";
    }

    @Override
    public String whatAmI() {
        return "dog";
    }
}
