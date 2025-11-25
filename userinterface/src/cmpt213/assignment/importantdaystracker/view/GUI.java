package cmpt213.assignment.importantdaystracker.view;

import cmpt213.assignment.importantdaystracker.control.ImportantDaysTracker;
import cmpt213.assignment.importantdaystracker.model.ImportantDay;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.ArrayList;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

/**
 * The class that deals with the user interface of the program
 */
public class GUI {
    private JPanel listOfDaysPanel;

    /**
     * Constructor for the GUI
     */
    public GUI() {
        JFrame frame = new JFrame("Important Days Tracker");
        frame.setSize(600, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        // Panel for all the buttons
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // First row of buttons
        JPanel buttonsPanel = new JPanel();
        JButton allButton = new JButton("All");
        JButton thisYearButton = new JButton("This Year");
        JButton passedThisYearButton = new JButton("Passed this year");
        JButton upcomingThisYearButton = new JButton("Upcoming this year");

        buttonsPanel.add(allButton);
        buttonsPanel.add(thisYearButton);
        buttonsPanel.add(passedThisYearButton);
        buttonsPanel.add(upcomingThisYearButton);

        // Row of radio buttons
        JPanel radioPanel = new JPanel();
        JRadioButton anniversaryButton = new JRadioButton("Anniversary");
        JRadioButton birthdayButton = new JRadioButton("Birthday");
        JRadioButton occasionButton = new JRadioButton("Occasion");

        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(anniversaryButton);
        radioGroup.add(birthdayButton);
        radioGroup.add(occasionButton);

        radioPanel.add(anniversaryButton);
        radioPanel.add(birthdayButton);
        radioPanel.add(occasionButton);

        // Add buttons to the top options panel
        topPanel.add(buttonsPanel, BorderLayout.NORTH);
        topPanel.add(radioPanel, BorderLayout.SOUTH);

        // Middle scroll Panel
        listOfDaysPanel = new JPanel();
        listOfDaysPanel.setLayout(new BoxLayout(listOfDaysPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listOfDaysPanel);
        updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listAll"), "");

        // Bottom button panel
        JPanel bottompanel = new JPanel();
        JButton addImportantDayButton = new JButton("Add an Important Day");
        bottompanel.add(addImportantDayButton);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(bottompanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // deal with event listeners
        allButton.addActionListener(e -> {
            updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listAll"), "");
            radioGroup.clearSelection();
        });

        thisYearButton.addActionListener(e -> {
            updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listDaysThisYear"), "");
            radioGroup.clearSelection();

        });
        passedThisYearButton.addActionListener(e -> {
            updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listPassedDaysThisYear"),
                    "passedThisYear");
            radioGroup.clearSelection();
        });
        upcomingThisYearButton.addActionListener(e -> {
            updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listUpcomingDaysThisYear"),
                    "upcomingThisYear");
            radioGroup.clearSelection();
        });

        anniversaryButton.addActionListener(e -> {
            if (anniversaryButton.isSelected()) {
                updateList(
                        ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listDaysThisYearOfType?type=a"),
                        "");
            }
        });

        birthdayButton.addActionListener(e -> {
            if (birthdayButton.isSelected()) {
                updateList(
                        ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listDaysThisYearOfType?type=b"),
                        "");
            }
        });

        occasionButton.addActionListener(e -> {
            if (occasionButton.isSelected()) {
                updateList(
                        ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listDaysThisYearOfType?type=o"),
                        "");
            }
        });

        addImportantDayButton.addActionListener(e -> showAddImportantDayDialog());

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ImportantDaysTracker.exit();
                frame.dispose();
            }

        });
    }

    /**
     * Updates the list in the JScrollPane
     *
     * @param importantDays The new array list to be displayed
     * @param option        Option to specify the message when list is empty
     */
    private void updateList(ArrayList<ImportantDay> importantDays, String option) {
        listOfDaysPanel.removeAll();
        int index = 1;

        if (importantDays.size() == 0) {
            JPanel noItemsPanel = new JPanel();
            noItemsPanel.setLayout(new BorderLayout());
            noItemsPanel.setMaximumSize(new Dimension((Integer.MAX_VALUE), 100));

            JLabel noItems;

            if (option.equals("passedThisYear")) {
                noItems = new JLabel("No passed important days to show.",
                        SwingConstants.CENTER);
            } else if (option.equals("upcomingThisYear")) {
                noItems = new JLabel("No upcoming important days to show.",
                        SwingConstants.CENTER);
            } else {
                noItems = new JLabel("No items to show.", SwingConstants.CENTER);
            }

            noItems.setHorizontalAlignment(SwingConstants.CENTER);

            noItemsPanel.add(noItems, BorderLayout.CENTER);
            listOfDaysPanel.add(noItemsPanel);
        }

        for (ImportantDay importantDay : importantDays) {
            JPanel dayPanel = new JPanel();
            dayPanel.setLayout(new BorderLayout());
            dayPanel.setMaximumSize(new Dimension((Integer.MAX_VALUE), 100));
            dayPanel.setBorder(BorderFactory.createTitledBorder("Important Day #" +
                    index));
            index++;

            JTextArea dayDescPanel = new JTextArea(importantDay.toString());
            dayDescPanel.setEditable(false);

            // deal with the remove button
            JButton removeButton = new JButton("Remove");
            removeButton.addActionListener(e -> {
                ImportantDaysTracker.removeImportantDay(importantDay);
                updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listAll"), "");
            });

            // add all components to their main panels
            dayPanel.add(dayDescPanel, BorderLayout.CENTER);
            dayPanel.add(removeButton, BorderLayout.EAST);
            listOfDaysPanel.add(dayPanel);
        }

        listOfDaysPanel.revalidate();
        listOfDaysPanel.repaint();
    }

    /**
     * Method that shows the dialog box to add important day.
     */
    private void showAddImportantDayDialog() {
        JDialog dialog = new JDialog();
        dialog.setTitle("Add an Important Day");
        dialog.setSize(400, 400);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel typeLabel = new JLabel("Type:");
        JComboBox<String> typeComboBox = new JComboBox<>(new String[] {
                "Anniversary", "Birthday", "Occasion" });
        typeComboBox.setSelectedIndex(0); // choose anniv as default

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel dateLabel = new JLabel("Date:");
        DatePicker datePicker = new DatePicker();
        DatePickerSettings dateField = datePicker.getSettings();
        dateField.setAllowEmptyDates(false);
        dateField.setDateRangeLimits(LocalDate.of(1900, 1, 1), LocalDate.of(2100, 12,
                31));

        JLabel descriptionLabel = new JLabel("Description:");
        JTextField descriptionField = new JTextField();

        JLabel extraInfoLabel = new JLabel("Location:");
        JTextField extraInfoField = new JTextField();

        // Bottom buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        panel.add(typeLabel);
        panel.add(typeComboBox);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(dateLabel);
        panel.add(datePicker);
        panel.add(descriptionLabel);
        panel.add(descriptionField);
        panel.add(extraInfoLabel);
        panel.add(extraInfoField);

        // panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(cancelButton);

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);

        typeComboBox
                .addActionListener(e -> updateExtraField(typeComboBox.getSelectedItem().toString(), extraInfoLabel));

        addButton.addActionListener(e -> {
            String name = nameField.getText();
            LocalDate date = datePicker.getDate();
            String desc = descriptionField.getText();
            String extraInfo = extraInfoField.getText();
            int frequency = 1;

            char type;
            switch ((String) typeComboBox.getSelectedItem()) {
                case "Anniversary":
                    type = 'a';
                    break;
                case "Birthday":
                    type = 'b';
                    break;
                case "Occasion":
                    type = 'o';
                    if (!extraInfo.isBlank()) {
                        frequency = Integer.valueOf(extraInfo);
                    }
                    break;
                default:
                    type = 'z';
                    break;
            }

            // Validation
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name is empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (date == null || date.isBefore(LocalDate.of(1900, 1, 1))) {
                JOptionPane.showMessageDialog(dialog, "Date must be after 1900", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (extraInfo.isBlank() && type == 'o') {
                JOptionPane.showMessageDialog(dialog, "Frequency cannot be empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (frequency < 1) {
                JOptionPane.showMessageDialog(dialog, "Frequency must be more than 1",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (extraInfo.isBlank() && type == 'b') {
                JOptionPane.showMessageDialog(dialog, "Name cannot be empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (extraInfo.isBlank() && type == 'a') {
                JOptionPane.showMessageDialog(dialog, "Location cannot be empty", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean addDaySuccess = ImportantDaysTracker.addImportantDay(name, date, desc, type, frequency, extraInfo);

            // Show success msg
            if (addDaySuccess) {
                JOptionPane.showMessageDialog(dialog, "Important day added successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(dialog, "Failed to add important day!", "Failed",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            updateList(ImportantDaysTracker.getArrayListFromURL("http://localhost:8080/listAll"), "");
            dialog.dispose();
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }

    /**
     * Method that updates the extra field description/prompt according to the type
     * of day.
     */
    private void updateExtraField(String type, JLabel extraInfoLabel) {
        if (type.equals("Anniversary")) {
            extraInfoLabel.setText("Location:");
        } else if (type.equals("Birthday")) {
            extraInfoLabel.setText("Birthday Name:");
        } else if (type.equals("Occasion")) {
            extraInfoLabel.setText("Frequency:");
        }
    }
}
