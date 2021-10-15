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
    public void addFlight(Flight f1) {
        if (!containsFlight(f1)) {
            allFlights.add((f1));
        }
    }

    public Flight get(int index) {
        Flight f = allFlights.get(index);
        return f;
    }

    // EFFECTS: removes flight from list of scheduled flights if it exists in the list already
    public void removeFlight(Flight f) {
        if (containsFlight(f)) {
            allFlights.remove(f);
        }
    }

    // EFFECTS: returns true if flight exists in list, false otherwise. (checks by specific flight number)
    public boolean containsFlight(Flight f1) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.equals(f1)) {
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

    public boolean containsDestination(String dest) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getDestination().equals(dest)) {
                return true;
            }
        }
        return false;
    }



    public boolean isEmpty() {
        return allFlights.isEmpty();
    }







}
