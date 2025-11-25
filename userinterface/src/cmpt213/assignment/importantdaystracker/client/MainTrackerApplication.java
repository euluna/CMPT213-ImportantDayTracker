package cmpt213.assignment.importantdaystracker.client;

import javax.swing.SwingUtilities;
import cmpt213.assignment.importantdaystracker.view.GUI;

/**
 * The main applicaton class for the Important Day Tracker
 */
public class MainTrackerApplication {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GUI();
        });
    }
}