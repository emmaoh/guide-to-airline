package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

// flights with same number ...?
// duplicates ...?
class FlightsTest {
    private ListOfFlights sampleLOF;
    private Flight testFlight1;
    private Flight testFlight2;
    private Flight testFlight3;
    private Flight testFlight4;
    private Flight testFlight5;

    @BeforeEach
    public void setUp() {
        sampleLOF = new ListOfFlights();
        testFlight1 = new Flight("111EOK", "JFK", 6,
                LocalDate.of(2022, Month.APRIL, 5), "0800");
        testFlight2 = new Flight("124LYZ", "ICN", 11,
                LocalDate.of(2022, Month.JANUARY, 20), "1430");
        testFlight3 = new Flight("352TTT", "YYC", 1.5,
                LocalDate.of(2022, Month.SEPTEMBER, 28), "0200");
        testFlight4 = new Flight("444UIC", "HNL", 6,
                LocalDate.of(2022, Month.AUGUST, 1), "2400");
        testFlight5 = new Flight("986QCC", "JFK", 6,
                LocalDate.of(2022, Month.JULY, 16), "1100");
    }

    @Test
    public void testListOfFlights() {
        assertEquals(0, sampleLOF.size());
    }

    @Test
    public void testAddFlight() {
        sampleLOF.addFlight(testFlight1);
        assertTrue(sampleLOF.containsFlight("111EOK"));
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testRemoveFlight() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.removeFlight(testFlight1);
        assertFalse(sampleLOF.containsFlight("111EOK"));
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testContainsFlight() {
        assertFalse(sampleLOF.containsFlight("523LLL"));
        assertFalse(sampleLOF.containsFlight("111EOK"));
        sampleLOF.addFlight(testFlight3);
        assertTrue(sampleLOF.containsFlight("352TTT"));
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, sampleLOF.size());
        sampleLOF.addFlight(testFlight4);
        sampleLOF.addFlight(testFlight5);
        assertEquals(2, sampleLOF.size());
    }




}