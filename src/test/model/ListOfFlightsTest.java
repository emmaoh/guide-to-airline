package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.time.Month;


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
        sampleLOF = new ListOfFlights();
        testFlight1 = new Flight("TestFlight1", "111EOK", "JFK", 6,
                LocalDate.of(2022, Month.APRIL, 5), "0800", 135);
        testFlight2 = new Flight("TestFlight2", "124LYZ", "ICN", 11,
                LocalDate.of(2022, Month.JANUARY, 20), "1430", 178);
        testFlight3 = new Flight("TestFlight3", "352TTT", "YYC", 1.5,
                LocalDate.of(2022, Month.SEPTEMBER, 28), "0200", 110);
        testFlight4 = new Flight("TestFlight4", "444UIC", "HNL", 6,
                LocalDate.of(2022, Month.AUGUST, 1), "2400", 140);
        testFlight5 = new Flight("TestFlight5", "986QCC", "JFK", 6,
                LocalDate.of(2022, Month.JULY, 16), "1100", 137);
    }

    @Test
    public void testListOfFlights() {
        assertEquals(0, sampleLOF.size());
    }

    @Test
    public void testConstructors() {
        assertEquals("TestFlight1", testFlight1.getName());
        assertEquals("111EOK", testFlight1.getFlightNum());
        assertEquals("JFK", testFlight1.getDestination());
        assertEquals(6, testFlight1.getDuration());
        assertEquals(LocalDate.of(2022, Month.APRIL, 5), testFlight1.getDate());
        assertEquals("0800", testFlight1.getTime());
        assertEquals(135, testFlight1.getMaxSeats());
    }

    @Test
    public void testAddFlight() {
        assertEquals(0, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertTrue(sampleLOF.containsFlight(testFlight1.getFlightNum()));
        assertEquals(1, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testRemoveFlight() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.removeFlight(testFlight1);
        assertFalse(sampleLOF.containsFlight(testFlight1.getFlightNum()));
        assertEquals(1, sampleLOF.size());
        sampleLOF.removeFlight(testFlight3);
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testContainsFlight() {
        assertFalse(sampleLOF.containsFlight("523LLL"));
        assertFalse(sampleLOF.containsFlight("111EOK"));
        sampleLOF.addFlight(testFlight3);
        assertTrue(sampleLOF.containsFlight("352TTT"));
        assertEquals(1, sampleLOF.size());
        sampleLOF.removeFlight(testFlight3);
        assertFalse(sampleLOF.containsFlight("352TTT"));
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
    public void testViewFlight() {
        assertEquals(null, sampleLOF.viewFlight("TestFlight1"));
        sampleLOF.addFlight(testFlight1);
        assertEquals(testFlight1, sampleLOF.viewFlight("TestFlight1"));
        assertEquals(null, sampleLOF.viewFlight("TestFlight2"));
    }

    @Test
    public void testDurationToString() {
        assertEquals("6.00", testFlight1.durationToString(testFlight1.getDuration()));
    }

    @Test
    public void testDateToString() {
        assertEquals("2022-Apr-05", testFlight1.dateToString(testFlight1.getDate()));
        assertEquals("2022-Jan-20", testFlight2.dateToString(testFlight2.getDate()));
    }

    @Test
    public void testSeatToString() {
        assertEquals("135", testFlight1.seatToString(testFlight1.getMaxSeats()));
    }





}