public abstract class Vehicle {

    private String regNum;

    public Vehicle(String regNum) {
        this.regNum = regNum;
    }

    public abstract void printHowItMoves();

}

public class Car extends Vehicle {
    @Override
    public void printHowItMoves() {
        System.out.println("Drive on the road!");
    }
}

public class Plane extends Vehicle {
    public void printHowItMoves() {
        System.out.println("Fly in the sky!");
    }
}
public class Hovercraft extends Vehicle {
    public void printHowItMoves() {
        System.out.println("Hover just above the ground!");
    }
}
