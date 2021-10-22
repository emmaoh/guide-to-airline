package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ListOfFlightsTest {
    private ListOfFlights sampleLOF;
    private Flight testFlight1;
    private Flight testFlight2;
    private Flight testFlight3;
    private Flight testFlight4;
    private Flight testFlight5;

    @BeforeEach
    public void setUp() {
        sampleLOF = new ListOfFlights("New Flights");
        testFlight1 = new Flight("TestFlight1", "111EOK", "JFK", 6,
                "2022-04-05", "0800", 135);
        testFlight2 = new Flight("TestFlight2", "124LYZ", "ICN", 11,
                "2022-01-20", "1430", 178);
        testFlight3 = new Flight("TestFlight3", "352TTT", "YYC", 1.5,
                "2022-09-28", "0200", 110);
        testFlight4 = new Flight("TestFlight4", "444UIC", "HNL", 6,
                "2022-08-01", "2400", 140);
        testFlight5 = new Flight("TestFlight5", "986QCC", "JFK", 6,
                "2022-07-16", "1100", 137);
    }

    @Test
    public void testListOfFlights() {
        assertEquals(0, sampleLOF.size());
        assertEquals("New Flights", sampleLOF.getName());
    }

    @Test
    public void testConstructors() {
        assertEquals("TestFlight1", testFlight1.getName());
        assertEquals("111EOK", testFlight1.getFlightNum());
        assertEquals("JFK", testFlight1.getDestination());
        assertEquals(6, testFlight1.getDuration());
        assertEquals("2022-04-05", testFlight1.getDate());
        assertEquals("0800", testFlight1.getTime());
        assertEquals(135, testFlight1.getMaxSeats());
    }

    @Test
    public void testAddFlight() {
        assertEquals(0, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertTrue(sampleLOF.containsFlight(testFlight1));
        assertEquals(1, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testRemoveFlight() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.removeFlight(testFlight1);
        assertFalse(sampleLOF.containsFlight(testFlight1));
        assertEquals(1, sampleLOF.size());
        sampleLOF.removeFlight(testFlight5);
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testContainsFlight() {
        assertFalse(sampleLOF.containsFlight(testFlight1));
        assertFalse(sampleLOF.containsFlight(testFlight2));
        sampleLOF.addFlight(testFlight3);
        assertTrue(sampleLOF.containsFlight(testFlight3));
        assertEquals(1, sampleLOF.size());
        sampleLOF.removeFlight(testFlight3);
        assertFalse(sampleLOF.containsFlight(testFlight3));
        assertEquals(0, sampleLOF.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, sampleLOF.size());
        sampleLOF.addFlight(testFlight4);
        sampleLOF.addFlight(testFlight5);
        assertEquals(2, sampleLOF.size());
    }

    @Test
    public void testSearchFlight() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.addFlight(testFlight3);
        sampleLOF.addFlight(testFlight4);
        sampleLOF.addFlight(testFlight5);
        assertEquals(5, sampleLOF.size());
        assertEquals(2, sampleLOF.searchFlight("JFK").size());

    }

    @Test
    public void testDurationToString() {
        assertEquals("6.00", testFlight1.durationToString(testFlight1.getDuration()));
    }

    @Test
    public void testSeatToString() {
        assertEquals("135", testFlight1.seatToString(testFlight1.getMaxSeats()));
    }

    @Test
    public void testIsEmpty() {
        assertTrue(sampleLOF.isEmpty());
        sampleLOF.addFlight(testFlight1);
        assertFalse(sampleLOF.isEmpty());
        sampleLOF.removeFlight(testFlight1);
        assertTrue(sampleLOF.isEmpty());
    }

    @Test
    public void testContainsDestination() {
        assertFalse(sampleLOF.containsDestination(testFlight1.getDestination()));
        sampleLOF.addFlight(testFlight1);
        assertTrue(sampleLOF.containsDestination(testFlight1.getDestination()));
        sampleLOF.removeFlight(testFlight1);
        assertFalse(sampleLOF.containsDestination(testFlight1.getDestination()));
        sampleLOF.addFlight(testFlight2);
        assertFalse(sampleLOF.containsDestination(testFlight1.getDestination()));
    }

    @Test
    public void testGet() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.addFlight(testFlight3);
        assertEquals(testFlight1, sampleLOF.get(0));
        assertEquals(testFlight2, sampleLOF.get(1));
        assertEquals(testFlight3, sampleLOF.get(2));
    }
}
