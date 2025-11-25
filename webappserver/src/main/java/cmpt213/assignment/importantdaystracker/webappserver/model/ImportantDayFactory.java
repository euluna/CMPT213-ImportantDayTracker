// Euluna Gotami (301635546)
// CMPT 213 - Assignment 2
// 21/03/2025

package cmpt213.assignment.importantdaystracker.webappserver.model;

import java.time.LocalDate;

/**
 * Factory class that creates an instance of an ImportantDay based on the type
 */
public class ImportantDayFactory {
    /**
     * Returns an instance of an ImportantDay based on the type
     * 
     * @param type        the type of ImportantDay
     * @param name        the name of the ImportantDay
     * @param date        the date of the ImportantDay
     * @param description the description of the ImportantDay
     * @param frequency   the frequency of the ImportantDay
     * @param extraInfo   the extra information of the ImportantDay
     *                    (location/person's name)
     * @return an instance of an ImportantDay
     */
    public static ImportantDay getInstance(String type, String name, LocalDate date, String description, int frequency,
            String extraInfo) {
        switch (type) {
            case "a":
                return new Anniversary(name, date, description, frequency, extraInfo);
            case "b":
                return new Birthday(name, date, description, frequency, extraInfo);
            case "o":
                return new Occasion(name, date, description, frequency);
            default:
                throw new IllegalArgumentException("Invalid type");
        }
    }
}