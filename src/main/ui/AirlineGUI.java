package ui;

// This [AirlineGUI] references code from this [ListDemo] example from Oracle - JavaTutorials
// Link: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

// This [AirlineGUI] references code from this [AlarmSystem] example from CPSC210 Repository
// Link: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git

import model.Flight;
import model.ListOfFlights;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;


// Airline Schedule Guide Graphical Application
public class AirlineGUI extends JFrame
        implements ListSelectionListener {
    private JList list;
    private final DefaultListModel flightListNames;
    private ListOfFlights allListOfFlights;
    private Flight viewFlight;

    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/lof.json";

    private static final String addString = "Add Flight";
    private static final String removeString = "Remove Flight";
    private static final String viewString = "View Flight Details";
    private static final String saveString = "Save Schedule of Flights";
    private static final String loadString = "Load Schedule of Flights";
    private static final String printString = "Print Schedule of Flights";
    private static final String searchString = "Search Flight";

    private static final int frameWidth = 650;
    private static final int frameHeight = 500;

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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JPanel scrollPanel = new JPanel();

        initializeButtons();
        addScrollPane(scrollPanel);
        addButtonPanel(buttonPanel);

        JFrame frame = new JFrame(); // create frame
        frame.setTitle("Emma's Travel Guide to Airlines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(frameWidth, frameHeight);
        frame.add(buttonPanel, BorderLayout.EAST);
        frame.add(scrollPanel, BorderLayout.CENTER);
        frame.getContentPane().setBackground(new Color(150, 204, 255));

        frame.setLocationRelativeTo(null);
        frame.add(createHeader(), BorderLayout.PAGE_START);
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to add into main frame as header
    public JLabel createHeader() {
        JLabel mainLabel = new JLabel("Time to Schedule Flights!");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setFont(new Font("Serif", Font.BOLD, 20));
        return mainLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a list and puts it in a scroll pane for display
    public void addScrollPane(JPanel scrollPanel) {
        scrollPanel.setSize(100, 300);
        scrollPanel.setOpaque(false);
        scrollPanel.setAlignmentX(SwingConstants.LEFT);

        list = new JList(flightListNames);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(10);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(370, 230));
        listScrollPane.setVisible(true);

        scrollPanel.add(listScrollPane, BorderLayout.WEST);
    }

    // EFFECTS: initializes JButtons for each task and corresponds it to a Listener
    public void initializeButtons() {
        removeButtonSetUp();
        viewButtonSetUp();
        saveButtonSetUp();
        loadButtonSetUp();
        printButtonSetUp();
        addButtonSetUp();
        searchButtonSetUp();
    }

    // MODIFIES: this
    // EFFECTS: initializes remove button
    public void removeButtonSetUp() {
        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());
        removeButton.setBorder(BorderFactory.createRaisedBevelBorder());
        removeButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes view button
    public void viewButtonSetUp() {
        viewButton = new JButton(viewString);
        viewButton.setActionCommand(viewString);
        viewButton.addActionListener(new ViewListener());
        viewButton.setBorder(BorderFactory.createRaisedBevelBorder());
        viewButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes save button
    public void saveButtonSetUp() {
        saveButton = new JButton(saveString);
        saveButton.setActionCommand(saveString);
        saveButton.addActionListener(new SaveListener());
        saveButton.setBorder(BorderFactory.createRaisedBevelBorder());
        saveButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes load button
    public void loadButtonSetUp() {
        loadButton = new JButton(loadString);
        loadButton.setActionCommand(loadString);
        loadButton.addActionListener(new LoadListener());
        loadButton.setBorder(BorderFactory.createRaisedBevelBorder());
        loadButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes print button
    public void printButtonSetUp() {
        printButton = new JButton(printString);
        printButton.setActionCommand(printString);
        printButton.addActionListener(new PrintListener());
        printButton.setBorder(BorderFactory.createRaisedBevelBorder());
        printButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes add button
    public void addButtonSetUp() {
        addButton = new JButton(addString);
        addButton.setActionCommand(addString);
        addButton.addActionListener(new AddListener());
        addButton.setBorder(BorderFactory.createRaisedBevelBorder());
        addButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: initializes search button
    public void searchButtonSetUp() {
        searchButton = new JButton(searchString);
        searchButton.setActionCommand(searchString);
        searchButton.addActionListener(new SearchListener());
        searchButton.setBorder(BorderFactory.createRaisedBevelBorder());
        searchButton.setOpaque(true);
    }

    // MODIFIES: this
    // EFFECTS: adds all the buttons for tasks into one JPanel
    public void addButtonPanel(JPanel buttonPanel) {
        JLabel buttonLabel = new JLabel("Button Menu");
        buttonLabel.setFont(new Font("Serif", Font.ITALIC, 17));
        buttonLabel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        buttonLabel.setOpaque(true);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setSize(200, 100);
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(buttonLabel, BorderLayout.PAGE_START);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        buttonPanel.add(viewButton, BorderLayout.EAST);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(removeButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(loadButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(printButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(addButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        buttonPanel.add(searchButton);
    }

    public void addImage(JPanel imagePanel) {
        ImageIcon blueIcon = new ImageIcon("data/blue.png");
        Image newBlue = blueIcon.getImage();
        Image scaledBlue = newBlue.getScaledInstance(100, 200, Image.SCALE_DEFAULT);
        blueIcon = new ImageIcon(scaledBlue);
        JLabel blueLabel = new JLabel(blueIcon);
        blueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        imagePanel.add(blueLabel);
        imagePanel.add(Box.createRigidArea(new Dimension(-80, 0)));
        imagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        imagePanel.setOpaque(false);
        imagePanel.setPreferredSize(new Dimension(100, 200));
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
                searchFlight(destinationSpinner.getValue().toString());
            }
        }
    }

    public void searchFlight(String destination) {
//        ListOfFlights destinationFlights = allListOfFlights.searchFlight(destination);
        int numberOfFlights = allListOfFlights.searchFlight(destination).size();
        flightListNames.clear();
        for (int s = 0; s < numberOfFlights; s++) {
            Flight f = allListOfFlights.searchFlight(destination).get(s);
            String searchedFlight = f.getName();
            flightListNames.addElement(searchedFlight);
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

            addOptionsPage(nameField, numField, destinationSpinner, durationField, dateField, timeField,
                    maxSeatField);
        }
    }

    // EFFECTS: creates JOptionPane to input info on a new flight to add and calls response to "Ok" button
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
                message, "Enter Flight Information", JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
                addFlightImage());
        respondOKOption(nameField, numField, destinationSpinner, durationField, dateField, timeField, maxSeatField,
                enterFlightInfo);
    }


    // EFFECTS: creates a new flight given by inputted text fields if not empty. throws exception if invalid input for
    // duration and max seats fields
    public void respondOKOption(JTextField nameField, JTextField numField, JSpinner destinationSpinner,
                                JTextField durationField, JTextField dateField, JTextField timeField,
                                JTextField maxSeatField, int enterFlightInfo) {
        String name = nameField.getText();
        String flightNumber = numField.getText();
        String destination = destinationSpinner.getValue().toString();
        String durationString = durationField.getText();
        String maxSeatsString = maxSeatField.getText();
        String date = dateField.getText();
        String time = timeField.getText();

        if (enterFlightInfo == JOptionPane.OK_OPTION) {
            if (isFieldsEmpty(name, flightNumber, durationString, date, time, maxSeatsString)) {
                JOptionPane.showMessageDialog(null, missingField, invalidTitle, JOptionPane.ERROR_MESSAGE,
                        errorImage());
            } else if (!isFieldsEmpty(name, flightNumber, durationString, date, time, maxSeatsString)) {
                try {
                    Double duration = Double.parseDouble(durationField.getText());
                    Integer maxSeats = Integer.parseInt(maxSeatField.getText());
                    createNewFlight(name, flightNumber, destination, duration, date, time, maxSeats);
                } catch (NumberFormatException n) {
                    JOptionPane.showMessageDialog(null, incorrectField, invalidTitle,
                            JOptionPane.ERROR_MESSAGE, errorImage());
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: returns image resized for the add flight button
    public ImageIcon addFlightImage() {
        ImageIcon background = new ImageIcon("data/addBackground.jpeg");
        Image newMessage = background.getImage();
        Image newImage = newMessage.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
        background = new ImageIcon(newImage);
        return background;
    }

    // MODIFIES: this
    // EFFECTS: returns image resized for the JOption error messages
    public ImageIcon errorImage() {
        ImageIcon background = new ImageIcon("data/errorIcon.jpeg");
        Image newMessage = background.getImage();
        Image newImage = newMessage.getScaledInstance(145, 170, Image.SCALE_SMOOTH);
        background = new ImageIcon(newImage);
        return background;
    }


    // EFFECTS: returns true if any of JTextFields were submitted empty, false otherwise
    public boolean isFieldsEmpty(String name, String num, String duration, String date, String time, String maxSeats) {
        return name.isEmpty() || num.isEmpty() || duration.isEmpty() || date.isEmpty() || time.isEmpty()
                || maxSeats.isEmpty();
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





