package ui;

import model.Flight;
import model.ListOfFlights;


import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
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
    private Flight emptyFlight;
    private String newName;
    private String newNumber;
    private String newDestination;
    private Double newDuration;
    private String newDate;

    public AirlineApp() {
        runAirlineApp();
    }

    // MODIFIES: this
    // EFFECTS: takes in user's input and processes
    public void runAirlineApp() {
        boolean continueUser = true;
        String command = null;

        initializeAirlines();

        System.out.println("\n Welcome to Emma's Guide to Airlines");

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
            runAddFlight(scheduledFlights);
        } else if (command.equals("s")) {
            runSearchFlight(scheduledFlights);
        } else if (command.equals("r")) {
            runRemoveFlight(scheduledFlights);
        } else if (command.equals("v")) {
            runViewFlight();
        } else {
            System.out.println("The input is not a valid selection");
        }
    }

    // EFFECTS: initializes airline flights
    private void initializeAirlines() {
        jfkFlight0105 = new Flight("JFKFlight0105", "010JFY", "JFK", 6.5,
                "2022-01-05", "0900", 134);
        jfkFlight0111 = new Flight("JFKFlight0111", "111JEY", "JFK", 6.5,
                "2022-01-11", "1200", 138);
        icnFlight0310 = new Flight("ICNFlight0310", "031MTY", "ICN", 11,
                "2022-03-10", "1400", 188);
        yycFlight0428 = new Flight("YYCFlight0428", "042ATY", "YYC", 2,
                "2022-05-05", "1800", 110);
        hnlFlight0701 = new Flight("HNLFlight0701", "070JFY", "HNL", 6.5,
                "2022-07-01", "1530", 150);
        scheduledFlights = new ListOfFlights();
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
    }

    private void runAddFlight(ListOfFlights scheduledFlights) {
        System.out.println("What is the flight's name? \n");
        String newName = userInput.next();
        System.out.println("What is the flight number? \n");
        String newNumber = userInput.next();
        System.out.println("Where is this flight headed? \n");
        String newDestination = userInput.next();
        System.out.println("What is the estimated duration of this flight? (xx.xx) \n");
        Double newDuration = userInput.nextDouble();
        System.out.println("When is this flight's departure date? (yyyy-mm-dd) \n");
        String newDate = userInput.next();
        System.out.println("What time is this flight departing? (tttt) \n");
        String newTime = userInput.next();
        System.out.println("What is the plane's max capacity of seats? \n");
        Integer newMaxSeats = userInput.nextInt();
        Flight newFlight = new Flight(newName, newNumber, newDestination, newDuration,
                newDate, newTime, newMaxSeats);
        scheduledFlights.addFlight(newFlight);
        System.out.println("A new flight with name: " + newFlight.getName()
                + " was successfully created");

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

//    // MODIFIES: this
//    // EFFECTS: conducts addition of flight into list
//    private void runAddFlight(Flight f) {
//        runUserCreateFlight();
//        String selection = "";
//        System.out.println("Would you like to add this flight to the schedule?");
//        if (selection.equals("yes")) {
//            scheduledFlights.addFlight(f);
//            if (scheduledFlights.containsFlight(f.getFlightNum())) {
//                System.out.println("\n This flight already exists in the scheduled flights");
//            } else {
//                System.out.println("\n Successful addition of Flight number:" + f.getFlightNum()
//                        + " and Flight name:" + f.getName());
//                runRemoveFlight(f);
//            }
//        } else if (selection.equals("no")) {
//            System.out.println("What would you like to do instead?");;
//        }
//    }


    // MODIFIES: this
    // EFFECTS: conducts removal of flight from list
    private void runRemoveFlight(ListOfFlights scheduledFlights) {
        System.out.println("Which flight would you like to remove from the schedule? (index)");
        for (int s = 0; s < scheduledFlights.size(); s++) {
            Flight flight = scheduledFlights.get(s);
            System.out.println(flight.getName());
        }
        Integer i = userInput.nextInt();
        Flight newestFlight = scheduledFlights.get(i);
        if (scheduledFlights.containsFlight(newestFlight)) {
            scheduledFlights.removeFlight(newestFlight);
            System.out.println("Successful removal of flight: " + newestFlight.getName());
        } else {
            System.out.println("This flight does not exist in scheduled list");
        }
    }


    // EFFECTS: allows user to search flight given destination
    private void runSearchFlight(ListOfFlights scheduledFlights) {
        String pickedDestination = "";
        System.out.println("Where would you like to travel to?");
        pickedDestination = userInput.next();
        ListOfFlights intersectList = scheduledFlights.searchFlight(pickedDestination);
        int numFlights = intersectList.size();
        for (int s = 0; s < numFlights; s++) {
            Flight flight = intersectList.get(s);
            String flightName = flight.getName();
            String flightNum = flight.getFlightNum();
            System.out.println("The flights available for this destination: " + pickedDestination + " are "
                    + flightName);
        }
        if (!scheduledFlights.containsDestination(pickedDestination)) {
            System.out.println("Sorry, there are no available flights for this destination just yet!");
        }
    }

    // EFFECTS: allows user to view flight details given by flight name
    private void runViewFlight() {
        System.out.println("Which flight would you like to view the details of?");
        for (int s = 0; s < scheduledFlights.size(); s++) {
            Flight flight = scheduledFlights.get(s);
            System.out.println(flight.getName());
        }
        Integer i = userInput.nextInt();
        if (!scheduledFlights.isEmpty()) {
            Flight newestFlight = scheduledFlights.get(i);
//            String newFlightName = newestFlight.getName();
//            String newFlightNum = newestFlight.getFlightNum();
//            String newestDestination = newestFlight.getDestination();
//            String newestDuration = newestFlight.durationToString(newestFlight.getDuration());
//            String newestDate = newestFlight.getDate();
//            String newestTime = newestFlight.getTime();
//            String newestMaxSeats = newestFlight.seatToString(newestFlight.getMaxSeats());
            System.out.println("Here are the details of this flight: " + newestFlight);
//                            + newestFlight.getName()
//                    + newestFlight.getFlightNum()
//                    + newestFlight.getDestination() + newestFlight.durationToString(newestFlight.getDuration())
//                    + newestFlight.getDate() + newestFlight.getTime()
//                    + newestFlight.seatToString(newestFlight.getMaxSeats()));
        } else if (scheduledFlights.isEmpty()) {
            System.out.println("Sorry, invalid input. This scheduled list is empty!");
        }
    }


}
