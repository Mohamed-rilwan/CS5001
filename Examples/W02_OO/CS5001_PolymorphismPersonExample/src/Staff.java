public class Staff extends Person {
    private int payrollNumber;

    public Staff(String name, int age, int payroll_number) {
        super(name, age);
        this.payrollNumber = payroll_number;
    }

    public int getPayrollNumber() {
        return payrollNumber;
    }
}
