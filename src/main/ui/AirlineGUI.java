package ui;

// This [AirlineGUI] references code from this [ListDemo] example from Oracle - JavaTutorials
// Link: https://docs.oracle.com/javase/tutorial/uiswing/examples/components/index.html

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AirlineGUI extends JPanel
        implements ListSelectionListener {
    private static int frameWidth = 400;
    private static int frameLength = 400;
    private JList list;
    private DefaultListModel listModel;

    private static final String addString = "Add Flight";
    private static final String removeString = "Remove Flight";
    private JTextField flightName;
    private JButton addButton;
    private JButton removeButton;
    private JFrame frame;

    private JTextField nameField;
    private JTextField numField;
    private JTextField durationField;
    private JTextField dateField;
    private JTextField timeField;
    private JSpinner destinationSpinner;


    @SuppressWarnings("methodlength")
    AirlineGUI() {
        super((new BorderLayout()));

        JFrame frame = new JFrame("Emma's Travel Guide to Airlines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setBackground(new Color(75, 130, 150));

        listModel = new DefaultListModel();
        listModel.addElement("Flight111JFK");
        listModel.addElement("Flight222ICN");
        listModel.addElement("Flight333HNL");

        // Create list and put it in scroll pane
        list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);

//        JButton addButton = new JButton(addString);
//        AddListener addListener = new AddListener(addButton);
//        addButton.setActionCommand(addString);
//        addButton.addActionListener(addListener);
//        addButton.setEnabled(false);

        removeButton = new JButton(removeString);
        removeButton.setActionCommand(removeString);
        removeButton.addActionListener(new RemoveListener());

//        flightName = new JTextField(10);
//        flightName.addActionListener(addListener);
//        flightName.getDocument().addDocumentListener(addListener);
//        String name = listModel.getElementAt(
//                list.getSelectedIndex()).toString();

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.setBackground(new Color(60, 80, 100));
        buttonPane.add(removeButton);
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator((SwingConstants.VERTICAL)));
        buttonPane.add(Box.createHorizontalStrut(5));
//        buttonPane.add(flightName);
        buttonPane.add(new JButton(new AddFlightAction()));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    public class RemoveListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            int index = list.getSelectedIndex();
            listModel.remove(index);

            int size = listModel.getSize();

            if (size == 0) {
                removeButton.setEnabled(false);
            } else {
                if (index == listModel.getSize()) {
                    index--;
                }
                list.setSelectedIndex(index);
                list.ensureIndexIsVisible(index);
            }
        }
    }

//    //  Listener shared by text field and add button
//    public class AddListener implements ActionListener, DocumentListener {
//        private boolean alreadyEnabled = false;
//        private JButton button;
//
//        public AddListener(JButton button) {
//            this.button = button;
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            new AddFlightAction();
//
//            String name = flightName.getText();
//
//            if (name.equals("") || alreadyInList(name)) {
//                Toolkit.getDefaultToolkit().beep();
//                flightName.requestFocusInWindow();
//                flightName.selectAll();
//                return;
//            }
//
//            int index = list.getSelectedIndex(); // get selected index
//            if (index == -1) { // no selection, so insert at beginning
//                index = 0;
//            } else {           // add after selected item
//                index++;
//            }
//
//            listModel.addElement(flightName.getText()); // add Name at end of list
//
//            // Resetting text field after submission
//            flightName.requestFocusInWindow();
//            flightName.setText("");
//
//            // Select new item and make visible on panel
//            list.setSelectedIndex(index);
//            list.ensureIndexIsVisible(index);
//        }
//
//
//        @Override
//        public void insertUpdate(DocumentEvent e) {
//            enableButton();
//        }
//
//        @Override
//        public void removeUpdate(DocumentEvent e) {
//            handleEmptyTextField(e);
//        }
//
//        @Override
//        public void changedUpdate(DocumentEvent e) {
//            if (!handleEmptyTextField(e)) {
//                enableButton();
//            }
//        }
//
//        private void enableButton() {
//            if (!alreadyEnabled) {
//                button.setEnabled(true);
//            }
//        }
//
//        private boolean handleEmptyTextField(DocumentEvent e) {
//            if (e.getDocument().getLength() <= 0) {
//                button.setEnabled(false);
//                alreadyEnabled = false;
//                return true;
//            }
//            return false;
//        }
//    }

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

        @Override
        public void actionPerformed(ActionEvent e) {
            nameField = new JTextField();
            numField = new JTextField();
            Object[] message = {
                    "Flight Name: ", nameField,
                    "Flight Number: ", numField
            };
            String flightNamePane = JOptionPane.showInputDialog(null,
                    message,
                    "Enter Flight Information",
                    JOptionPane.QUESTION_MESSAGE);
        }
    }


    public boolean alreadyInList(String name) {
        return listModel.contains(name);
    }
}



