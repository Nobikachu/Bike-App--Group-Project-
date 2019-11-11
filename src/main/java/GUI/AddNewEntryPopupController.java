package GUI;

import Data.DataManager;
import Model.BikeStation;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

import static GUI.UserAlerts.error;

/**
 * Controller for the pop up which is used to add new Points of Interest
 */
public class AddNewEntryPopupController {

    //The name of each model's fxml used to get the classpath for loading them
    private final String BIKE_STATION_FXML = "bikestationDetails.fxml";
    private final String WIFI_FXML = "wifiDetails.fxml";
    private final String ROUTE_FXML = "routeDetails.fxml";
    private final String RETAILER_FXML = "retailerDetails.fxml";
    @FXML
    private Pane contentPane; //The pane that will hold the fields for each attribute
    //The controller for each fxml which contains the text fields for each data type
    private RetailerInfoController retailerInfoController;
    private RouteInfoController routeInfoController;
    private WifiInfoController wifiInfoController;
    private BikeStationInfoController bikeStationInfoController;
    //The stage which houses the nodes for the pop up to add new entries
    private Stage stage;
    //The entry type that wil lbe added.
    private String type;
    //The name of list that the entry will be added to
    private Object listName;

    /**
     * Each time the calls initType to add a new entry, 'reset' all the controllers removing
     * previous controllers that we don't need anymore.
     */
    private void resetControllers() {
        bikeStationInfoController = null;
        retailerInfoController = null;
        routeInfoController = null;
        wifiInfoController = null;
    }

    /**
     * Method called when the user wants to add a new entry, load the appropriate fxml file into the contectPane.
     *
     * @param listNameObject The name of the bike station list.
     * @param parentStage    The pop up's stage that needs to be closed when the details have been entered.
     * @param newType        The type of data the new entry will be (e.g. Bike Station, Retailer etc.)
     */
    public void initType(Object listNameObject, Stage parentStage, String newType) {
        stage = parentStage;
        listName = listNameObject;
        type = newType;

        resetControllers(); //Get rid of any unused/oudated controllers


        switch (type) {
            case "Retailer":
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(RETAILER_FXML));
                    Pane retailerPane = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().addAll(retailerPane);
                    retailerInfoController = fxmlLoader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "Route":
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(ROUTE_FXML));
                    Pane routePane = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().addAll(routePane);
                    routeInfoController = fxmlLoader.getController();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "WiFi":
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(WIFI_FXML));
                    Pane wifiPane = fxmlLoader.load();
                    contentPane.getChildren().clear();
                    contentPane.getChildren().addAll(wifiPane);
                    wifiInfoController = fxmlLoader.getController();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Bike Station":
                try {
                    //System.out.println(getClass().getResource("bikestationDetails.fxml"));
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(BIKE_STATION_FXML));
                    Pane bikeStationPane = fxmlLoader.load();
                    contentPane.getChildren().clear(); //Remove any previous loaded nodes from potentially other data types
                    contentPane.getChildren().add(bikeStationPane);

                    bikeStationInfoController = fxmlLoader.getController();


                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }


    }

    /**
     * Decide which method to be called to add the new bike entry based on the type chosen.
     */
    @FXML
    public void addNewEntry() {
        switch (type) {
            case "Retailer":
                addNewRetailerEntry();
                break;
            case "Route":
                addNewRouteEntry();
                break;
            case "WiFi":
                addNewWiFiEntry();
                break;
            case "Bike Station":
                addNewBikeStationEntry();
                break;
        }
    }

    /**
     * Check if the entered values are correct, if so add the new bike station entry to the appropraite list
     */
    private void addNewRetailerEntry() {
        if (!retailerInfoController.areFieldsEmpty() && retailerInfoController.areFieldsValid()) {
            Retailer newRetailer = retailerInfoController.getNewRetailer();
            DataManager.addRetailer(listName, newRetailer);
            //Added the new retailer, can now close the popup.
            closePopup();
        }

    }

    /**
     * Check if the entered values are correct, if so add the new route to the appropriate list.
     */
    private void addNewRouteEntry() {
        try {
            if (!routeInfoController.areFieldsEmpty() && routeInfoController.areFieldsValid()) {
                //All the entered values are valid, can create and add a new route
                Route newRoute = routeInfoController.getNewRoute();
                DataManager.addRoute(listName, newRoute);
                //Added the new reoute, can now close the popup
                closePopup();
            }
        } catch (ParseException e) {
            error("You entered invalid start or stop time.");
            //e.printStackTrace();
        }
    }

    /**
     * Check if the entered values are correct, if so add the new WiFi location to the appropriate list.
     */
    private void addNewWiFiEntry() {
        if (!wifiInfoController.areFieldsEmpty() && wifiInfoController.areFieldsValid()) {
            //All the entered values are valid, can created and add a new WiFi location
            WiFi newWifi = wifiInfoController.getNewWiFi();
            DataManager.addWifi(listName, newWifi);
            closePopup();
        }
    }


    /**
     * Check if the entered values are correct, if so add the new bike station entry to the appropriate list.
     */
    private void addNewBikeStationEntry() {
        if (!bikeStationInfoController.areFieldsEmpty()) {
            if (bikeStationInfoController.areFieldsValid()) {
                //All the fields are correct can now create and add the new bike stations
                BikeStation newBikeStation = bikeStationInfoController.getNewBikeStation();
                DataManager.addBikeStation(listName, newBikeStation);

                //Can now close the pop up since the bike station has been created and added
                closePopup();
            }
        }

    }

    /**
     * User clicked the button to close the pop up, close the stage.
     */
    @FXML
    public void closePopup() {
        stage.close();
    }

}
