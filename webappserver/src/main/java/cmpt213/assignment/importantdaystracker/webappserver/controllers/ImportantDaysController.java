package cmpt213.assignment.importantdaystracker.webappserver.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import cmpt213.assignment.importantdaystracker.webappserver.control.ImportantDaysTracker;
import cmpt213.assignment.importantdaystracker.webappserver.model.ImportantDay;
import cmpt213.assignment.importantdaystracker.webappserver.model.ImportantDayFactory;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controller class for the important days web application.
 */
@RestController
public class ImportantDaysController {
    private final ImportantDaysTracker tracker = new ImportantDaysTracker();

    /**
     * Endpoint to check if the server is running.
     * 
     * @return returns string saying the system is up.
     */
    @GetMapping("/ping")
    public String ping() {
        return "System is up!";
    }

    /**
     * Endpoint to add a new important day to the list.
     * 
     * @param dayData takes in a map with the details of the important day.
     * @return Returns the updated list.
     */
    @PostMapping("/addDay")
    public List<ImportantDay> addDay(@RequestBody Map<String, Object> dayData) {
        String name = (String) dayData.get("name");
        String description = (String) dayData.get("description");
        String dateString = (String) dayData.get("date");
        String typeString = (String) dayData.get("type");
        Integer frequency = (Integer) dayData.get("frequency");
        String extraInfo = (String) dayData.get("extraInfo");

        // Validate fields
        if (name == null) {
            System.out.println("Name is mising.");
            return tracker.getImportantDays();
        }

        if (dateString == null) {
            System.out.println("Date missing.");
            return tracker.getImportantDays();
        }

        LocalDate date = LocalDate.parse(dateString);
        char type = typeString.charAt(0);

        tracker.addImportantDay(name, date, description, type, frequency, extraInfo);

        return tracker.getImportantDays();
    }

    /**
     * Endpoint to remove an important day from the list.
     * 
     * @param dayData takes in a map with the details of the day to be removed.
     * @return Returns the updated list.
     */
    @PostMapping("/removeDay")
    public List<ImportantDay> removeDay(@RequestBody Map<String, Object> dayData) {
        String name = (String) dayData.get("name");
        String description = (String) dayData.get("description");
        String dateString = (String) dayData.get("date");
        String typeString = (String) dayData.get("type");
        String freqString = (String) dayData.get("frequency");

        // Validate required fields
        if (name == null || dateString == null || typeString == null || freqString == null) {
            System.out.println("Failed to remove. Missing fields.");
            return tracker.getImportantDays();
        }

        try {
            // Parse fields
            LocalDate date = LocalDate.parse(dateString);
            int frequency = Integer.parseInt(freqString);
            char type = typeString.charAt(0);

            // Create a temporary ImportantDay object for comparison
            ImportantDay dayToRemove = ImportantDayFactory.getInstance(
                    String.valueOf(type), name, date, description != null ? description : "", frequency, "");

            // Remove the day
            tracker.removeImportantDay(dayToRemove);

        } catch (Exception e) {
            System.out.println("Error while removing day.");
            return tracker.getImportantDays();
        }

        return tracker.getImportantDays();
    }

    /**
     * Endpoint to save the list of the important days to a txt file
     * 
     * @return A string saying that it has saved successfully.
     */
    @GetMapping("/exit")
    public String exit() {
        boolean success = ImportantDaysTracker.saveImportantDays();

        if (success) {
            System.out.println("Saved.");
            return "Successfully saved.";
        } else {
            System.out.println("Failed to save.");
            return "Failed to save.";
        }
    }

    /**
     * Endpoint to list all important days
     * 
     * @return Returns a list of all the important days
     */
    @GetMapping("/listAll")
    public List<ImportantDay> listAll() {
        return tracker.getImportantDays();
    }

    /**
     * Endpoint to list all passed important days in this year.
     * 
     * @return Returns a list of all the passed important days this year.
     */
    @GetMapping("/listPassedDaysThisYear")
    public List<ImportantDay> getPassedImportantDaysThisYear() {
        return tracker.getPassedImportantDaysThisYear();
    }

    /**
     * Endpoint to list all upcoming important days in this year.
     * 
     * @return Returns a list of all the upcoming important days this year.
     */
    @GetMapping("/listUpcomingDaysThisYear")
    public List<ImportantDay> getUpcomingImportantDays() {
        return tracker.getUpcomingImportantDaysThisYear();
    }

    /**
     * Endpoint to list important days this year of a certain type.
     * 
     * @param type The type of important day to filter with.
     * @return Returns a list of all important days of a certain type this year.
     */
    @GetMapping("/listDaysThisYearOfType")
    public List<ImportantDay> getImportantDaysOfType(@RequestParam char type) {
        return tracker.getImportantDaysOfType(type);
    }

    /**
     * Endpoint to list all important days this year.
     * 
     * @return Returns a list of all important days this year.
     */
    @GetMapping("/listDaysThisYear")
    public List<ImportantDay> getImportantDaysThisYear() {
        return tracker.getImportantDaysThisYear();
    }
}
