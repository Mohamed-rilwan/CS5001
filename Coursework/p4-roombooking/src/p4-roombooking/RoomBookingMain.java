import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.University;
import service.UniversityService;
import view.commandline.RoomBookingCL;
import view.graphicalinterface.GUIMain;

public class RoomBookingMain {

    public static void main(String[] args) {

        boolean cliOpen = true;
        boolean guiOpen = true;

        University universityModel = new University();
        UniversityService universityService = new UniversityService(universityModel);
        final UserController userController = new UserController(universityModel,universityService);
        final BuildingController buildingController = new BuildingController(universityModel,universityService);
        final BookingController bookingController = new BookingController(universityModel,universityService);

        try {
            RoomBookingCL roomBookingCommandLine = new RoomBookingCL(universityModel, userController,
                    buildingController, bookingController,guiOpen,cliOpen);

            GUIMain guiMain = new GUIMain(universityModel, userController,
            buildingController, bookingController,guiOpen,cliOpen);
            guiMain.start();

            // Call Command line
            roomBookingCommandLine.start();

            System.out.println("Both exited");
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }
}
