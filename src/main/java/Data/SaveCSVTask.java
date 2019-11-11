package Data;

import GUI.RawDataViewerController;
import Model.BikeStation;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashMap;

/**
 * Task that will be concurrently threaded to save all the values currently in the ListViews into the
 * appropriate CSV files in the corresponding folder directories. The pop up showing the progress bar is
 * closed at the end of saving all the files.
 */
class SaveCSVTask extends Task {
    private Stage savingStage;
    private String retailerFolderDirectory;
    private String routeFolderDirectory;
    private String wifiFolderDirectory;
    private String bikeStationFolderDirectory;
    private String favoriteFolderDirectory;

    /**
     * Pass the values to the task so the values can be saved to the CSV files to the appropriate directories.
     *
     * @param stage              The stage containing the pop up that needs to be closed after the files have been saved.
     * @param retailerFolderD    The folder directory where all the retailer CSV's are to be saved.
     * @param routeFolderD       The folder directory where all the route CSV's are to be saved.
     * @param wifiFolderD        The folder directory where all the WiFi CSV's are to be saved.
     * @param bikeStationFolderD The folder directory where all the Bike station CSV's are to be saved.
     * @param favoriteFolderD    The folder directory where all the favourited entries are to be saved.
     */
    public SaveCSVTask(Stage stage, String retailerFolderD, String routeFolderD, String wifiFolderD, String bikeStationFolderD, String favoriteFolderD) {
        savingStage = stage;
        retailerFolderDirectory = retailerFolderD;
        routeFolderDirectory = routeFolderD;
        wifiFolderDirectory = wifiFolderD;
        bikeStationFolderDirectory = bikeStationFolderD;
        favoriteFolderDirectory = favoriteFolderD;
    }

