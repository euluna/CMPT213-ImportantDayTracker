// Euluna Gotami (301635546)

package cmpt213.assignment.importantdaystracker.webappserver.control;

import cmpt213.assignment.importantdaystracker.webappserver.model.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.stream.*;

/**
 * The class that manages the important days.
 */
public class ImportantDaysTracker {
    private static ArrayList<ImportantDay> importantDays = new ArrayList<>();

    /**
     * Constructor for the important days tracker
     */
    public ImportantDaysTracker() {
        loadImportantDays();
    }

    /**
     * Loads the important dates in from the JSON file
     */
    private static void loadImportantDays() {
        File input = new File("./list.json");
        try {
            JsonArray jsonArrayOfDates = JsonParser.parseReader(new FileReader(input)).getAsJsonArray();

            for (JsonElement dateElement : jsonArrayOfDates) {
                // Get the JsonObject
                JsonObject dateJsonObject = dateElement.getAsJsonObject();

                // Extract data
                String name = dateJsonObject.get("name").getAsString();
                String dateString = dateJsonObject.get("date").getAsString();
                LocalDate date = LocalDate.parse(dateString);
                String description = dateJsonObject.get("description").getAsString();
                char type = dateJsonObject.get("type").getAsString().charAt(0);
                int frequency = dateJsonObject.get("frequency").getAsInt();
                String extraInfo = "";
                if (dateJsonObject.has("location")) {
                    extraInfo = dateJsonObject.get("location").getAsString();
                } else if (dateJsonObject.has("bdayName")) {
                    extraInfo = dateJsonObject.get("bdayName").getAsString();
                }

                ImportantDay day = ImportantDayFactory.getInstance(String.valueOf(type), name, date, description,
                        frequency, extraInfo);
                importantDays.add(day);
            }

        } catch (FileNotFoundException e) {
            // Do nothing if file does not exist
        } catch (Exception e) {
            System.err.println("Error processing input file!");
            e.printStackTrace();
        }
    }

    /**
     * Saves all the important dates in the list to a JSON file.
     * 
     * @return returns true if successfully saves, and false if not.
     */
    public static boolean saveImportantDays() {
        Gson myGson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new TypeAdapter<LocalDate>() {
            @Override
            public void write(JsonWriter jsonWriter, LocalDate localDate) throws IOException {
                jsonWriter.value(localDate.toString());
            }

            @Override
            public LocalDate read(JsonReader jsonReader) throws IOException {
                return LocalDate.parse(jsonReader.nextString());
            }
        }).create();

        try (FileWriter writer = new FileWriter("./list.json")) {
            myGson.toJson(importantDays, writer);
            return true;
        } catch (IOException e) {
            System.out.println("Error while saving.");
            return false;
        }
    }

    /**
     * Removes an important day from the list
     * 
     * @param day The importantDay object to be removed
     */
    public void removeImportantDay(ImportantDay day) {
        importantDays.remove(day);
    }

    /**
     * Returns the array list of important days
     * 
     * @return Returns the importan days array list
     */
    public ArrayList<ImportantDay> getImportantDays() {
        return importantDays;
    }

    /**
     * Filters the list to only include important days in the current year
     * 
     * @return Returns the filtered array list
     */
    public ArrayList<ImportantDay> getImportantDaysThisYear() {
        ArrayList<ImportantDay> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (ImportantDay importantDay : importantDays) {
            // birthdays and anniv happen every year
            if (importantDay.getType() == 'a' || importantDay.getType() == 'b') {
                filteredList.add(importantDay);
            } else {
                // need to filter occasions
                // check if current year included
                if ((today.getYear() - importantDay.getDate().getYear()) % importantDay.getFrequency() == 0) {
                    filteredList.add(importantDay);
                }
            }
        }

        return filteredList;
    }

    /**
     * Filters the list to only include the passed important days this year
     * 
     * @return Returns the filtered array list
     */
    public ArrayList<ImportantDay> getPassedImportantDaysThisYear() {
        ArrayList<ImportantDay> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Goes through entire list of important days
        for (ImportantDay importantDay : importantDays) {
            LocalDate tempDate = importantDay.getDate();

            // Check if current year has important day
            if ((today.getYear() - tempDate.getYear()) % importantDay.getFrequency() == 0) {
                // Check if the important day has already passed
                if (tempDate.getMonthValue() < today.getMonthValue()
                        || (tempDate.getMonthValue() == today.getMonthValue()
                                && tempDate.getDayOfMonth() < today.getDayOfMonth())) {
                    filteredList.add(importantDay);
                }
            }
        }

        return filteredList;
    }

    /**
     * Filters the list to only include the upcoming important days this year
     * 
     * @return Returns the filtered array list
     */
    public ArrayList<ImportantDay> getUpcomingImportantDaysThisYear() {
        ArrayList<ImportantDay> filteredList = new ArrayList<>();
        LocalDate today = LocalDate.now();

        // Goes through entire list of important days
        for (ImportantDay importantDay : importantDays) {
            LocalDate tempDate = importantDay.getDate();

            // Check if current year has important day
            if ((today.getYear() - tempDate.getYear()) % importantDay.getFrequency() == 0) {
                if (tempDate.getMonthValue() > today.getMonthValue()
                        || (tempDate.getMonthValue() == today.getMonthValue()
                                && tempDate.getDayOfMonth() >= today.getDayOfMonth())) {
                    filteredList.add(importantDay);
                }
            }
        }

        return filteredList;
    }

    /**
     * Filters the list to only include the days of a certain type happening in this
     * year
     * 
     * @param type The type of important day to filter
     * @return Returns the filtered array list
     */
    public ArrayList<ImportantDay> getImportantDaysOfType(char type) {
        ArrayList<ImportantDay> importantDaysThisYear = getImportantDaysThisYear();
        ArrayList<ImportantDay> filteredList = new ArrayList<>();

        for (ImportantDay importantDay : importantDaysThisYear) {
            if (importantDay.getType() == type) {
                filteredList.add(importantDay);
            }
        }

        return filteredList;
    }

    /**
     * Adds an important day to the array list
     * 
     * @param name        name of the important day
     * @param date        date of the important day
     * @param description description of the important day
     * @param type        type of the important day
     * @param frequency   frequency of the important day
     * @param extraInfo   extra info for the important day depending on type. (name
     *                    of birthday person/ location of anniv)
     */
    public void addImportantDay(String name, LocalDate date, String description, char type, int frequency,
            String extraInfo) {
        // Create the important day and add it to the list
        ImportantDay importantDay = ImportantDayFactory.getInstance(String.valueOf(type), name, date, description,
                frequency, extraInfo);
        importantDays.add(importantDay);

        // Keeps the list sorted
        Collections.sort(importantDays);
    }
}
