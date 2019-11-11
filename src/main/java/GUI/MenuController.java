package GUI;

import Data.CSVWriter;
import Data.DataManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Dialog;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import static GUI.UserAlerts.error;

/**
 * Contains the methods called by the buttons contained in the outer menu. Namely the import and export functions
 */
public class MenuController {

    /**
     * Method to call the changeMapNormalTheme method from MapController.
     */
    @FXML
    public void changeNormalTheme() {
        MapController.controller.changeMapNormalTheme();
    }

    /**
     * Method to call the changeMapDarkTheme() method from MapController.
     */
    @FXML
    public void changeDarkTheme() {
        MapController.controller.changeMapDarkTheme();
    }

    /**
     * Presents the user with a file chooser to choose a CSV file o retailers to import. If the file cannot be read or
     * does not exist, the user is informed.
     */
    @FXML
    public void importRetailers() {
        File file = csvChooser("Retailers");

        if (file != null) {
            System.out.println(file.getAbsolutePath());
            //Settings.updateRetailer(file.getAbsolutePath());
            DataManager.importRetailers(file.getAbsolutePath());
        }

    }

    /**
     * User has clicked the "Close" menu item, close the applicaiton.
     */
    @FXML
    public void close() {
        Main.Main.closeApplicationRequest();
    }

    /**
     * Presents the user with a file chooser to choose a CSV file o routes to import. If the file cannot be read or
     * does not exist, the user is informed.
     */
    @FXML
    public void importRoutes() {
        File file = csvChooser("Routes");
        if (file == null) {
            return;
        }
        DataManager.importRoutes(file.getAbsolutePath());

    }

    /**
     * Presents the user with a file chooser to choose a CSV file o WiFi hotspots to import. If the file cannot be read
     * or does not exist, the user is informed.
     */
    @FXML
    public void importWiFi() {
        File file = csvChooser("WiFi");
        if (file == null) {
            return;
        }
        DataManager.importWifi(file.getAbsolutePath());
    }

    /**
     * Presents the user with a file chooser to choose a CSV file containing bike stations to import. If the
     * file cannot be read or does not exist the user is informed. The user can cancel choosing a file
     * importing a CSV files it not done.
     */
    @FXML
    public void importBikeStations() {
        File file = csvChooser("Bike Stations");
        if (file == null) {
            return;
        }
        DataManager.importBikeStation(file.getAbsolutePath());

    }


    /**
     * Prompt the user to select a list of Retailers to export and choose a CSV file to export it to
     */
    @FXML
    public void exportRetailers() {
        if (DataManager.getRetailersArrays().size() == 0) {
            error("There are no lists to export");
            return;
        }
        Object list = listDialog(DataManager.getRetailersArrays().keySet());

        if (list == null) { //User cancelled selecing a list to export
            return;
        }

        File file = csvExporter("retailer");
        if (file == null) {
            return;
        }

        CSVWriter.writeCSV(file.getAbsolutePath(), DataManager.retailerHeaders, DataManager.getRetailersArrays().get(list).getSet());
    }

    /**
     * Prompt the user to select a list of Routes to export and choose a CSV file to export it to
     */
    @FXML
    public void exportRoutes() {
        if (DataManager.getRouteArrays().size() == 0) {
            error("There are no lists to export");
            return;
        }
        Object list = listDialog(DataManager.getRouteArrays().keySet());

        if (list == null) { //User cancelled selecing a list to export
            return;
        }

        File file = csvExporter("route");
        if (file == null) {
            return;
        }

        CSVWriter.writeCSV(file.getAbsolutePath(), DataManager.routeHeaders, DataManager.getRouteArrays().get(list).getSet());
    }

    /**
     * Prompt the user to select a list of WiFi to export and choose a CSV file to export it to
     */
    @FXML
    public void exportWiFi() {
        if (DataManager.getWifiArrays().size() == 0) {
            error("There are no lists to export");
            return;
        }
        System.out.println(DataManager.getWifiArrays().keySet());
        Object list = listDialog(DataManager.getWifiArrays().keySet());

        if (list == null) { //User cancelled selecing a list to export
            return;
        }

        File file = csvExporter("wifi");
        if (file == null) {
            return;
        }

        CSVWriter.writeCSV(file.getAbsolutePath(), DataManager.wifiHeaders, DataManager.getWifiArrays().get(list).getSet());
    }

    /**
     * Prompt the user to select a bike station list and choose a file location to export the list to as a CSV file.
     */
    @FXML
    public void exportBikeStations() {
        if (DataManager.getBikeStationArrays().size() == 0) {
            error("There are no lists to export.");
            return;
        }
        Object list = listDialog(DataManager.getBikeStationArrays().keySet());

        if (list == null) { //User cancelled selecing a list to export
            return;
        }

        File file = csvExporter("Bike Station");

        if (file == null) { //User cancelled
            return;
        }

        CSVWriter.writeCSV(file.getAbsolutePath(), DataManager.bikeStationHeaders, DataManager.getBikeStationArrays().get(list).getSet());
    }


    /**
     * Loads a help popup explaining how to use the app.
     */
    @FXML
    public void loadHelpText() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("helpPopup.fxml")));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("goBike Guide");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("ERROR: an error occurred loading the popup.fxml file");
        }
    }

    /**
     * @param options name of all the selected type of list.
     * @return returns the selected list or null if the user has not picked.
     */
    private Object listDialog(Set<Object> options) {
        Dialog<Object> dialog = new ChoiceDialog<>(options.toArray()[0], options);
        dialog.setTitle("Which list");
        dialog.setHeaderText("Which list would you like to export from?");
        dialog.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        Optional<Object> result = dialog.showAndWait();
        return result.orElse(null);
    }


    /**
     * The file is exported to the selected directory.
     *
     * @param type the type of data being exported, used in the title.
     * @return a file object to be passed on to export to the selected directory.
     */
    private File csvExporter(String type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Export " + type + " CSV");
        Stage stage = new Stage();
        File file = fileChooser.showSaveDialog(stage);
        try {
            if (!file.getAbsolutePath().toLowerCase().endsWith(".csv")) {
                file = new File(file.getAbsolutePath().concat(".csv"));
            }
        } catch (NullPointerException e) {
            //When the user close fileChooser without exporting.
        }
        return file;
    }


    /**
     * Prompts the user to choose a CSV file to be imported/exported
     *
     * @param type the type of data being imported/exported, used in the title.
     * @return a file object to be passed to the CSVReader/CSVExport
     */
    private File csvChooser(String type) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open " + type + " CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        Stage stage = new Stage();
        return fileChooser.showOpenDialog(stage);
    }


}
