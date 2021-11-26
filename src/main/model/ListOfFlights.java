package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a list of flights

public class ListOfFlights implements Writable {
    private String name;
    private List<Flight> allFlights;

    // EFFECTS: list of flights is empty
    public ListOfFlights(String name) {
        this.name = name;
        allFlights = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    // MODIFIES: this
    // EFFECTS: adds a new flight into list of scheduled flights, if it doesn't exist in the list already
    public void addFlight(Flight flight) {
        if (!containsFlight(flight)) {
            allFlights.add((flight));
            EventLog.getInstance().logEvent(new Event("Added new flight, " + flight.getName() + ": "
                    + flight + " to List of Flights"));
        }
    }

    // REQUIRES: list size > 0
    // EFFECTS: returns flight that is at a specified index in the list
    public Flight get(int index) {
        Flight pickedFlight = allFlights.get(index);
        return pickedFlight;
    }

    // EFFECTS: returns unmodifiable list of flights in this list of flights
    public List<Flight> getAllFlights() {
        return Collections.unmodifiableList(allFlights);
    }

    // MODIFIES: this
    // EFFECTS: removes flight from list of scheduled flights if it exists in the list already
    public void removeFlight(Flight flight) {
        if (containsFlight(flight)) {
            allFlights.remove(flight);
            EventLog.getInstance().logEvent(new Event("Removed flight, " + flight.getName() + ": "
                    + flight + " from List of Flights"));
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
        ListOfFlights destinationList = new ListOfFlights("New List Of Flights");
        for (Flight nextDestinationFlight : allFlights) {
            if ((nextDestinationFlight.getDestination().equals(destination))) {
                destinationList.addFlight(nextDestinationFlight);
                EventLog.getInstance().logEvent(new Event("Added flight: " + nextDestinationFlight.getName()
                        + ": " + nextDestinationFlight + " to a new list of the given destination"));
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

    //  create JSONObject of given fields of ListOfFlights to reference in object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("flights", flightsToJson());
        return json;
    }

    // converts flight in ListOfFlights to an array list in JSON format
    private JSONArray flightsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Flight f : allFlights) {
            jsonArray.put(f.toJson());
        }
        return jsonArray;
    }
}
