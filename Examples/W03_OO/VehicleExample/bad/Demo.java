public class Demo {

    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new Vehicle("car", "J284 RFB"),
            new Vehicle("car", "G61 BRW"),
            new Vehicle("plane", "A112X"),
            new Vehicle("hovercraft", "DOV_CAL_2")
        };

        for (Vehicle v : vehicles) {
            if (v.getType().equals("car")) {
                System.out.println("Drive on the road!");
            } else if (v.getType().equals("plane")) {
                System.out.println("Fly in the sky!");
            } else if (v.getType().equals("hovercraft")) {
                System.out.println("Hover just above the ground!");
            }
        }
    }

}
