public class Demo {

    public static void main(String[] args) {
        Vehicle[] vehicles = {
            new Car("J284 RFB"),
            new Car("G61 BRW"),
            new Plane("A112X"),
            new Hovercraft("DOV_CAL_2")
        };

        for (Vehicle v : vehicles) {
            v.printHowItMoves();
        }
    }

}
