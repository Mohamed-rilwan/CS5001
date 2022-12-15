// package view;
// import controller.BookingController;
// import controller.BuildingController;
// import controller.UserController;
// import model.University;
// import view.commandline.RoomBookingCL;
// import view.graphicalinterface.GUIMain;

// public class Intializer{
   
//     public Intializer(){
//      this.university = new University();
//     }

//     public University university;

//     public University getUniversity() {
//         return university;
//     }

//     public void setUniversity(University university) {
//         this.university = university;
//     }

//     public Intializer newIntializer(University university)
//     {
//         Intializer i=new Intializer();
//          i.setUniversity(university);
//          return i;
//     }

//     /**
//      * @param university initial model requreed to start the function
//      */
//     public void initializeControllers(){
//         UserController userController = new UserController(university);
//         BuildingController buildingController = new BuildingController(university);
//         BookingController bookingController = new BookingController(university);

//         try {
//             RoomBookingCL roomBookingCommandLine = new RoomBookingCL(university, userController,
//                     buildingController, bookingController);

//             GUIMain guiMain = new GUIMain(university, userController,
//             buildingController, bookingController);
//             guiMain.start();

//             // Call Command line
//             roomBookingCommandLine.run();
//             System.out.println("Both exited");
//         } catch (Exception ex) {
//             System.out.println(ex.getLocalizedMessage());
//         }        
//     }
// }
