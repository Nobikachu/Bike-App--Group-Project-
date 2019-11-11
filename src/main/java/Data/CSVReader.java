package Data;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;


/**
 * A program which reads the contents of an external CSV file, and returns the relevant data contained inside.
 */
public class CSVReader {
    private Reader in;


    /**
     * Reads the data within the CSV and returns it as a list which can then be used by other programs.
     *
     * @param fileName The CSV file being read.
     * @return A list of data within the CSV file.
     */
    public CSVParser readFile(String fileName) {
        try {
            // Attempt to open the CSV and return a list of records
            in = new FileReader(fileName);
            return CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
        } catch (FileNotFoundException e) {
            // Warn the user if the given file is not found or can't be read
            errorDialog("File not found!\nYou may not have sufficient permissions to view this file.");
            return null;
        } catch (IOException e) {
            // Warn the user if an unexpected error occurred
            errorDialog("File cannot be read!");
            return null;
        }
    }

    /**
     * Terminates the reader.
     */
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            errorDialog("An error occurred when trying to close the file.");
        }
    }

    /**
     * Alerts the user to an IOError
     *
     * @param message the message to be presented to the user
     */
    private void errorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.showAndWait();
    }
}
