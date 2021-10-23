import model.ListOfFlights;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonTest;

import java.io.IOException;

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
            checkFlight("Emma's ICN Airline", "000ICN", "ICN", 10.5,
                    "2022-01-07", "1400", 198, lof.get(1));
            checkFlight("Emma's HNL Airline", "111HNL", "HNL", 6.5,
                    "2022-02-14", "1000", 130, lof.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
