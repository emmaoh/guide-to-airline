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
        allFlights.remove(f);
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

    public Flight searchFlight(Flight f) {
        return null;
    }

    public Flight viewFlight(Flight f) {
        return null;
    }
}