    /**
     * Call to save all the files concurrently to the respective folders. At the end of saving all the files
     * the stage is closed.
     *
     * @return null
     */
    @Override
    protected Object call() {
        //Saving all the retailers values
        HashMap<Object, ObservableSetList<Retailer>> retailerCsvFileHashMap = DataManager.getRetailersArrays();
        Object[] retailerCsvNames = retailerCsvFileHashMap.keySet().toArray();
        for (Object retailerCsvNameObject : retailerCsvNames) {
            String retailerCsvName = (String) retailerCsvNameObject;

            String fileName; //The name the file will be saved under
            if (!retailerCsvName.endsWith(".csv")) {
                fileName = retailerCsvName + ".csv"; //If it doesn't have the .csv extension include it
            } else {
                fileName = retailerCsvName;
            }

            String retailerFileDirectory = retailerFolderDirectory + File.separatorChar + fileName;
            ObservableSet<Retailer> retailerValues = retailerCsvFileHashMap.get(retailerCsvName).getSet();
//            System.out.print("Retailer entity values: ");
//            System.out.println(retailerValues);
            CSVWriter.writeCSV(retailerFileDirectory, DataManager.retailerHeaders, retailerValues);
            System.out.println("Create a CSV file for: " + fileName);
        }


        //Saving all the route values
        HashMap<Object, ObservableSetList<Route>> routeCsvFileHashMap = DataManager.getRouteArrays();
        Object[] routeCsvNames = routeCsvFileHashMap.keySet().toArray();
        for (Object routeCsvNameObject : routeCsvNames) {
            String routeCsvName = (String) routeCsvNameObject;
            String fileName; //The name the file will be saved under
            if (!routeCsvName.endsWith(".csv")) {
                fileName = routeCsvName + ".csv"; //If the file doesn't have the .csv extension include it
            } else {
                fileName = routeCsvName;
            }
            String routeFileDirectory = routeFolderDirectory + File.separatorChar + fileName;
            ObservableSet<Route> routeValues = routeCsvFileHashMap.get(routeCsvName).getSet();
            CSVWriter.writeCSV(routeFileDirectory, DataManager.routeHeaders, routeValues);
            System.out.println("Created a CSV file for : " + fileName);
        }


        //Saving all the wifi values
        HashMap<Object, ObservableSetList<WiFi>> wifiCsvFileHashMap = DataManager.getWifiArrays();
        Object[] wifiCsvNames = wifiCsvFileHashMap.keySet().toArray();
        for (Object wifiCsvNameObject : wifiCsvNames) {
            String wifiCsvName = (String) wifiCsvNameObject;
            String fileName; //The name of the file wil lbe saved udner
            if (!wifiCsvName.endsWith(".csv")) {
                fileName = wifiCsvName + ".csv";
            } else {
                fileName = wifiCsvName;
            }

            String wifiFileDirectory = wifiFolderDirectory + File.separatorChar + fileName;
            ObservableSet<WiFi> wifiValues = wifiCsvFileHashMap.get(wifiCsvName).getSet();
            CSVWriter.writeCSV(wifiFileDirectory, DataManager.wifiHeaders, wifiValues);
            System.out.println("Created a CSV file for : " + fileName);
        }

        //Saving all the bike station values
        HashMap<Object, ObservableSetList<BikeStation>> bikeStationCsvFileHashMap = DataManager.getBikeStationArrays();
        Object[] bikeStationCsvNames = bikeStationCsvFileHashMap.keySet().toArray();
        for (Object bikeStationCsvNameObject : bikeStationCsvNames) {
            String bikeStationCsvName = (String) bikeStationCsvNameObject;
            String fileName; //The name the file will be saved under.
            if (!bikeStationCsvName.endsWith(".csv")) {
                fileName = bikeStationCsvName + ".csv";
            } else {
                fileName = bikeStationCsvName;
            }

            String bikeStationFileDirectory = bikeStationFolderDirectory + File.separatorChar + fileName;
            ObservableSet<BikeStation> bikeStationsValues = bikeStationCsvFileHashMap.get(bikeStationCsvName).getSet();
            CSVWriter.writeCSV(bikeStationFileDirectory, DataManager.bikeStationHeaders, bikeStationsValues);
            System.out.println("Created a CSV file for : " + fileName);
        }

        //Save the favourited retailers.
        ObservableList<Retailer> favouritedRetailers = RawDataViewerController.controller.getFavoriteRetailers();
        ObservableSet<Retailer> favouritedRetailerSet = new ObservableSetList<>(favouritedRetailers).getSet();
        String favouriteRetailerFilePath = favoriteFolderDirectory + File.separatorChar + "favouriteRetailers.csv";
        CSVWriter.writeCSV(favouriteRetailerFilePath, DataManager.retailerHeaders, favouritedRetailerSet);
        System.out.println("Created a CSV file for the favourited retailers: " + favouriteRetailerFilePath);

        //Save the favourited routes
        ObservableList<Route> favouritedRoutes = RawDataViewerController.controller.getFavouriteRoutes();
        ObservableSet<Route> favouritedRoutesSet = new ObservableSetList<>(favouritedRoutes).getSet();
        String favouriteRouteFilePath = favoriteFolderDirectory + File.separatorChar + "favouriteRoutes.csv";
        CSVWriter.writeCSV(favouriteRouteFilePath, DataManager.routeHeaders, favouritedRoutesSet);
        System.out.println("Created a CSV File for the favourited routes: " + favouriteRouteFilePath);

        //Save the favourited wifis
        ObservableList<WiFi> favouritedWifis = RawDataViewerController.controller.getFavouriteWifis();
        ObservableSet<WiFi> favouritedWifisSet = new ObservableSetList<>(favouritedWifis).getSet();
        String favouritedWifisFilePath = favoriteFolderDirectory + File.separatorChar + "favouriteWifis.csv";
        CSVWriter.writeCSV(favouritedWifisFilePath, DataManager.wifiHeaders, favouritedWifisSet);
        System.out.println("Created a CSV File for the favourited wifis: " + favouritedWifisFilePath);

        //Save the favourited bike stations
        System.out.println("About to start saving the favourited bike stations.");
        ObservableList<BikeStation> favouritedBikeSetations = RawDataViewerController.controller.getFavouriteBikeStations();
        System.out.print("Favourited bike stations: ");
        System.out.println(favouritedBikeSetations);
        System.out.flush();
        ObservableSet<BikeStation> favouritedBikeStationSet = new ObservableSetList<>(favouritedBikeSetations).getSet();
        String favouritedBikeStationFilePath = favoriteFolderDirectory + File.separatorChar + "favouriteBikeStations.csv";
        CSVWriter.writeCSV(favouritedBikeStationFilePath, DataManager.bikeStationHeaders, favouritedBikeStationSet);
        System.out.println("Created a CSV file for the favourited bike stations: " + favouritedBikeStationFilePath);

        System.out.println("Finished saving all the CSV files.");


        //Have finished saving all the files. Close the pop up. Can only update the UI in the javafx thread
        //hence need to use run later.
        Platform.runLater(() -> savingStage.close());

        return null;
    }

}
