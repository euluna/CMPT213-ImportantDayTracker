// Euluna Gotami (301635546)
// CMPT 213 - Assignment 2
// 21/03/2025

package cmpt213.assignment.importantdaystracker.webappserver.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that stores an occasion with its name, date, description, and
 * frequency.
 */
public class Occasion extends ImportantDay {
    /**
     * Constructor for Occasion
     * 
     * @param name        name of the occasion
     * @param date        date of the occasion
     * @param description description of the occasion
     * @param frequency   frequency of the occasion
     */
    public Occasion(String name, LocalDate date, String description, int frequency) {
        super(name, date, description, 'o', frequency);
    }

    /**
     * Returns a string description for the Occasion
     * 
     * @return string description of the Occasion
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        String dateString = getName() + " on " + getDate().format(formatter) + "\n" + getDescription();
        String extraInfoDesc = "Frequency: every " + getFrequency() + " years";
        return "Important Day Type: Occasion\n" + dateString + "\n" + extraInfoDesc;
    }
}