package ui;

import model.Flight;
import model.ListOfFlights;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

// This [AirlineApp] references code from [CPSC210] repository
// Link [https://github.students.cs.ubc.ca/CPSC210/TellerApp.git]

// Airline Schedule Guide Application
public class AirlineApp {
    private static final String JSON_STORE = "./data/lof.json";
    private Scanner userInput;
    private ListOfFlights scheduledFlights;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: initiates airline app, FileNotFoundException thrown if file does not exist
    public AirlineApp() throws FileNotFoundException {
        userInput = new Scanner(System.in);
        scheduledFlights = new ListOfFlights("Emma's scheduled flights");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runAirlineApp();
    }

    // MODIFIES: this
    // EFFECTS: takes in user's input and processes
    public void runAirlineApp() {
        boolean continueUser = true;
        String command = null;
        userInput = new Scanner(System.in);
        initializeAirlines();

        System.out.println("\n Welcome to Emma's Travel Guide to Airlines!");

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
        System.out.println("\n Thank you, have a good day!");
    }

    // MODIFIES: this
    // EFFECTS: processes the user command inputted
    private void processCommand(String command) {
        if (command.equals("a")) {
            runAddFlight(scheduledFlights);
        } else if (command.equals("s")) {
            runSearchFlight(scheduledFlights);
        } else if (command.equals("r")) {
            runRemoveFlight(scheduledFlights);
        } else if (command.equals("v")) {
            runViewFlight(scheduledFlights);
        } else if (command.equals("p")) {
            printFlights();
        } else if (command.equals("f")) {
            saveListOfFlights();
        } else if (command.equals("l")) {
            loadListOfFlights();
        } else {
            System.out.println("This input is not a valid selection");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes airline flights
    private void initializeAirlines() {
        scheduledFlights = new ListOfFlights("New Scheduled Flights");
        userInput = new Scanner(System.in);
        userInput.useDelimiter("\n");
    }

    // MODIFIES: this
    // EFFECTS: creates a new flight and adds it into initialized list of flights
    private void runAddFlight(ListOfFlights scheduledFlights) {
        System.out.println("What is the flight's name? \n");
        String newName = userInput.next();
        System.out.println("What is the flight number? \n");
        String newNumber = userInput.next();
        System.out.println("Where is this flight headed? \n");
        String newDestination = userInput.next();
        System.out.println("What is the estimated duration of this flight? (double) \n");
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
                + " was successfully created!");
    }

    // EFFECTS: displays options for user to choose from in the menu
    private void displayAirlineMenu() {
        System.out.println("\n Choose an option from the following:");
        System.out.println("\n a -> add flight");
        System.out.println("\n r -> remove flight");
        System.out.println("\n s -> search flight");
        System.out.println("\n v -> view flight");
        System.out.println("\n p -> print flights");
        System.out.println("\n f -> save flights to file");
        System.out.println("\n l -> load flights from file");
        System.out.println("\n q -> quit");
    }

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
            System.out.println("Successful removal of flight with name: " + newestFlight.getName()
                    + " from scheduled flights");
        } else {
            System.out.println("Sorry, this flight does not exist in scheduled list!");
        }
    }

    // MODIFIES: this
    // EFFECTS: allows user to search flight given its destination
    private void runSearchFlight(ListOfFlights scheduledFlights) {
        String pickedDestination = "";
        System.out.println("Where would you like to travel to?");
        pickedDestination = userInput.next();
        ListOfFlights intersectList = scheduledFlights.searchFlight(pickedDestination);
        int numFlights = intersectList.size();
        for (int s = 0; s < numFlights; s++) {
            Flight flight = intersectList.get(s);
            String flightName = flight.getName();
            System.out.println("The flights available for this destination: " + pickedDestination + " are "
                    + flightName);
        }
        if (!scheduledFlights.containsDestination(pickedDestination)) {
            System.out.println("Sorry, there are no available flights for this destination just yet!");
        }
    }

    // EFFECTS: allows user to view flight details given by representation of index in list
    public void runViewFlight(ListOfFlights scheduledFlights) {
        System.out.println("Which flight would you like to view the details of?");
        for (int s = 0; s < scheduledFlights.size(); s++) {
            Flight flight = scheduledFlights.get(s);
            System.out.println(flight.getName());
        }
        Integer i = userInput.nextInt();
        Flight newestFlight = scheduledFlights.get(i);

        if (!scheduledFlights.isEmpty()) {
            System.out.println("Here are the details of this flight for: " + newestFlight.getName());
            System.out.println("The Flight Number: " + newestFlight.getFlightNum());
            System.out.println("The Destination: " + newestFlight.getDestination());
            System.out.println("Flight Duration: " + newestFlight.durationToString(newestFlight.getDuration())
                    + " hours");
            System.out.println("Flight Departure Date: " + newestFlight.getDate());
            System.out.println("Flight Departure Time: " + newestFlight.getTime() + " hours");
            System.out.println("Flight Maximum Seats: " + newestFlight.seatToString(newestFlight.getMaxSeats()));
        } else {
            System.out.println("Sorry, invalid input. This scheduled list is empty!");
        }
    }

    // EFFECTS: prints all the flight names in the list of scheduled flights to console
    public void printFlights() {
        List<Flight> allFlights = scheduledFlights.getAllFlights();

        for (Flight f : allFlights) {
            System.out.println(f.getName() + ": " + f.getFlightNum() + "\t" + f.getDestination()
                    + "\t" + f.durationToString(f.getDuration()) + "\t" + f.getDate() + "\t" + f.getTime()
                    + "\t" + f.seatToString(f.getMaxSeats()));
        }
    }

    // EFFECTS: saves the list of scheduled flights to a file
    public void saveListOfFlights() {
        try {
            jsonWriter.open();
            jsonWriter.write(scheduledFlights);
            jsonWriter.close();
            System.out.println("Saved " + scheduledFlights.getName() + " to" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to the file " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the list of scheduled flights from the file
    public void loadListOfFlights() {
        try {
            scheduledFlights = jsonReader.read();
            System.out.println("Loaded " + scheduledFlights.getName() + " from" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from the file: " + JSON_STORE);
        }
    }
}
