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


public class AirlineGUI extends JPanel
        implements ListSelectionListener {
    private static int frameWidth = 400;
    private static int frameLength = 400;
    private JList list;
    private DefaultListModel flightListNames;
    private ListOfFlights allListOfFlights;
    private Flight chosenFlight;
    private Flight viewFlight;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/lof.json";

    private static final String addString = "Add Flight";
    private static final String removeString = "Remove Flight";
    private static final String viewString = "View Flight Details";
    private static final String saveString = "Save Schedule of Flights";
    private static final String loadString = "Load Schedule of Flights";
    private JButton addButton;
    private JButton removeButton;
    private JButton viewButton;
    private JButton saveButton;
    private JButton loadButton;
    private JFrame frame;
    private JLabel flightDisplay;
    private JOptionPane messageDisplay;

    private JTextField nameField;
    private JTextField numField;
    private JSpinner destinationSpinner;
    private JTextField durationField;
    private JTextField dateField;
    private JTextField timeField;
    private JTextField maxSeatField;

    private String nameInput;
    private String numInput;
    private String destinationInput;
    private double durationInput;
    private String dateInput;
    private String timeInput;
    private int maxSeatInput;

    private String flightDetails;


    @SuppressWarnings("methodlength")
    AirlineGUI() {
        super((new BorderLayout()));
//
//        JFrame frame = new JFrame(); // create frame
//        frame.setSize(frameWidth, frameLength); // set frame width and length
//        frame.setTitle("Emma's Travel Guide to Airlines");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);


        flightListNames = new DefaultListModel();
        allListOfFlights = new ListOfFlights("New Scheduled Flights");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
//        listModel.addElement("Flight111JFK");
//        listModel.addElement("Flight222ICN");
//        listModel.addElement("Flight333HNL");

        // Create list and put it in scroll pane
        list = new JList(flightListNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

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

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBackground(new Color(60, 80, 100));
        buttonPane.add(viewButton);
        buttonPane.add(removeButton);
        buttonPane.add(saveButton);
        buttonPane.add(loadButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator((SwingConstants.VERTICAL)));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JButton(new AddFlightAction()));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

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

    public class LoadListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                allListOfFlights = jsonReader.read();
                JOptionPane.showMessageDialog(frame,
                        "Loaded" + allListOfFlights.getName() + " from" + JSON_STORE, "Loaded File",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException i) {
                JOptionPane.showMessageDialog(frame,
                        "Unable to read from the file: " + JSON_STORE, "Load Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }


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

    private class AddFlightAction extends AbstractAction {

        AddFlightAction() {
            super("Add a new flight");
        }

        @SuppressWarnings("methodlength")
        @Override
        public void actionPerformed(ActionEvent e) {
            nameField = new JTextField();
            numField = new JTextField();
            durationField = new JTextField();
            dateField = new JTextField();
            timeField = new JTextField();
            maxSeatField = new JTextField();
            String[] destinationStrings = getDestinationStrings();
            destinationSpinner = new JSpinner(new SpinnerListModel(destinationStrings));

            Object[] message = {
                    "Flight Name: ", nameField,
                    "Flight Number: ", numField,
                    "Destination: ", destinationSpinner,
                    "Duration: ", durationField,
                    "Date of Departure: ", dateField,
                    "Time of Departure: ", timeField,
                    "Maximum Number of Seats: ", maxSeatField
            };

            int flightNamePane = JOptionPane.showConfirmDialog(null,
                    message,
                    "Enter Flight Information",
                    JOptionPane.OK_CANCEL_OPTION);

            nameInput = nameField.getText();
            numInput = numField.getText();
            String destinationInput = (String) destinationSpinner.getValue();
            durationInput = Double.parseDouble(durationField.getText());
            dateInput = dateField.getText();
            timeInput = timeField.getText();
            maxSeatInput = Integer.parseInt(maxSeatField.getText());

            if (flightNamePane == JOptionPane.OK_OPTION) {
                createNewFlight(nameInput, numInput, destinationInput, durationInput, dateInput, timeInput,
                        maxSeatInput);
            } else if (flightNamePane == JOptionPane.CANCEL_OPTION) {
                System.out.println("Cancelled");
            }
        }
    }


    public void createNewFlight(String nameInput, String numInput, String destinationInput, double durationInput,
                                String dateInput, String timeInput, int maxSeatInput) {
        Flight newFlight = new Flight(nameInput, numInput, destinationInput, durationInput, dateInput, timeInput,
                maxSeatInput);
        flightListNames.addElement(newFlight.getName());
        allListOfFlights.addFlight(newFlight);
    }

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





