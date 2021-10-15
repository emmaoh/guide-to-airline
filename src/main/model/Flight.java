package model;

// Represents a flight with a given flight number, destination, duration, date, cost, price range and seats available
public class Flight {
    private String name;           // name of specific flight (plane)
    private String flightNum;      // the plane number
    private String destination;    // arrival (location) destination of flight
    private double duration;       // hours taken for arrival
    private String date;           // date of departure: yyyy-mm-dd
    private String time;           // time of departure: 24-hour time
    private String airport;        // name of airport for departure
    private int maxSeats;          // max number of passenger seats for flight


    // EFFECTS: flight has given name, flight number, destination, duration, date, time and maxSeats
    public Flight(String name, String flightNum, String destination, double duration,
                  String date, String time, int maxSeats) {
        this.name = name;
        this.flightNum = flightNum;
        this.destination = destination;
        this.duration = duration;
        this.date = date;
        this.time = time;
        this.maxSeats = maxSeats;
        airport = "YVR";
    }


    // EFFECTS: returns name of plane
    public String getName() {
        return name;
    }

    // EFFECTS: returns flight number
    public String getFlightNum() {
        return flightNum;
    }

    // EFFECTS: returns destination of flight
    public String getDestination() {
        return destination;
    }

    // EFFECTS: returns duration of flight
    public double getDuration() {
        return duration;
    }

    // EFFECTS: returns date of flight's departure
    public String getDate() {
        return date;
    }

    // EFFECTS: returns time of flight of departure
    public String getTime() {
        return time;
    }

    // EFFECTS: returns max amount of seats on the flight
    public int getMaxSeats() {
        return maxSeats;
    }


    // MODIFIES: this
    // EFFECTS: returns duration (double) to duration (String)
    public String durationToString(double duration) {
        String durationString = String.format("%1$,.2f", duration);
        return durationString;
    }


    // MODIFIES: this
    // EFFECTS: returns maxSeats (int) to maxSeats (string)
    public String seatToString(int maxSeats) {
        String seatString = String.format("%d", maxSeats);
        return seatString;
    }


}
