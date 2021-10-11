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

    }

    public void removeFlight(Flight f) {

    }

    public boolean containsFlight(String flightNum) {
        return false;
    }


    public int size() {
        return 0;
    }

    public Flight searchFlight(Flight f) {
        return null;
    }

    public Flight viewFlight(Flight f) {
        return null;
    }
}
