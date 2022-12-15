import controller.BookingController;
import controller.BuildingController;
import controller.UserController;
import model.University;
import service.UniversityService;
import view.EventCloser;
import view.commandline.RoomBookingCL;
import view.graphicalinterface.GUIMain;

public class RoomBookingMain {

    public static void main(String[] args) {

        

        University universityModel = new University();
        UniversityService universityService = new UniversityService(universityModel);
        final UserController userController = new UserController(universityModel,universityService);
        final BuildingController buildingController = new BuildingController(universityModel,universityService);
        final BookingController bookingController = new BookingController(universityModel,universityService);
        EventCloser eventCloser = new EventCloser(); 

        try {
            RoomBookingCL roomBookingCommandLine = new RoomBookingCL(universityModel, userController,
                    buildingController, bookingController,eventCloser);

            GUIMain guiMain = new GUIMain(universityModel, userController,
            buildingController, bookingController,eventCloser);
            guiMain.start();

            // Call Command line
            roomBookingCommandLine.start();

            System.out.println("Both exited");
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }

    }
}
