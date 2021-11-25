package persistence;// This [JsnReader] references code from this [JSON-java] CPSC 210 repository
// Link: https://github.com/stleary/JSON-java

import model.Flight;
import model.ListOfFlights;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {
    private Flight sampleFlight0;
    private Flight sampleFlight1;
    private Flight sampleFlight2;

    @BeforeEach
    public void setUp() {
        sampleFlight0 = new Flight("Emma's JFK Airline", "115JFK", "JFK", 6.0,
                "2021-12-01", "0800", 145);
        sampleFlight1 = new Flight("Emma's ICN Airline", "000ICN", "ICN", 10.5,
                "2022-01-07", "1400", 198);
        sampleFlight2 = new Flight("Emma's HNL Airline", "111HNL", "HNL", 6.5,
                "2022-02-14", "1000", 130);

    }

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfFlights lof = new ListOfFlights("My list of flights");
            JsonWriter writer = new JsonWriter("./data\00invalid.file.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyLOF() {
        try {
            ListOfFlights lof = new ListOfFlights("My list of flights");
            JsonWriter writer = new JsonWriter("./data/testWriterLOF.json");
            writer.open();
            writer.write(lof);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyLOF.json");
            lof = reader.read();
            assertEquals("My list of flights", lof.getName());
            assertEquals(0, lof.size());
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testWriterFilledLOF() {
        try {
            ListOfFlights lof = new ListOfFlights("My list of flights");
            lof.addFlight(sampleFlight0);
            lof.addFlight(sampleFlight1);
            lof.addFlight(sampleFlight2);
            JsonWriter writer = new JsonWriter("./data/testWriterFilledLOF.json");
            writer.open();
            writer.write(lof);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterFilledLOF.json");
            lof = reader.read();
            assertEquals("My list of flights", lof.getName());
            List<Flight> flights = lof.getAllFlights();
            assertEquals(3, flights.size());
            checkFlight("Emma's JFK Airline", "115JFK", "JFK", 6.0,
                    "2021-12-01", "0800", 145, lof.get(0));
            checkFlight("Emma's ICN Airline", "000ICN", "ICN", 10.5,
                    "2022-01-07", "1400", 198, lof.get(1));
            checkFlight("Emma's HNL Airline", "111HNL", "HNL", 6.5,
                    "2022-02-14", "1000", 130, lof.get(2));
        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }
}
