// Euluna Gotami (301635546)
// CMPT 213 - Assignment 2
// 21/03/2025

package cmpt213.assignment.importantdaystracker.webappserver.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that stores an anniversary with its name, date, description, frequency,
 * and location.
 */
public class Anniversary extends ImportantDay {
    private String location;

    /**
     * Constructor for Anniversary
     * 
     * @param name        name of the anniversary
     * @param date        date of the anniversary
     * @param description description of the anniversary
     * @param frequency   frequency of the anniversary
     * @param location    location of the anniversary
     */
    public Anniversary(String name, LocalDate date, String description, int frequency, String location) {
        super(name, date, description, 'a', frequency);
        this.location = location;
    }

    /**
     * Returns a string description for the Anniversary
     * 
     * @return string description of the Anniversary
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        String dateString = getName() + " on " + getDate().format(formatter) + "\n" + getDescription();
        String extraInfoDesc = "Located at: " + location;
        return "Important Day Type: Anniversary\n" + dateString + "\n" + extraInfoDesc;
    }

    /**
     * Get the location of anniversary
     * 
     * @return Returns the location of the anniversary
     */
    public String getLocation() {
        return this.location;
    }

}