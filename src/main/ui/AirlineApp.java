package ui;
// This [AirlineApp] references code from [CPSC210] repository
// Link [https://github.students.cs.ubc.ca/CPSC210/TellerApp.git]

// Airline Schedule Guide Application
public class AirlineApp {


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

        }

    }

    // EFFECTS: initializes airline status'
    private void initializeAirlines() {

    }

    // EFFECTS: displays options for user to choose from in the menu
    private void displayAirlineMenu() {
        System.out.println("\n a -> add flight");
        System.out.println("\n r -> remove flight");
        System.out.println("\n s -> search flight");
        System.out.println("\n v -> view flight");
    }

    // MODIFIES: this
    // EFFECTS: conducts addition of flight into list
    private void runAddFlight() {}

    // MODIFIES: this
    // EFFECTS: conducts removal of flight from list
    private void runRemoveFlight(){}

    // EFFECTS: allows user to search flight given destination
    private void runSearchFlight(){}

    // EFFECTS: allows user to view flight details given by flight name
    private void runViewFlight(){}
}
