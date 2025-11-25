// Euluna Gotami (301635546)
// CMPT 213 - Assignment 2
// 21/03/2025

package cmpt213.assignment.importantdaystracker.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Class that stores a birthday with its name, date, description, frequency, and
 * person's name.
 */
public class Birthday extends ImportantDay {
    private String bdayName;

    /**
     * Constructor for Birthday
     * 
     * @param name        name of the birthday
     * @param date        date of the birthday
     * @param description description of the birthday
     * @param frequency   frequency of the birthday
     * @param bdayName    name of the person celebrating the birthday
     */
    public Birthday(String name, LocalDate date, String description, int frequency, String bdayName) {
        super(name, date, description, 'b', frequency);
        this.bdayName = bdayName;
    }

    /**
     * Returns a string description for the Birthday
     * 
     * @return string description of the Birthday
     */
    @Override
    public String toString() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy MMM dd");
        String dateString = getName() + " on " + getDate().format(formatter) + "\n" + getDescription();
        String extraInfoDesc = "Note: " + bdayName + " is turning " + (today.getYear() - getDate().getYear())
                + " year(s) old.";
        return "Important Day Type: Birthday\n" + dateString + "\n" + extraInfoDesc;
    }

    /**
     * gets the name of the birthday person
     * 
     * @return returns the name of the person
     */
    public String getBdayName() {
        return this.bdayName;
    }

    /**
     * gets the name of the birthday person
     * 
     * @return returns the name of the person
     */
    public String getExtraInfo() {
        return this.getBdayName();
    }
}