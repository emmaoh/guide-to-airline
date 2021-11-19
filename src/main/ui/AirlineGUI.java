package ui;

// This [AirlineGUI] references code from this [ListDemo] example from Oracle - JavaTutorials
// Link: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

import model.Flight;
import model.ListOfFlights;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;


// Airline Schedule Guide Graphical Application
public class AirlineGUI extends JPanel
        implements ListSelectionListener {
    private JList list;
    private DefaultListModel flightListNames;
    private ListOfFlights allListOfFlights;
    private Flight viewFlight;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/lof.json";

    private static final String addString = "Add Flight";
    private static final String removeString = "Remove Flight";
    private static final String viewString = "View Flight Details";
    private static final String saveString = "Save Schedule of Flights";
    private static final String loadString = "Load Schedule of Flights";
    private static final String printString = "Print Schedule of Flights";
    private static final String searchString = "Search Flight";

    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton printButton;
    private JButton searchButton;
    private JFrame frame;

    private String flightDetails;

    String incorrectField = "Please Enter a Double Value for the Duration Field and an Integer for Max Seats";
    String invalidTitle = "Invalid Fields";
    String missingField = "Please Enter Values in All Given Fields";


    // MODIFIES: this
    // EFFECTS: initializes the Airline application
    public AirlineGUI() {
        flightListNames = new DefaultListModel();
        allListOfFlights = new ListOfFlights("New Scheduled Flights");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);


        initializeButtons();
        addScrollPane();
        addButtonPanel();
    }


    // EFFECTS: creates a list and puts it in a scroll pane for display
    public void addScrollPane() {
        list = new JList(flightListNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
    }

    // EFFECTS: initializes JButtons for each task and corresponds it to a Listener
    @SuppressWarnings("methodlength")
    public void initializeButtons() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setBorder(BorderFactory.createRaisedBevelBorder());

        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewListener());
        viewButton.setBorder(BorderFactory.createRaisedBevelBorder());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
        loadButton.setBorder(BorderFactory.createRaisedBevelBorder());

        printButton = new JButton(printString);
        printButton.setActionCommand(printString);
        printButton.addActionListener(new PrintListener());
        printButton.setBorder(BorderFactory.createRaisedBevelBorder());
        ;

        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddListener());
        addButton.setBorder(BorderFactory.createRaisedBevelBorder());

        searchButton = new JButton(searchString);
        searchButton.setActionCommand(searchString);
        searchButton.addActionListener(new SearchListener());
        searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
    }


    // EFFECTS: adds all the buttons for tasks into one JPanel
    @SuppressWarnings("methodlength")
    public void addButtonPanel() {
        ImageIcon blueIcon = new ImageIcon("data/blue.png");
        Image newBlue = blueIcon.getImage();
        Image scaledBlue = newBlue.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
        blueIcon = new ImageIcon(scaledBlue);
        JLabel blueLabel = new JLabel(blueIcon);
        blueLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(blueLabel);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(viewButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(printButton);
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator((SwingConstants.VERTICAL)));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JScrollPane listScrollPane = new JScrollPane(list);

        add(listScrollPane, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.WEST);
    }

    // EFFECTS: when print button is chosen, displays all list of flights in schedule
    public class PrintListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (Flight f : allListOfFlights.getAllFlights()) {
                flightListNames.addElement(f.getName());
            }
        }
    }

    // EFFECTS: Prompts user to pick a certain destination and displays flights available when search button chosen
    public class SearchListener implements ActionListener {

        String[] destinationStrings = getDestinationStrings();
        JSpinner destinationSpinner = new JSpinner(new SpinnerListModel(destinationStrings));
        String chosenDestination = (String) destinationSpinner.getValue();

        Object[] destinationMessage = {
                "Destination ", destinationSpinner
        };

        @Override
        public void actionPerformed(ActionEvent e) {
            ImageIcon globeImage = new ImageIcon("data/globe.jpeg");
            Image newGlobe = globeImage.getImage();
            Image newImage = newGlobe.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
            globeImage = new ImageIcon(newImage);

            int flightSearchPane = JOptionPane.showConfirmDialog(null,
                    destinationMessage,
                    "Choose Flight Destination",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, globeImage);

            if (flightSearchPane == JOptionPane.OK_OPTION) {
                searchFlight(chosenDestination);
            }
        }
    }

    public void searchFlight(String destination) {
        ListOfFlights destinationFlights = allListOfFlights.searchFlight(destination);
        int numberOfFlights = destinationFlights.size();
        flightListNames.clear();
        for (int s = 0; s < numberOfFlights; s++) {
            Flight f = destinationFlights.get(s);
            String searchedFlight = f.getName();
            flightListNames.addElement(searchedFlight);
            list.setEnabled(true);
            list.setVisible(true);
        }
    }

    // EFFECTS: when save button is chosen, saves all list of flights into JSON file
    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ImageIcon folderImage = new ImageIcon("data/folder.png");
            Image newFolder = folderImage.getImage();
            Image newImage = newFolder.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
            folderImage = new ImageIcon(newImage);

            try {
                jsonWriter.open();
                jsonWriter.write(allListOfFlights);
                jsonWriter.close();
                JOptionPane.showMessageDialog(frame, "Saved " + allListOfFlights.getName() + " to"
                                + JSON_STORE, "Save Successfully Completed",
                        JOptionPane.INFORMATION_MESSAGE, folderImage);
            } catch (FileNotFoundException f) {
                JOptionPane.showMessageDialog(frame, "Unable to write to the file: " + JSON_STORE, "Save Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // EFFECTS: when load button is chosen, loads all list of flights from JSON file
    public class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            ImageIcon loadIcon = new ImageIcon("data/planeload.jpeg");
            Image newLoad = loadIcon.getImage();
            Image scaledLoad = newLoad.getScaledInstance(165, 170, Image.SCALE_SMOOTH);
            loadIcon = new ImageIcon(scaledLoad);
            try {
                allListOfFlights = jsonReader.read();
                JOptionPane.showMessageDialog(frame,
                        "Loaded " + allListOfFlights.getName() + " from" + JSON_STORE, "Loaded File",
                        JOptionPane.INFORMATION_MESSAGE, loadIcon);
            } catch (IOException i) {
                JOptionPane.showMessageDialog(frame,
                        "Unable to read from the file: " + JSON_STORE, "Load Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: when remove button is chosen, removes chosen index of flight from display and list of flights
    public class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int size = flightListNames.size();

            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                flightListNames.remove(index);
                Flight chosenFlight = allListOfFlights.get(index);
                allListOfFlights.removeFlight(chosenFlight);

                if (index == size) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                removeButton.setEnabled(false);
                viewButton.setEnabled(false);
            } else {
                removeButton.setEnabled(true);
                viewButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: when add button is chosen, prompts user to add in a new flight with inputting fields
    public class AddListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JTextField nameField = new JTextField();
            JTextField numField = new JTextField();
            JTextField durationField = new JTextField();
            JTextField dateField = new JTextField();
            JTextField timeField = new JTextField();
            JTextField maxSeatField = new JTextField();
            String[] destinationStrings = getDestinationStrings();
            JSpinner destinationSpinner = new JSpinner(new SpinnerListModel(destinationStrings));

            try {
                addOptionsPage(nameField, numField, destinationSpinner, durationField, dateField, timeField,
                        maxSeatField);
            } catch (NumberFormatException n) {
                JOptionPane.showMessageDialog(null, incorrectField, invalidTitle,
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // EFFECTS: creates JOptionPane to input info on a new flight to add
    public void addOptionsPage(JTextField nameField, JTextField numField, JSpinner destinationSpinner,
                               JTextField durationField, JTextField dateField, JTextField timeField,
                               JTextField maxSeatField) {
        Object[] message = {
                "Flight Name: ", nameField,
                "Flight Number: ", numField,
                "Destination: ", destinationSpinner,
                "Duration: ", durationField,
                "Date of Departure: ", dateField,
                "Time of Departure: ", timeField,
                "Maximum Number of Seats: ", maxSeatField
        };

        int enterFlightInfo = JOptionPane.showConfirmDialog(null,
                message, "Enter Flight Information", JOptionPane.OK_CANCEL_OPTION);

        String name = nameField.getText();
        String flightNumber = numField.getText();
        String destination = (String) destinationSpinner.getValue();
        Double duration = Double.parseDouble(durationField.getText());
        String date = dateField.getText();
        String time = timeField.getText();
        Integer maxSeats = Integer.parseInt(maxSeatField.getText());

        if (enterFlightInfo == JOptionPane.OK_OPTION) {
            if (isFieldsEmpty(name, flightNumber, duration, date, time, maxSeats)) {
                JOptionPane.showMessageDialog(null, missingField, invalidTitle, JOptionPane.ERROR_MESSAGE);
            } else {
                createNewFlight(name, flightNumber, destination, duration, date, time, maxSeats);
            }
        }
    }

    // EFFECTS: returns true if any of JTextFields were submitted empty, false otherwise
    public boolean isFieldsEmpty(String name, String num, Double duration, String date, String time, Integer maxSeats) {
        if (name.isEmpty() || num.isEmpty() || duration.equals(null) || date.isEmpty() || time.isEmpty()
                || maxSeats.equals(null)) {
            return true;
        }
        return false;
    }


    // EFFECTS: creates a new flight object given by the inputted fields from user
    public void createNewFlight(String nameInput, String numInput, String destinationInput, double durationInput,
                                String dateInput, String timeInput, int maxSeatInput) {
        Flight newFlight = new Flight(nameInput, numInput, destinationInput, durationInput, dateInput, timeInput,
                maxSeatInput);
        flightListNames.addElement(newFlight.getName());
        allListOfFlights.addFlight(newFlight);
    }

    // EFFECTS: returns all destinations (string) that user may choose from for spinner
    public String[] getDestinationStrings() {
        String[] destinationStrings = {
                "JFK - New York",
                "YYZ - Toronto",
                "ICN - Seoul",
                "HNL - Honolulu",
                "LHR - London",
                "MXP - Milan",
                "BCN - Barcelona",
                "NRT - Tokyo",
                "HKG - Hong Kong",
                "LAX - Los Angeles",
                "SFO - San Francisco",
                "SEA - Seattle",
                "SYD - Sydney",
                "SVO - Moscow",
                "DXB - Dubai",
                "BKK - Bangkok"
        };
        return destinationStrings;
    }


    // EFFECTS: when view button is chosen, displays all information of single flight chosen
    public class ViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            int size = flightListNames.size();

            if (size == 0) {
                viewButton.setEnabled(false);
            } else {
                viewFlight = allListOfFlights.get(index);

                flightDetails = "Flight Number: " + viewFlight.getFlightNum() + "\n"
                        + "Flight Destination: " + viewFlight.getDestination() + "\n"
                        + "Duration: " + viewFlight.getDuration() + " Hours" + "\n"
                        + "Date of Departure: " + viewFlight.getDate() + "\n"
                        + "Time of Departure: " + viewFlight.getTime() + " Hours" + "\n"
                        + "Maximum Number of Seats: " + viewFlight.getMaxSeats();

                ImageIcon message = new ImageIcon("data/plane.jpeg");
                Image newMessage = message.getImage();
                Image newImage = newMessage.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
                message = new ImageIcon(newImage);

                JOptionPane.showMessageDialog(frame, flightDetails, "Flight Details of " + viewFlight.getName(),
                        JOptionPane.INFORMATION_MESSAGE, message);

                if (index == size) {
                    index--;

                }
            }

        }
    }

}





