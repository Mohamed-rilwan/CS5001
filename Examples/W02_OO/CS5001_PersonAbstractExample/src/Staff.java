public class Staff extends Person {
    private int payroll_number;

    public Staff(String name, int age, int payroll_number) {
        super(name, age);
        this.payroll_number = payroll_number;
    }

    @Override
    public String toString() {
        return super.toString() + " and is Staff member with payroll_number " + payroll_number;
    }

    public String getResources() {
        return "Studres and Staffres";
    }
}
