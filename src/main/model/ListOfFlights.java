package model;

import java.util.ArrayList;
import java.util.List;

public class ListOfFlights {
    private List<Flight> allFlights;

    // EFFECTS: list of flights is empty
    public ListOfFlights() {
        allFlights = new ArrayList<>();
    }

    public void addFlight(Flight f) {
        if (!containsFlight(f.getFlightNum())) {
            allFlights.add((f));
        }
    }

    public void removeFlight(Flight f) {
        if (containsFlight(f.getFlightNum())) {
            allFlights.remove(f);
        }
    }

    public boolean containsFlight(String flightNum) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getFlightNum().equals(flightNum)) {
                return true;
            }
        }
        return false;
    }


    public int size() {
        return allFlights.size();
    }

    public String searchFlight(Flight f) {
        if (containsDestination(f.getDestination())) {
            return f.getName();
        }
        return f.getName();
    }

    public boolean containsDestination(String destination) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getDestination().equals(destination)) {
                return true;
            }
        }
        return false;
    }



    public boolean containsName(String name) {
        for (Flight nextFlight : allFlights) {
            if (nextFlight.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
