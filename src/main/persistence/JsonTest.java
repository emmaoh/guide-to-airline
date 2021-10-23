package persistence;

// This [JsnReader] references code from this [JSON-java] CPSC 210 repository
// Link: https://github.com/stleary/JSON-java

import model.Flight;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFlight(String name, String flightNum, String destination, double duration,
                               String date, String time, int maxSeats, Flight flight) {
        assertEquals(name, flight.getName());
        assertEquals(flightNum, flight.getFlightNum());
        assertEquals(destination, flight.getDestination());
        assertEquals(duration, flight.getDuration());
        assertEquals(date, flight.getDate());
        assertEquals(time, flight.getTime());
        assertEquals(maxSeats, flight.getMaxSeats());
    }
}
