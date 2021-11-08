package ui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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


    @SuppressWarnings("methodlength")
    AirlineGUI() {
        super((new BorderLayout()));

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

        JButton addButton = new JButton(addString);
        AddListener addListener = new AddListener(addButton);
        addButton.setActionCommand(addString);
        addButton.addActionListener(addListener);
        addButton.setEnabled(false);

        flightName = new JTextField(10);
        flightName.addActionListener(addListener);
        flightName.getDocument().addDocumentListener(addListener);
        String name = listModel.getElementAt(
                list.getSelectedIndex()).toString();

        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(new JSeparator((SwingConstants.VERTICAL)));
        buttonPane.add(Box.createHorizontalStrut(5));
        buttonPane.add(flightName);
        buttonPane.add(addButton);
        buttonPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(listScrollPane, BorderLayout.CENTER);
        add(buttonPane, BorderLayout.PAGE_END);
    }

    // Listener shared by text field and add button
    public class AddListener implements ActionListener, DocumentListener {
        private boolean alreadyEnabled = false;
        private JButton button;

        public AddListener(JButton button) {
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String name = flightName.getText();

            if (name.equals("") || alreadyInList(name)) {
                Toolkit.getDefaultToolkit().beep();
                flightName.requestFocusInWindow();
                flightName.selectAll();
                return;
            }

            int index = list.getSelectedIndex(); // get selected index
            if (index == -1) { // no selection, so insert at beginning
                index = 0;
            } else {           // add after selected item
                index++;
            }

            listModel.addElement(flightName.getText()); // add Name at end of list

            // Resetting text field after submission
            flightName.requestFocusInWindow();
            flightName.setText("");

            // Select new item and make visible on panel
            list.setSelectedIndex(index);
            list.ensureIndexIsVisible(index);
        }

        protected boolean alreadyInList(String name) {
            return listModel.contains(name);
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            enableButton();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            handleEmptyTextField(e);
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            if (!handleEmptyTextField(e)) {
                enableButton();
            }
        }

        private void enableButton() {
            if (!alreadyEnabled) {
                button.setEnabled(true);
            }
        }

        private boolean handleEmptyTextField(DocumentEvent e) {
            if (e.getDocument().getLength() <= 0) {
                button.setEnabled(false);
                alreadyEnabled = false;
                return true;
            }
            return false;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
                addButton.setEnabled(false);
            } else {
                addButton.setEnabled(true);
            }
        }
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Emma's Travel Guide to Airlines");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JComponent newContentPane = new AirlineGUI();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        frame.pack();
        frame.setVisible(true);
    }

    public void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}



