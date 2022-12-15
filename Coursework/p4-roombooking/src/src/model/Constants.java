package model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import controller.UserController;

public class Constants {
        /*
         * Date time format : yyyy-mm-dd HH:MM
         */
        public static final String DATE_TIME_FORMAT_REGEX = "^[2]\\d{3}-[01][0-2]-([0-2]\\d|3[01])\\s([01][0-9]|2[0-3]):([0-5][0-9])$";
        public static final String DATE_FORMAT_REGEX = "^[2]\\d{3}-[01][0-2]-([0-2]\\d|3[01])$";
        public static final String TIME_FORMAT_REGEX = "^([01][0-9]|2[0-3]):([0-5][0-9])$";

        /*
         * The following regular expression was extracted from
         * https://www.baeldung.com/java-email-validation-regex
         * some email addresses that will be valid via this email validation technique
         * are:
         * username@domain.com
         * user.name@domain.com
         * user-name@domain.com
         * username@domain.co.in
         * user_name@domain.com
         * 
         * Here's a shortlist of some email addresses that will be invalid via this
         * email validation:
         * username.@domain.com
         * .user.name@domain.com
         * user-name@domain.com.
         * username@.com
         */

        public static final String VALID_EMAIL_REGEX = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        public static final int MINIMUM_BOOKING_TIME = 5;
        public static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm";

        public static final String CHOOSE_BUILDING = "Choose Building from the list of Building Names";
        public static final String NO_REGISTERED_BUILDING = "No building exists in university";
        public static final String NO_BUILDING_WITH_NAME = "No building exists with name ";

        public static final String ENTER_BUILDING_NAME = "Enter name of the building";
        public static final String ENTER_ROOM_NAME = "Enter name of the room";
        public static final String INVALID_OPTION = "Please enter a valid option";
        public static final String INVALID_BUILDING_NAME = "Invalid Building name";


        public enum PropertyToCheck {
                BUILDING,
                ROOM,
                PERSON,
                BOOKING,
        }

        public static final String USER_PROFILE_STORAGE_PATH = "p4-roombooking\\file.storage\\user.txt";
        public static final String BOOKING_PROFILE_STORAGE_PATH = "p4-roombooking\\file.storage\\booking.txt";
        public static final String BUILDING_PROFILE_STORAGE_PATH = "p4-roombooking\\file.storage\\building.txt";
        public static final String UNIVERSITY_PROFILE_STORAGE_PATH = "p4-roombooking\\file.storage\\university.txt";

        public enum Interaction {
                ADD,
                REMOVE,
                VIEW,
                VIEW_BOOKING_BY_ROOM,
                VIEW_BOOKING_BY_BUILDING,
                VIEW_BOOKING_BY_USER,
                VIEW_FREE_PERIOD,
                VIEW_BY_TIME,
                VIEW_BY_TIME_PERIOD,
                RETURN
        }

        public static final String RETURN_TO_MAIN = "Return To Main Menu.";
        // #region - User Operation
        public static final Map<Interaction, String> userAction = new LinkedHashMap<>();
        static {
                userAction.put(Interaction.ADD, "Add New User To The System");
                userAction.put(Interaction.REMOVE, "Remove Registered User From The System");
                userAction.put(Interaction.VIEW, "View All User In The System");
        }

        // #endregion

        // #region - Building Operation
        public static Map<Interaction, String> buildingAction = new LinkedHashMap<>();
        static {
                buildingAction.put(Interaction.ADD, "Add New Building To The University");
                buildingAction.put(Interaction.REMOVE, "Remove Building User From The University");
                buildingAction.put(Interaction.VIEW, "View All Buildings In The University");
        }
        // #endregion

        // #region - Room Operation
        public static Map<Interaction, String> roomAction = new LinkedHashMap<>();
        static {
                roomAction.put(Interaction.ADD, "Add New Room To A Building In The University");
                roomAction.put(Interaction.REMOVE, "Remove Room From Building In The University");
                roomAction.put(Interaction.VIEW, "View All Rooms In A Building");
        }

        // #endregion

        // #region - Booking Operation
        public static Map<Interaction, String> bookingAction = new LinkedHashMap<>();
        static {
                bookingAction.put(Interaction.ADD,
                                "Add new Room Booking");
                bookingAction.put(Interaction.REMOVE, "Remove Booking For Room In The University");
                bookingAction.put(Interaction.VIEW_BOOKING_BY_ROOM, "View all Booking for a Room in a Building");
                bookingAction.put(Interaction.VIEW_BOOKING_BY_BUILDING, "View all Booking for a Building in the University");
                bookingAction.put(Interaction.VIEW_BOOKING_BY_USER, "View all Booking for a User in the University.");
                bookingAction.put(Interaction.VIEW_FREE_PERIOD, "View all free period for a room.");
                bookingAction.put(Interaction.VIEW_BY_TIME, "View all Rooms available at the given time");
                bookingAction.put(Interaction.VIEW_BY_TIME_PERIOD, "View all Rooms available at the given time period");             
        }
        // #endregion

        // #region - GUI Option
        public static String CHOOSE_OPTION = "Choose from the following action to be performed:\n";
        // #endregion

}
