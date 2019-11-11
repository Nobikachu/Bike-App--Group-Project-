package Data;

import Model.WiFi;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import junit.framework.TestCase;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Testing for the ObservableSetList Class
 */
public class ObservableSetListTest extends TestCase {

    ObservableSetList<WiFi> observableSetList;
    WiFi wifi1, wifi2, wifi3;

    /**
     * Setting up some new WiFi classes to test the functionality of ObservableSetList class.
     *
     * @throws Exception Setup was unsuccessful
     */
    public void setUp() throws Exception {
        super.setUp();
        ObservableList<WiFi> wiFiObservableList = FXCollections.observableArrayList();
        CSVReader reader = new CSVReader();
        List<CSVRecord> records = reader.readFile((new File("src/test/resources/Data/testWifi.csv")).getAbsolutePath()).getRecords();
        wifi1 = new WiFi(records.get(0));
        wifi2 = new WiFi(records.get(1));
        wifi3 = new WiFi(records.get(2));
        wiFiObservableList.addAll(wifi1, wifi2, wifi3);
        observableSetList = new ObservableSetList<>(wiFiObservableList);
    }

    /**
     * Checks if the ObservableSet retrieve the correct data.
     *
     * @throws Exception Observableset retrieve incorrect data.
     */
    public void testGetSet() throws Exception {
        ObservableSet<WiFi> wiFiSet = observableSetList.getSet();
        ObservableSet<WiFi> wiFiCheck = FXCollections.observableSet();
        wiFiCheck.add(wifi1);
        wiFiCheck.add(wifi2);
        wiFiCheck.add(wifi3);
        assertEquals(wiFiSet, wiFiCheck);

    }

    /**
     * Checks if the ObservableList retrieve the correct data.
     *
     * @throws Exception ObservableList retrieve the incorrect data.
     */
    public void testGetList() throws Exception {
        ObservableList<WiFi> wiFiCheck = FXCollections.observableArrayList();
        wiFiCheck.addAll(wifi1, wifi2, wifi3);
        ObservableList<WiFi> wiFiObservableList = observableSetList.getList();
        assertEquals(wiFiCheck, wiFiObservableList);
    }

    /**
     * Tests that the addAll method adds to both the list and the set
     *
     * @throws Exception error
     */
    public void testAddAll() throws Exception {
        assertTrue(observableSetList.getList().containsAll(Arrays.asList(wifi1, wifi2, wifi3)));

    }

    public void testRemove() throws Exception {
        observableSetList.remove(wifi2);
        assertFalse(observableSetList.getSet().contains(wifi2));
        assertFalse(observableSetList.getList().contains(wifi2));

        assertTrue(observableSetList.getSet().contains(wifi1));
        assertTrue(observableSetList.getList().contains(wifi3));
    }

}