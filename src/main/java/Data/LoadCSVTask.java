package Data;

import GUI.RawDataViewerController;
import Main.Main;
import Model.Retailer;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;

/**
 * Task loads the stored CSV files (if any exist) stored in the specified directories.
 */
class LoadCSVTask extends Task {
    private Stage loadingStage;
    private String retailerFolderDirectory;
    private String routeFolderDirectory;
    private String wifiFolderDirectory;
    private String bikeStationFolderDirectory;
    private String favoriteFolderDirectory;

    /**
     * Constructore is passed the stage used for the loading bar pop up, used to close the window
     * when it has finished loading. Constructor is also passed all the directires to the
     * file locations to load the files.
     *
     * @param stage              Used to close the pop up upon loading completion.
     * @param retailerFolderD    The folder containing retailer CSV's.
     * @param routeFolderD       The folder containing route CSV's.
     * @param wifiFolderD        The folder containing WiFi CSV's.
     * @param bikeStationFolderD The folder containing bike station CSV's.
     * @param favoriteFolderD    The folder containing all the CSV's of the user's favourited retailers, routes etc.
     */
    public LoadCSVTask(Stage stage, String retailerFolderD, String routeFolderD, String wifiFolderD,
                       String bikeStationFolderD, String favoriteFolderD) {
        loadingStage = stage;
        retailerFolderDirectory = retailerFolderD;
        routeFolderDirectory = routeFolderD;
        wifiFolderDirectory = wifiFolderD;
        bikeStationFolderDirectory = bikeStationFolderD;
        favoriteFolderDirectory = favoriteFolderD;
    }


