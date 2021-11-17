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
    private static int frameWidth = 400;
    private static int frameLength = 400;
    private JList list;
    private DefaultListModel flightListNames;
    private ListOfFlights allListOfFlights;
    private Flight chosenFlight;
    private Flight viewFlight;
    private JScrollPane listScrollPane;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/lof.json";

    private static final String addString = "Add Flight";
    private static final String removeString = "Remove Flight";
    private static final String viewString = "View Flight Details";
    private static final String saveString = "Save Schedule of Flights";
    private static final String loadString = "Load Schedule of Flights";
    private static final String printString = "Print Schedule of Flights";
    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton printButton;
    private JFrame frame;
    private JLabel flightDisplay;
    private JOptionPane messageDisplay;

    private String flightDetails;


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
    public void initializeButtons() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewListener());

        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());

        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());

        printButton = new JButton(printString);
        printButton.setActionCommand(printString);
        printButton.addActionListener(new PrintListener());

        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddListener());
    }

    // EFFECTS: adds all the buttons for tasks into one JPanel
    public void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(60, 80, 100));
        buttonPanel.add(viewButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(printButton);
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.add(new JSeparator((SwingConstants.VERTICAL)));
        buttonPanel.add(Box.createHorizontalStrut(5));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.setSize(100, 200);

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

    // EFFECTS: when save button is chosen, saves all list of flights into JSON file
    public class SaveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                jsonWriter.open();
                jsonWriter.write(allListOfFlights);
                jsonWriter.close();
                JOptionPane.showMessageDialog(frame, "Saved " + allListOfFlights.getName() + " to"
                                + JSON_STORE, "Save Successfully Completed",
                        JOptionPane.INFORMATION_MESSAGE);
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
            try {
                allListOfFlights = jsonReader.read();
                JOptionPane.showMessageDialog(frame,
                        "Loaded " + allListOfFlights.getName() + " from" + JSON_STORE, "Loaded File",
                        JOptionPane.INFORMATION_MESSAGE);
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
            flightListNames.remove(index);
            Flight chosenFlight = allListOfFlights.get(index);
            allListOfFlights.removeFlight(chosenFlight);

            int size = flightListNames.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == flightListNames.getSize()) {
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
            } else {
                removeButton.setEnabled(true);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: when add button is chosen, prompts user to add in a new flight with inputting fields
    private class AddListener implements ActionListener {
        JTextField nameField = new JTextField();
        JTextField numField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField dateField = new JTextField();
        JTextField timeField = new JTextField();
        JTextField maxSeatField = new JTextField();
        String[] destinationStrings = getDestinationStrings();
        JSpinner destinationSpinner = new JSpinner(new SpinnerListModel(destinationStrings));

        Object[] message = {
                "Flight Name: ", nameField,
                "Flight Number: ", numField,
                "Destination: ", destinationSpinner,
                "Duration: ", durationField,
                "Date of Departure: ", dateField,
                "Time of Departure: ", timeField,
                "Maximum Number of Seats: ", maxSeatField
        };

        @Override
        public void actionPerformed(ActionEvent e) {

            int flightNamePane = JOptionPane.showConfirmDialog(null,
                    message,
                    "Enter Flight Information",
                    JOptionPane.OK_CANCEL_OPTION);

            String nameInput = nameField.getText();
            String numInput = numField.getText();
            String destinationInput = (String) destinationSpinner.getValue();
            Double durationInput = Double.parseDouble(durationField.getText());
            String dateInput = dateField.getText();
            String timeInput = timeField.getText();
            Integer maxSeatInput = Integer.parseInt(maxSeatField.getText());

            if (flightNamePane == JOptionPane.OK_OPTION) {
                createNewFlight(nameInput, numInput, destinationInput, durationInput, dateInput, timeInput,
                        maxSeatInput);
            } else if (flightNamePane == JOptionPane.CANCEL_OPTION) {
                System.out.println("Cancelled");
            }
        }
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

    public JFormattedTextField getTextField(JSpinner spinner) {
        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            return ((JSpinner.DefaultEditor) editor).getTextField();
        } else {
            System.err.println("Unexpected editor type: "
                    + spinner.getEditor().getClass()
                    + " isn't a descendant of DefaultEditor");
            return null;
        }
    }

    // EFFECTS: when view button is chosen, displays all information of single flight chosen
    public class ViewListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            viewFlight = allListOfFlights.get(index);

            if (list.getSelectedIndex() == -1) {
                viewButton.setEnabled(false);
            } else {
                viewButton.setEnabled(true);
            }

            flightDetails = "Flight Number: " + viewFlight.getFlightNum() + "\n"
                    + "Flight Destination: " + viewFlight.getDestination() + "\n"
                    + "Duration: " + viewFlight.getDuration() + " Hours" + "\n"
                    + "Date of Departure: " + viewFlight.getDate() + "\n"
                    + "Time of Departure: " + viewFlight.getTime() + " Hours" + "\n"
                    + "Maximum Number of Seats: " + viewFlight.getMaxSeats();

            ImageIcon message = new ImageIcon("data/plane.jpeg");

            JOptionPane.showMessageDialog(frame, flightDetails, "Flight Details of " + viewFlight.getName(),
                    JOptionPane.INFORMATION_MESSAGE, message);
        }
    }

}





