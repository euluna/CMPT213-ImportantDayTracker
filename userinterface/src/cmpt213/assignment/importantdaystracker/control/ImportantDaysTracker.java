// Euluna Gotami (301635546)

package cmpt213.assignment.importantdaystracker.control;

import cmpt213.assignment.importantdaystracker.model.*;
import java.time.LocalDate;
import java.util.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import com.google.gson.*;

/**
 * The class that manages the important days. It sends requests to the web
 * application. It does not manage the list locally.
 */
public class ImportantDaysTracker {

    /**
     * Constructor for the important days tracker
     */
    public ImportantDaysTracker() {
        System.out.println(getArrayListFromURL("http://localhost:8080/listAll"));
    }

    /**
     * Sends a remove request to the web application
     * 
     * @param importantDay Important day to be removed
     * @return Returns a true if successfully removes, and false if it fails
     */
    public static boolean removeImportantDay(ImportantDay importantDay) {
        try {
            URL url = URI.create("http://localhost:8080/removeDay").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Construct JSON payload
            String jsonPayload = String.format(
                    "{\"name\":\"%s\",\"description\":\"%s\",\"date\":\"%s\",\"type\":\"%s\",\"frequency\":\"%d\"}",
                    importantDay.getName(),
                    importantDay.getDescription() != null ? importantDay.getDescription() : "",
                    importantDay.getDate(),
                    String.valueOf(importantDay.getType()),
                    importantDay.getFrequency());

            // Send JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED
                    || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error processing the JSON!");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Sends a request to the webapplication to trigger it to save the list to a txt
     * file.
     */
    public static void exit() {
        try {
            URL url = URI.create("http://localhost:8080/exit").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Check response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_NO_CONTENT) {
                System.out.println("Exit success");
            } else {
                System.out.println("Failed to exit");
            }

        } catch (Exception e) {
            System.err.println("Error sending exit request!");
            e.printStackTrace();
        }
    }

    /**
     * Constructs a JSON of the important day and sends it to the webserver to be
     * added to the list.
     *
     * @param name        name of the important day
     * @param date        date of the important day
     * @param description description of the important day
     * @param type        type of the important day
     * @param frequency   frequency of the important day
     * @param extraInfo   extra info for the important day depending on type. (name
     *                    of birthday person/ location of anniv)
     * @return Returns true if successfully adds date, and false if not.
     */
    public static boolean addImportantDay(String name, LocalDate date, String description, char type, int frequency,
            String extraInfo) {
        try {
            URL url = URI.create("http://localhost:8080/addDay").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true); // default is false. need to specify true.

            // Construct JSON payload
            String jsonPayload = String.format(
                    "{\"name\":\"%s\",\"description\":\"%s\",\"date\":\"%s\",\"type\":\"%c\",\"frequency\":%d,\"extraInfo\":\"%s\"}",
                    name, description, date, type, frequency, extraInfo);

            // Send JSON payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Check response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            System.err.println("Error processing the JSON!");
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Sends a request to the web application to get a list from the specified URL
     * in JSON and converts it to an arraylist. It then returns that list.
     * 
     * @param urlString The URL to get the list from
     * @return Returns the list as an array list
     */
    public static ArrayList<ImportantDay> getArrayListFromURL(String urlString) {
        ArrayList<ImportantDay> importantDays = new ArrayList<>();

        try {
            URL url = URI.create(urlString).toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            // Check if the response code is ok
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK
                    || connection.getResponseCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                // Read the response
                InputStreamReader reader = new InputStreamReader(connection.getInputStream());
                JsonArray jsonArrayOfDates = JsonParser.parseReader(reader).getAsJsonArray();

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

                reader.close();
            } else {
                System.out.println(
                        "Failed to fetch data from the server. HTTP response code: " + connection.getResponseCode());
            }

            connection.disconnect();
        } catch (Exception e) {
            System.err.println("Error processing the JSON!");
            e.printStackTrace();
        }

        return importantDays;
    }
}