    /**
     * When the task is called, will begin searching each folder and look for CSV to load records from.
     * Each CSV file is then stored in DataManager as a 'list' the user can browse and interact with.
     *
     * @return null
     * @throws Exception Thrown when the file can not be loaded.
     */
    @Override
    protected Object call() throws Exception {

        File retailerDir = new File(retailerFolderDirectory + File.separatorChar);
        String[] everythingInRetailerDir = retailerDir.list();
        if (everythingInRetailerDir != null) {
            for (String fileName : everythingInRetailerDir) {
                //System.out.println("File found in retailer directory: " + fileName);
                if (fileName.endsWith(".csv")) {
                    //System.out.println("Found CSV: " + fileName);
                    //Remove the .csv extension to make it more user friendly, list name displayed on the data tab
                    String listName = fileName.substring(0, fileName.length() - 4);
                    try {
                        ObservableSetList<Retailer> loadedRetailers = DataManager.loadRetailersFromCSV(retailerFolderDirectory + File.separatorChar + fileName);
                        DataManager.getRetailersArrays().put(listName, loadedRetailers);
                    } catch (NullPointerException e) {
                        System.out.println("Could not load CSV file: " + fileName);
                    }
                }

            }
        }


        File routeDir = new File(routeFolderDirectory + File.separatorChar);
        String[] everythingInRouteDir = routeDir.list();
        if (everythingInRouteDir != null) {
            for (String fileName : everythingInRouteDir) {
                //System.out.println("File found in route directory: " + fileName);
                if (fileName.endsWith(".csv")) {
                    //System.out.println("Found CSV: " + fileName);
                    //Remove the .csv extensions to make it more user friendly, list name is displayed in the data tab
                    String listName = fileName.substring(0, fileName.length() - 4);
                    try {
                        ObservableSetList<Model.Route> loadedRoutes = DataManager.loadRoutesFromCSV(routeFolderDirectory + File.separatorChar + fileName);
                        DataManager.getRouteArrays().put(listName, loadedRoutes);
                    } catch (NullPointerException e) {
                        System.out.println("Could not load CSV file: " + fileName);
                    }
                }

            }
        }

        File wifiDir = new File(wifiFolderDirectory + File.separatorChar);
        String[] everythingInWifiDir = wifiDir.list();
        if (everythingInWifiDir != null) {
            for (String fileName : everythingInWifiDir) {
                //System.out.println("File found in wifi directory: " + fileName);
                if (fileName.endsWith(".csv")) {
                    //System.out.println("Found CSV: " + fileName);
                    //Remove the .csv extension to make it more user friendly, list name is diapleyd in the data tab
                    String listName = fileName.substring(0, fileName.length() - 4);
                    try {
                        ObservableSetList<Model.WiFi> loadedWiFi = DataManager.loadWifiFromCSV(wifiFolderDirectory + File.separatorChar + fileName);
                        DataManager.getWifiArrays().put(listName, loadedWiFi);
                    } catch (NullPointerException e) {
                        System.out.println("Could not load CSV file: " + fileName);
                    }
                }
            }
        }

        File bikeStationDir = new File(bikeStationFolderDirectory + File.separatorChar);
        String[] everythingInBikeStationDir = bikeStationDir.list();
        if (everythingInBikeStationDir != null) {
            for (String fileName : everythingInBikeStationDir) {
                System.out.println("File found in bike station directory: " + fileName);
                if (fileName.endsWith(".csv")) {
                    System.out.println("Found CSV: " + fileName);
                    //Remove the .csv extension to make it more user friendly, this will be used when displaying in the list tab
                    String listName = fileName.substring(0, fileName.length() - 4);
                    try {
                        ObservableSetList<Model.BikeStation> loadBikeStations = DataManager.loadBikeStationFromCSV(bikeStationFolderDirectory + File.separatorChar + fileName);
                        DataManager.getBikeStationArrays().put(listName, loadBikeStations);
                    } catch (NullPointerException e) {
                        System.out.println("Could not load CSV file: " + fileName);
                    }

                }
            }
        }

        //Load the retailers the user has favourited
        File favouritesDir = new File(favoriteFolderDirectory + File.separatorChar);
        String[] everythingInFavouritesDir = favouritesDir.list();
        if (everythingInFavouritesDir == null) {
            return null;
        } else {
            if (Arrays.asList(everythingInFavouritesDir).contains("favouriteRetailers.csv")) {
                System.out.println("Found the favourite retailers csv");
                ObservableSetList<Retailer> loadedFavouritedRetailers = DataManager.loadRetailersFromCSV(favoriteFolderDirectory + File.separatorChar + "favouriteRetailers.csv");
                RawDataViewerController.controller.setFavoriteRetailers(loadedFavouritedRetailers.getList());
            }

            if (Arrays.asList(everythingInFavouritesDir).contains("favouriteRoutes.csv")) {
                System.out.println("Found the favourited routes csv.");
                ObservableSetList<Model.Route> loadedFavouritedRotues = DataManager.loadRoutesFromCSV(favoriteFolderDirectory + File.separatorChar + "favouriteRoutes.csv");
                RawDataViewerController.controller.setFavouriteRoutes(loadedFavouritedRotues.getList());
            }

            if (Arrays.asList(everythingInFavouritesDir).contains("favouriteWifis.csv")) {
                System.out.println("Found the favourited wifis csv.");
                ObservableSetList<Model.WiFi> loadedFavouriteWifis = DataManager.loadWifiFromCSV(favoriteFolderDirectory + File.separatorChar + "favouriteWifis.csv");
                RawDataViewerController.controller.setFavouriteWifis(loadedFavouriteWifis.getList());
            }

            if (Arrays.asList(everythingInFavouritesDir).contains("favouriteBikeStations.csv")) {
                System.out.println("Found the favourited bike stations csv.");
                ObservableSetList<Model.BikeStation> loadedFavouriteBikeStations = DataManager.loadBikeStationFromCSV(favoriteFolderDirectory + File.separatorChar + "favouriteBikeStations.csv");
                RawDataViewerController.controller.setFavouriteBikeStations(loadedFavouriteBikeStations.getList());
            }
        }


        System.out.println("Finished loading all the CSV");

        //Have finished loading from the CSV files, refresh the list choice box to show the added lists
        //and close the pop up. Can only update the UI in a javafx thread, hence must use platform run later.
        Platform.runLater(() -> {
            RawDataViewerController.controller.changeListChoiceBox();
            loadingStage.close();
            //This shows the primaryStage which is the main program
            Main.copyPrimaryStage.show();
        });

        return null;
    }
}
