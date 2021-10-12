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
        testFlight1 = new Flight("TestFlight1", "111EOK", "JFK", 6,
                LocalDate.of(2022, Month.APRIL, 5), "0800", 650);
        testFlight2 = new Flight("TestFlight2", "124LYZ", "ICN", 11,
                LocalDate.of(2022, Month.JANUARY, 20), "1430", 1100);
        testFlight3 = new Flight("TestFlight3", "352TTT", "YYC", 1.5,
                LocalDate.of(2022, Month.SEPTEMBER, 28), "0200", 220);
        testFlight4 = new Flight("TestFlight4", "444UIC", "HNL", 6,
                LocalDate.of(2022, Month.AUGUST, 1), "2400", 670);
        testFlight5 = new Flight("TestFlight5", "986QCC", "JFK", 6,
                LocalDate.of(2022, Month.JULY, 16), "1100", 700);
    }

    @Test
    public void testListOfFlights() {
        assertEquals(0, sampleLOF.size());
    }

    @Test
    public void testAddFlight() {
        assertEquals(0, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertTrue(sampleLOF.containsFlight("111EOK"));
        assertEquals(1, sampleLOF.size());
        sampleLOF.addFlight(testFlight1);
        assertEquals(1, sampleLOF.size());
    }

    @Test
    public void testRemoveFlight() {
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.removeFlight(testFlight1);
        assertFalse(sampleLOF.containsFlight("111EOK"));
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
        ListOfFlights sampleDestinationLOF = new ListOfFlights();
        assertFalse(sampleLOF.containsDestination("JFK"));
        sampleLOF.addFlight(testFlight1);
        sampleLOF.addFlight(testFlight2);
        sampleLOF.addFlight(testFlight3);
        sampleLOF.addFlight(testFlight4);
        sampleLOF.addFlight(testFlight5);
        assertEquals(5, sampleLOF.size());
        assertTrue(sampleLOF.containsDestination("JFK"));
        sampleDestinationLOF.addFlight(testFlight1);
        sampleDestinationLOF.addFlight(testFlight5);
        assertEquals(2, sampleDestinationLOF.size());

    }

    @Test
    public void testContainsDestination() {
        assertFalse(sampleLOF.containsDestination("YYC"));
        sampleLOF.addFlight(testFlight3);
        assertEquals(1, sampleLOF.size());
        assertTrue(sampleLOF.containsDestination("YYC"));
        assertFalse(sampleLOF.containsDestination("HNL"));
    }

    @Test
    public void testContainsName() {
        assertFalse(sampleLOF.containsName("TestFlight5"));
        sampleLOF.addFlight(testFlight5);
        assertEquals(1, sampleLOF.size());
        assertTrue(sampleLOF.containsName("TestFlight5"));
        assertFalse(sampleLOF.containsName("TestFlight1"));
        sampleLOF.addFlight(testFlight1);
        assertEquals(2, sampleLOF.size());
        assertTrue(sampleLOF.containsName("TestFlight1"));
    }






}