// Euluna Gotami (301635546)
// CMPT 213 - Assignment 2
// 21/03/2025

package cmpt213.assignment.importantdaystracker.webappserver.model;

import java.time.*;
import java.util.Objects;

/**
 * Class comparedObj stores an important dat with its name, date, and
 * description.
 * Implements Comparable to handle sorting by date.
 */
public abstract class ImportantDay implements Comparable<ImportantDay> {
    private String name;
    private LocalDate date;
    private String description;
    private char type;
    private int frequency;

    /**
     * Constructs the initial ImportantDay
     * 
     * @param name        the name of the important day
     * @param date        the date of the important day
     * @param description a description of the important day
     * @param type        the type of the important day
     * @param frequency   the frequency of the important day
     */
    public ImportantDay(String name, LocalDate date, String description, char type, int frequency) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty.");
        }

        if (type != 'a' && type != 'b' && type != 'o') {
            throw new IllegalArgumentException("Type invalid.");
        }

        this.name = name;
        this.date = date;
        this.description = description;
        this.type = type;

        // set default frequency to 1 if it's less than 1
        if (frequency < 1) {
            this.frequency = 1;
        } else {
            this.frequency = frequency;
        }
    }

    @Override
    public abstract String toString();

    /**
     * Compares this important day with another for sorting.
     * 
     * @return a negative if this date is less, zero is they're the same, positive
     *         if this date is greater.
     */
    @Override
    public int compareTo(ImportantDay other) {
        return this.date.compareTo(other.date);
    }

    /**
     * Gets the name of the important day
     * 
     * @return the name of the important day
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the date of the important day
     * 
     * @return the date of the important day
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Gets the frequency of the important day
     * 
     * @return the frequency of the important day
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Gets the type of the important day
     * 
     * @return the type of the important day
     */
    public char getType() {
        return this.type;
    }

    /**
     * Gets the description of the important day
     * 
     * @return the description of the important day
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Overrides the equals method for the important day. It considers two important
     * days to be the same if they have the same name, type, frequency, description,
     * and date. It does not care about the extra info.
     * 
     * It is used to find specific important day for the remove method.
     * 
     * @return returns true if they are equal, and false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        ImportantDay comparedObj = (ImportantDay) obj;

        return type == comparedObj.type &&
                frequency == comparedObj.frequency &&
                Objects.equals(name, comparedObj.name) &&
                Objects.equals(description, comparedObj.description) &&
                Objects.equals(date, comparedObj.date);
    }
}