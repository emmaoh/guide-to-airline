package ui;

import model.Flight;
import model.ListOfFlights;


import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;
import java.util.Scanner;

// This [AirlineApp] references code from [CPSC210] repository
// Link [https://github.students.cs.ubc.ca/CPSC210/TellerApp.git]

// Airline Schedule Guide Application
public class AirlineApp {
    private Flight jfkFlight0105;
    private Flight icnFlight0310;
    private Flight yycFlight0428;
    private Flight hnlFlight0701;
    private Flight jfkFlight0111;
    private Scanner userInput;
    private ListOfFlights scheduledFlights;

    public AirlineApp() {
        runAirlineApp();
    }

    // MODIFIES: this
    // EFFECTS: takes in user's input and processes
    public void runAirlineApp() {
        boolean continueUser = true;
        String command = null;

        initializeAirlines();

        while (continueUser) {
            displayAirlineMenu();
            command = userInput.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                continueUser = false;
            } else {
                processCommand(command);
            }


        }
        System.out.println("\n Have a good day!");

    }

    private void processCommand(String command) {
        if (command.equals("a")) {
            runAddFlight();
        } else if (command.equals("r")) {
            runRemoveFlight();
        } else if (command.equals("s")) {
            runSearchFlight();
        } else if (command.equals("v")) {
            runViewFlight();
        } else {
            System.out.println("The input is not a valid selection");
        }
    }

    // EFFECTS: initializes airline flights
    private void initializeAirlines() {
        jfkFlight0105 = new Flight("JFKFlight0105", "010JFY", "JFK", 6.5,
                LocalDate.of(2022, Month.JANUARY, 5), "0900", 134);
        jfkFlight0111 = new Flight("JFKFlight0111", "111JEY", "JFK", 6.5,
                LocalDate.of(2022, Month.JANUARY, 11), "1200", 138);
        icnFlight0310 = new Flight("ICNFlight0310", "031MTY", "ICN", 11,
                LocalDate.of(2022, Month.MARCH, 10), "1400", 188);
        yycFlight0428 = new Flight("YYCFlight0428", "042ATY", "YYC", 2,
                LocalDate.of(2022, Month.MAY, 5), "1800", 110);
        hnlFlight0701 = new Flight("HNLFlight0701", "070JFY", "HNL", 6.5,
                LocalDate.of(2022, Month.JULY, 1), "1530", 150);
        scheduledFlights = new ListOfFlights();
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");

    }

    // EFFECTS: displays options for user to choose from in the menu
    private void displayAirlineMenu() {
        System.out.println("\n Choose an option from the following:");
        System.out.println("\n a -> add flight");
        System.out.println("\n r -> remove flight");
        System.out.println("\n s -> search flight");
        System.out.println("\n v -> view flight");
        System.out.println("\n q -> quit");
    }

    // MODIFIES: this
    // EFFECTS: conducts addition of flight into list
    private void runAddFlight() {
        Flight selectedFlight = selectFlight();
        System.out.println("Please pick a flight to add");
        String flightNumber = userInput.nextLine();
        if (scheduledFlights.containsFlight(selectedFlight.getFlightNum())) {
            System.out.println("\n This flight already exists in the scheduled flights");
        } else {
            System.out.println("\n Successful addition of Flight number:" + flightNumber);
            scheduledFlights.addFlight(selectedFlight);
        }
    }


    // MODIFIES: this
    // EFFECTS: conducts removal of flight from list
    private void runRemoveFlight() {
        Flight selectedFlight = selectFlight();
        System.out.println("Which flight would you like to remove from the schedule?");
        String flightNumber = userInput.nextLine();

        if (!scheduledFlights.containsFlight(selectedFlight.getFlightNum())) {
            System.out.println("This flight does not exist in the scheduled flights");
        } else {
            System.out.println("Successful removal of flight number:" + flightNumber);
        }
    }


    // EFFECTS: allows user to search flight given destination
    private void runSearchFlight() {
        System.out.println("Where would you like to travel to?");
        String pickedDestination = userInput.nextLine();

        System.out.println("The destination," + pickedDestination + ",has these flights available:"
                + scheduledFlights.searchFlight(pickedDestination));
    }

    // EFFECTS: allows user to view flight details given by flight name
    private void runViewFlight() {
        System.out.println("Which flight would you like to view the details of?");
        String pickedFlightName = userInput.nextLine();

        System.out.println("Here are the details of this flight:" + pickedFlightName
                + scheduledFlights.viewFlight(pickedFlightName).getName()
                + scheduledFlights.viewFlight(pickedFlightName).getFlightNum()
                + scheduledFlights.viewFlight(pickedFlightName).getDestination()
                + scheduledFlights.viewFlight(pickedFlightName).durationToString(
                scheduledFlights.viewFlight(pickedFlightName).getDuration())
                + scheduledFlights.viewFlight(pickedFlightName).dateToString(
                scheduledFlights.viewFlight(pickedFlightName).getDate())
                + scheduledFlights.viewFlight(pickedFlightName).getTime()
                + scheduledFlights.viewFlight(pickedFlightName).seatToString(
                scheduledFlights.viewFlight(pickedFlightName).getMaxSeats()));
    }

    // EFFECTS: allows user to select which flight they would like to add/remove
    private Flight selectFlight() {
        String characterInput = "";
        return null;
    }
}
