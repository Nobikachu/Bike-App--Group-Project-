package Data;

import Model.Exportable;
import javafx.collections.ObservableSet;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Provides methods to write to an output CSV file a list of CSVRecords. Requires a list of Exportable objects.
 */
public class CSVWriter {

    //Delimiter used in CSV file
    private static final String NEW_LINE_SEPARATOR = "\n";

    /**
     * Writes a list of Exportable objects to a given CSV file.
     *
     * @param directoryPath The path to the output file.
     * @param fileHeader    The CSV header to go at the top of the file.
     * @param objects       A collection of Exportable objects to be written to the file.
     */
    public static void writeCSV(String directoryPath, String[] fileHeader, ObservableSet<? extends Exportable> objects) {
        FileWriter fileWriter;
        CSVPrinter csvFilePrinter;

        //Create the CSVFormat object with "\n" as a record delimiter
        CSVFormat csvFileFormat = CSVFormat.EXCEL.withRecordSeparator(NEW_LINE_SEPARATOR);

        try {

            //initialize FileWriter object
            fileWriter = new FileWriter(directoryPath);

            //initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);

            //Create CSV file header
            csvFilePrinter.printRecord((Object[]) fileHeader);

            for (Exportable object : objects) {
                //System.out.print("Object to CSV: ");
                //System.out.println(object.toCSV());
                csvFilePrinter.printRecord(object.toCSV());
            }

            fileWriter.flush();
            fileWriter.close();
            csvFilePrinter.close();

        } catch (IOException e) {
            System.out.println("ERROR: An error occurred while writing to the output file " + directoryPath);
        }
    }
}
