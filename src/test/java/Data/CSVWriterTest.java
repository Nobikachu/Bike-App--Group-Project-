package Data;

import Model.WiFi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * JUnit Testing foir the CSVWriter Class
 */
public class CSVWriterTest extends TestCase {
    WiFi wifi1, wifi2, wifi3;
    ObservableList<WiFi> wiFiObservableList = FXCollections.observableArrayList();
    ObservableSetList<WiFi> wiFiObservableSetList;
    CSVReader csvReader;

    /**
     * Setting up some new WiFi classes to test the functionality of CSVWriter class.
     *
     * @throws Exception Setup was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        CSVReader reader = new CSVReader();
        List<CSVRecord> records = reader.readFile((new File("src/test/resources/Data/testWifi.csv")).getAbsolutePath()).getRecords();
        wifi1 = new WiFi(records.get(0));
        wifi2 = new WiFi(records.get(1));
        wifi3 = new WiFi(records.get(2));
        reader.close();
        wiFiObservableList.addAll(wifi1, wifi2, wifi3);
        csvReader = new CSVReader();

    }

    /**
     * Checks if the file has been exported as csv in a correct format
     *
     * @throws IOException CSV writer exported as csv in a incorrect format.
     */
    public void testWriteFile() throws IOException {
        File file = File.createTempFile("testexport", ".csv", new File("src/test/resources/Data/"));
        file.deleteOnExit();
        wiFiObservableSetList = new ObservableSetList<>(wiFiObservableList);
        CSVWriter.writeCSV(file.getAbsolutePath(), DataManager.wifiHeaders, wiFiObservableSetList.getSet());
        CSVParser check = csvReader.readFile("src/test/resources/Data/testExportCheck.csv");
        CSVParser fileCsv = csvReader.readFile(file.getAbsolutePath());
        assertEquals(check.getRecords().toString(), fileCsv.getRecords().toString());
    }


}