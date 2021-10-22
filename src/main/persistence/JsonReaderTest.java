package persistence;

import model.ListOfFlights;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNoFile() {
        JsonReader reader = new JsonReader("./data/noFileExisting.json");
        try {
            ListOfFlights lof = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testReaderEmptyLOF() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLOF.json");
        try {
            ListOfFlights lof = reader.read();
            assertEquals("My list of flights", lof.getName());
            assertEquals(0, lof.size());
        } catch (IOException e) {
            fail("Could not read from file");
        }
    }

    @Test
    void testReaderFilledLOF() {
        JsonReader reader = new JsonReader("./data/testReaderFilledLOF.json");
        try {
            ListOfFlights lof = reader.read();
            assertEquals("My list of flights", lof.getName());
            assertEquals(3, lof.size());
            checkFlight("Emma's JFK Airline", "115JFK", "JFK", 6.0,
                    "2021-12-01", "0800", 145, lof.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
