package persistence;

// This [JsnReader] references code from this [JSON-java] CPSC 210 repository
// Link: https://github.com/stleary/JSON-java

import model.ListOfFlights;
import model.Flight;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;


// Represents a reader that reads ListOfFlights from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ListOfFlights from file and returns it
    // throws IQException if an error occurs reading data from file
    public ListOfFlights read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfFlights(jsonObject);
    }

    // EFFECTS: reads source file as a string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }
        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOfFlights from JSON object and returns it
    private ListOfFlights parseListOfFlights(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ListOfFlights lof = new ListOfFlights(name);
        addFlights(lof, jsonObject);
        return lof;
    }

    // MODIFIES: ListOfFlights
    // EFFECTS: parses flights from JSON object and adds them to ListOfFlights
    private void addFlights(ListOfFlights lof, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("flights");
        for (Object json : jsonArray) {
            JSONObject nextFlight = (JSONObject) json;
            addFlight(lof, nextFlight);
        }
    }

    // MODIFIES: ListOfFlights
    // EFFECTS: parses flight from JSON object and adds it to ListOfFlights
    private void addFlight(ListOfFlights lof, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String flightNum = jsonObject.getString("flightNum");
        String destination = jsonObject.getString("destination");
        Double duration = jsonObject.optDouble("duration");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        Integer maxSeats = jsonObject.getInt("maxSeats");
        Flight flight = new Flight(name, flightNum, destination, duration, date, time, maxSeats);
        lof.addFlight(flight);
    }
}
