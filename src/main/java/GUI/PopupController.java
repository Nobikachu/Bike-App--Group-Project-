package GUI;

import Model.BikeStation;
import Model.Retailer;
import Model.Route;
import Model.WiFi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;

/**
 * Used to open up a new pop up application.
 */
public class PopupController {

    private Stage root; //The stage which contains the popup
    private String dataType;

    @FXML
    private Label typeOfDataLbl;
    @FXML
    private Pane contentPane;


    private RetailerInfoController retailerInfoController;
    private WifiInfoController wifiInfoController;
    private RouteInfoController routeInfoController;
    private BikeStationInfoController bikeStationInfoController;

    /**
     * Method is called in RawDataViewerController.showDetails. Wants to show the details of a retailer instance.
     * Call and set the appropriate fxml file to show labels and textfields for the attributes of the retailer.
     *
     * @param stage     Opens up a new stage on the GUI.
     * @param type      Retrieve the type data used (Bike Station, Retailer, Route, WifFi).
     * @param retailer  Retrieve a selected reatailer.
     * @param dataTable Table containing all the retailer in the application.
     * @param isNew     If the instance is newly created, disable editing of its coordinates.
     */
    public void initData(Stage stage, String type, Retailer retailer, ListView<Retailer> dataTable, boolean isNew) {
        //'Unlink' any potential previous controllers that might have previously been set.
        clearControllers();

        root = stage;
        dataType = type;

        System.out.println(getClass().getResource("retailerDetails.fxml"));

        try {
            //Load the fxml which contains all the functionality for editing the retailer information
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("retailerDetails.fxml"));
            Pane retailerPane = fxmlLoader.load();
            contentPane.getChildren().clear(); //Remove anything previous
            contentPane.getChildren().add(retailerPane);

            //Get the controller to access the text fields of the RetailerInfoController
            retailerInfoController = fxmlLoader.getController();

            //Pass the retailer that will be edited and the list view to be updated
            retailerInfoController.initData(retailer, dataTable);

            //Seta all the initial values to the text fields
            retailerInfoController.setTextFields(isNew);

            setLabels();
        } catch (IOException e) {
            System.out.println("ERROR: An error occurredd while loading retailerDetails.fxml");
        }
    }

    /**
     * Method is called in RawDataViewerController.showDetails. Wants to show the details of a WiFi instance.
     * Call and set the appropriate fxml file to show labels and textfields for the attributes of the WiFi.
     *
     * @param stage     Opens up a new stage on the GUI.
     * @param type      Retrieve the type data used (Bike Station, Retailer, Route, WifFi).
     * @param wifi      Retrieve a selected wifis.
     * @param dataTable Table containing all the wifi in the application.
     * @param isNew     If the instance is newly created, disable editing of its coordinates.
     */
    public void initData(Stage stage, String type, WiFi wifi, ListView<WiFi> dataTable, boolean isNew) {
        //'Unlink' any potential previous controllers that might have previously been set.
        clearControllers();

        root = stage;
        dataType = type;

        //System.out.println(getClass().getResource("wifiDetails.fxml"));

        try {
            //Load the fxml which contains all the functionality for editing the retailer information
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("wifiDetails.fxml"));
            Pane wifiPane = fxmlLoader.load();
            contentPane.getChildren().clear(); //Remove anything previous
            contentPane.getChildren().add(wifiPane);

            //Get the controller to access the text fields of the RetailerInfoController
            wifiInfoController = fxmlLoader.getController();

            //Pass the retailer that will be edited and the list view to be updated
            wifiInfoController.initData(wifi, dataTable);

            //Seta all the initial values to the text fields
            wifiInfoController.setTextFields(isNew);

            setLabels();
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred while loading wifiDetails.fxml");
        }
    }

    /**
     * Method is called in RawDataViewerController.showDetails. Wants to show the details of a route instance.
     * Call and set the appropriate fxml file to show labels and textfields for the attributes of the route.
     *
     * @param stage     Opens up a new stage on the GUI.
     * @param type      Retrieve the type data used  Retailer, Route, WifFi, Bike Station).
     * @param route     Retrieve a selected routes.
     * @param dataTable Table containing all the routes in the application.
     * @param isNew     If the instance is newly created, disable editing of its coordinates.
     */
    public void initData(Stage stage, String type, Route route, ListView<Route> dataTable, boolean isNew) {
        //'Unlink' any potential previous controllers that might have previously been set.
        clearControllers();

        root = stage;
        dataType = type;

        //System.out.println(getClass().getResource("wifiDetails.fxml"));

        try {
            //Load the fxml which contains all the functionality for editing the retailer information
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("routeDetails.fxml"));
            Pane routePane = fxmlLoader.load();
            contentPane.getChildren().clear(); //Remove anything previous
            contentPane.getChildren().add(routePane);

            //Get the controller to access the text fields of the RetailerInfoController
            routeInfoController = fxmlLoader.getController();

            //Pass the retailer that will be edited and the list view to be updated
            routeInfoController.initData(route, dataTable);

            //Seta all the initial values to the text fields
            routeInfoController.setTextFields(isNew);

            setLabels();
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred while loading routeDetails.fxml");
        }
    }


    /**
     * Method called in RawDataViewerController.showDetails. Wants to show the details of a bike station instance.
     * Call and set the appropriate fxml file to swho the labels and text fields for the attribute of the route.
     *
     * @param stage       Opens up a new stage for the appication.
     * @param type        The type of data chosen (Bike Station, Retailer, Route, WiFi)
     * @param bikeStation The instance whose fields will be displayed and potentially edited.
     * @param dataTable   The list view that needs to be refreshed to show the new changes.
     * @param isNew       If the instance is newly created, disable editing of its coordinates.
     */
    public void initData(Stage stage, String type, BikeStation bikeStation, ListView<BikeStation> dataTable, boolean isNew) {
        //'Unlink' any potential prevoius controllers that might have previously been set.
        clearControllers();

        root = stage;
        dataType = type;

        try {
            //Load the fxml which contains all the functionality for eidting the retailer information
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("bikestationDetails.fxml"));
            Pane bikeStationPane = fxmlLoader.load();
            contentPane.getChildren().clear(); //Remove the previously loaded nodes for potentially other data types
            contentPane.getChildren().add(bikeStationPane);

            //Get the controller to access the text fields of the BikeStationInfoController
            bikeStationInfoController = fxmlLoader.getController();

            //Pass the bike station that will be edited and the list view to be updated
            bikeStationInfoController.initData(bikeStation, dataTable);

            //Set all the initial values to the text fields.
            bikeStationInfoController.setTextFields(isNew);
        } catch (IOException e) {
            System.out.println("ERROR: An error occured while loading bikeStationDetails.fxml");
        }
    }

    /**
     * Set the labels to display the appropriate text.
     */
    private void setLabels() {
        typeOfDataLbl.setText(dataType);

    }

    /**
     * If the popup was called before, then get rid of any previously instantiated controllers to make sure
     * we can then instantate the one we require.
     */
    private void clearControllers() {
        wifiInfoController = null;
        routeInfoController = null;
        retailerInfoController = null;
        bikeStationInfoController = null;
    }

    /**
     * The user has clicked the applyEditsBtn meaning they want to save any changes they've made in the GUI
     * to the object. Call the controller's method to set the new values to the object.
     */
    @FXML
    private void applyEdits() {
        if (retailerInfoController != null) {
            if (!retailerInfoController.applyEdits()) {
                return;
            }
        } else if (wifiInfoController != null) {
            if (!wifiInfoController.applyEdits()) {
                return;
            }
        } else if (routeInfoController != null) {
            try {
                if (!routeInfoController.applyEdits()) {
                    return;
                }
            } catch (ParseException e) {
                UserAlerts.error("Error. Invalid date/time format. Should be yyy-mm-dd HH:MM:SS");
                return;
            }
        } else if (bikeStationInfoController != null) {
            bikeStationInfoController.applyEdits();
        }

        root.close();
    }

//    @FXML
//    private void createRoute() {
//        routeInfoController.createEntry();
//    }

    /**
     * User has clicked the cancelBtn, will close the pop up.
     */
    @FXML
    private void closePopup() {
        root.close();
    }

//    /**
//     * Alters the user on invalid inputs they have entered.
//     * @param message The message displayed to the user using the dialogue.
//     */
//    public static void error(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
//        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//        alert.showAndWait();
//    }
//
//    public static String warning(String message) {
//        Alert alert = new Alert(Alert.AlertType.WARNING, message, ButtonType.YES, ButtonType.NO);
//        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
//        Optional<ButtonType> input = alert.showAndWait();
//        return input.get().getText();
//    }
}

