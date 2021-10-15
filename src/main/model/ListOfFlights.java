package model;

import java.util.ArrayList;
import java.util.List;

public class ListOfFlights {
    private List<Flight> allFlights;

    // EFFECTS: list of flights is empty
    public ListOfFlights() {
        allFlights = new ArrayList<>();
    }

    // EFFECTS: adds a new flight into list of scheduled flights, if it doesn't exist in the list already
    public void addFlight(Flight f) {
        if (!containsFlight(f.getFlightNum())) {
            allFlights.add((f));
        }
    }

    // EFFECTS: removes flight from list of scheduled flights if it exists in the list already
    public void removeFlight(Flight f) {
        if (containsFlight(f.getFlightNum())) {
            allFlights.remove(f);
        }
    }

    // EFFECTS: returns true if flight exists in list, false otherwise. (checks by specific flight number)
    public boolean containsFlight(String flightNum) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getFlightNum().equals(flightNum)) {
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
        for (Flight nextFlight : allFlights) {
            if ((nextFlight.getDestination().equals(destination))) {
                destinationList.addFlight(nextFlight);
            }
        }
        return destinationList;
    }




    // EFFECTS: returns flight with given name, null otherwise
    public Flight viewFlight(String name) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getName().equals(name)) {
                return nextFlight;
            }
        }
        return null;
    }
}
