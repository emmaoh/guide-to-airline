package model;

import java.util.ArrayList;
import java.util.List;

public class ListOfFlights {
    private List<Flight> allFlights;

    // EFFECTS: list of flights is empty
    public ListOfFlights() {
        allFlights = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a new flight into list of scheduled flights, if it doesn't exist in the list already
    public void addFlight(Flight flight) {
        if (!containsFlight(flight)) {
            allFlights.add((flight));
        }
    }

    // REQUIRES: list size > 0
    // EFFECTS: returns flight that is at a specified index in the list
    public Flight get(int index) {
        Flight pickedFlight = allFlights.get(index);
        return pickedFlight;
    }

    // MODIFIES: this
    // EFFECTS: removes flight from list of scheduled flights if it exists in the list already
    public void removeFlight(Flight flight) {
        if (containsFlight(flight)) {
            allFlights.remove(flight);
        }
    }

    // EFFECTS: returns true if flight exists in list, false otherwise. (checks by specific flight number)
    public boolean containsFlight(Flight flight) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.equals(flight)) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: returns the number of flights in list of scheduled flights
    public int size() {
        return allFlights.size();
    }

    // EFFECTS: returns new list of flights that are available with same destination
    public ListOfFlights searchFlight(String destination) {
        ListOfFlights destinationList = new ListOfFlights();
        for (Flight nextDestinationFlight : allFlights) {
            if ((nextDestinationFlight.getDestination().equals(destination))) {
                destinationList.addFlight(nextDestinationFlight);
            }
        }
        return destinationList;
    }

    // EFFECTS: returns true if given destination exists in the list, false otherwise
    public boolean containsDestination(String destination) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }


    // EFFECTS: returns true if list is empty, false otherwise
    public boolean isEmpty() {
        return allFlights.isEmpty();
    }
}
