package GUI;

import Data.DataManager;
import Data.Filter;
import Data.ObservableSetList;
import Data.Settings;
import Main.Main;
import Model.*;
import com.lynden.gmapsfx.javascript.object.LatLong;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

import static GUI.UserAlerts.error;
import static GUI.UserAlerts.warning;


/**
 * Controller which dictates the functionality of the data viewer. Features nodes for filtering, searching, displaying
 * the raw data and editing data.
 * <p>
 * ============================================= Displaying Using ListView =============================================
 * Functions by having multiple list views for each type of data. E.g. Retailers have a retailListView which will
 * be set to an Obvservablelist of type Retailer. The same is done for WiFi, Route and any other data types included.
 * The controller is initialized such that all the list views are set to hidden, based on the data type chosen to be
 * displayed in the dataTypeChoiceBox the appropriate list view is shown while the rest are hidden.
 */
public class RawDataViewerController {
    //Variable for the route that is currently being displayed.
    public static Route currentlySelectedRoute = null;
    //Variable for the retailer that is currently being displayed.
    public static Retailer currentlySelectedRetailer = null;
    //Variable for the wifi that is currently being displayed.
    public static WiFi currentlySelectedWiFi = null;
    //Variable for the bike station that is currently being displayed.
    public static BikeStation currentlySelectedBikeStation = null;
    public static RawDataViewerController controller;
    //The index of the map pane in the tabbed panes.
    private static int MAP_PANE_INDEX = 1;
    @FXML
    private ChoiceBox<String> dataTypeChoiceBox;
    @FXML
    private ListView<Retailer> retailListView = new ListView<>();
    @FXML
    private ListView<WiFi> wifiListView = new ListView<>();
    @FXML
    private ListView<Route> routeListView = new ListView<>();
    @FXML
    private ListView<BikeStation> bikeStationListView = new ListView<>();
    @FXML
    private Label errorDetailLabel;
    @FXML
    private Label errorFilterTypeLabel;
    @FXML
    private Label errorSearchTypeLabel;
    @FXML
    private ComboBox<String> filterDetailComboBox;
    @FXML
    private ChoiceBox<String> filterTypeChoiceBox;
    @FXML
    private ChoiceBox<String> searchChoiceBox;
    @FXML
    private TextField searchText;
    @FXML
    private Label errorRecordLabel;
    @FXML
    private Button removeFavouriteButton;
    @FXML
    private Button addFavouriteButton;
    @FXML
    private ChoiceBox<Object> listChoiceBox;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button statisticButton;
    @FXML
    private Button removeListBtn;
    @FXML
    private String currentList;
    @FXML
    private Label listErrorLabel;
    @FXML
    private ChoiceBox<String> sortChoiceBox;
    //Variable used to keep track of which data type is selected from dataTypeChoices
    private String selectedType;
    //Variable used to keep track of which filter type is selected from filterTypeChoiceBox.
    private String selectedFilterType = "";
    //Arrays of strings that contains unique filter details.
    private ArrayList<String> filterDetails = new ArrayList<>();
    //Array list of data types that cnan be selected and shown in the different list views
    private final ArrayList<String> dataTypeChoices = new ArrayList<>(Arrays.asList("Retailer", "Route", "WiFi", "Bike Station"));
    //Array list of retail types that can be selected and shown in the different list views
    private final ArrayList<String> retailTypeChoices = new ArrayList<>(Arrays.asList("Street name", "ZIP", "Primary"));
    //Array list of wifi types that can be selected and shown in the different list views
    private final ArrayList<String> wifiTypeChoices = new ArrayList<>(Arrays.asList("Borough", "Type", "Provider"));
    //Array list of route types that can be selected and shown in the different list views
    private final ArrayList<String> routeTypeChoices = new ArrayList<>(Arrays.asList("Start location", "End location", "Bike ID", "Gender"));
    //Array list of bike station types that can be selected and shown in the  different list views
    private final ArrayList<String> bikeStationTypeChoices = new ArrayList<>(Arrays.asList("Name"));
    //Fields Retailers can be sorted on
    private final ArrayList<String> retailSortChoices = new ArrayList<>(Arrays.asList("Name", "Street Name"));
    //Fields WiFi can be sorted on
    private final ArrayList<String> wifiSortChoices = new ArrayList<>(Arrays.asList("Name", "SSID"));
    //Fields Routes can be sorted on
    private final ArrayList<String> routeSortChoices = new ArrayList<>(Arrays.asList("Distance", "Duration", "Date/Time"));
    //Fields Bike Stations can be sorted on
    private final ArrayList<String> bikeStationSortChoices = new ArrayList<>(Arrays.asList("Name"));
    //Observable lists which will contain the data to be shown. Separate from our entire data sets for each type stored in the static DataManager
    private ObservableList<Retailer> displayedRetails = FXCollections.observableArrayList();
    private ObservableList<WiFi> displayedWifis = FXCollections.observableArrayList();
    private ObservableList<Route> displayedRoutes = FXCollections.observableArrayList();
    private ObservableList<BikeStation> displayedBikeStations = FXCollections.observableArrayList();
    //Observable lists which will contain all the data added in the application
    private ObservableList<Retailer> allRetails = FXCollections.observableArrayList();
    private ObservableList<WiFi> allWifis = FXCollections.observableArrayList();
    private ObservableList<Route> allRoutes = FXCollections.observableArrayList();
    private ObservableList<BikeStation> allBikeStations = FXCollections.observableArrayList();
    //Observable list which will contain the favourite data
    private ObservableList<Retailer> favouriteRetails = FXCollections.observableArrayList();
    private ObservableList<WiFi> favouriteWifis = FXCollections.observableArrayList();
    private ObservableList<Route> favouriteRoutes = FXCollections.observableArrayList();
    private ObservableList<BikeStation> favouriteBikeStations = FXCollections.observableArrayList();

    /**
     * Get the retailers the user has favourited.
     *
     * @return Retails the user has favourited
     */
    public ObservableList<Retailer> getFavoriteRetailers() {
        return favouriteRetails;
    }

    /**
     * Set the retailers favourited by the user.
     *
     * @param favourites The values the favourited list will be set to.
     */
    public void setFavoriteRetailers(ObservableList<Retailer> favourites) {
        favouriteRetails = favourites;
    }

    /**
     * Get the list of WiFis the user has favourited.
     *
     * @return WiFis the user has favourited.
     */
    public ObservableList<WiFi> getFavouriteWifis() {
        return favouriteWifis;
    }

    /**
     * Set the WiFis favourited by the user.
     *
     * @param favourites The values the favourited list will be set to.
     */
    public void setFavouriteWifis(ObservableList<WiFi> favourites) {
        favouriteWifis = favourites;
    }

    /**
     * Get the routes the user has favourited.
     *
     * @return Routes the user has favourited.
     */
    public ObservableList<Route> getFavouriteRoutes() {
        return favouriteRoutes;
    }

    /**
     * Set the list of routes the user have favourited.
     *
     * @param favourites List of routes to be marked as favourite
     */
    public void setFavouriteRoutes(ObservableList<Route> favourites) {
        favouriteRoutes = favourites;
    }

    /**
     * Get the bike stations the user has favourited.
     *
     * @return Bike stations the user has favourited.
     */
    public ObservableList<BikeStation> getFavouriteBikeStations() {
        return favouriteBikeStations;
    }

    /**
     * Set the bike stations favourited by the user.
     *
     * @param favourites The values the favourited list will be set to.
     */
    public void setFavouriteBikeStations(ObservableList<BikeStation> favourites) {
        favouriteBikeStations = favourites;
    }

    @FXML
    private void showDetails() {
        showDetailsHelper(false);
    }

    /**
     * Bound to the showDetailsBtn, when clicked if there is a selected item from the list view, it will display
     * the popup (showing the full details and giving the option to edit details. It must be checked which data type
     * has been selected, then the appropriate listview and point of interest object will be passed.
     *
     * @param isNew Whether or not the object being edited is new or not. Disable editing coordinates if the object is just created.
     */
    private void showDetailsHelper(boolean isNew) {
        //If no entries was selected, display an error message.
        if (retailListView.getSelectionModel().getSelectedItem() == null && selectedType.equals("Retailer")) {
            errorDetailLabel.setVisible(true);
        } else if (wifiListView.getSelectionModel().getSelectedItem() == null && selectedType.equals("WiFi")) {
            errorDetailLabel.setVisible(true);
        } else if (routeListView.getSelectionModel().getSelectedItem() == null && selectedType.equals("Route")) {
            errorDetailLabel.setVisible(true);
        } else if (bikeStationListView.getSelectionModel().getSelectedItem() == null && selectedType.equals("Bike Station")) {
            errorDetailLabel.setVisible(true);
        } else {
            errorDetailLabel.setVisible(false);
            //Item was selected, need to retrieve and pass the values

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(("popup.fxml")));
                Parent root = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("Pop up");
                stage.setScene(new Scene(root));

                //Controller will be used used to pass the data (Retailer, Route, WiFi etc.) to be dispaleyd and
                //possibly edited as well as the respective listview to be refreshed to reflect any changes.
                PopupController popupController = fxmlLoader.getController();

                //Figure out which item was selected and pass that value to the controller
                switch (selectedType) {
                    case "Retailer":
                        System.out.println("A retailer object is chosen to be shown details and possibly edited");
                        popupController.initData(stage, selectedType, retailListView.getSelectionModel().getSelectedItem(), retailListView, isNew);
                        break;
                    case "WiFi":
                        popupController.initData(stage, selectedType, wifiListView.getSelectionModel().getSelectedItem(), wifiListView, isNew);
                        break;
                    case "Route":
                        popupController.initData(stage, selectedType, routeListView.getSelectionModel().getSelectedItem(), routeListView, isNew);
                        break;
                    case "Bike Station":
                        popupController.initData(stage, selectedType, bikeStationListView.getSelectionModel().getSelectedItem(), bikeStationListView, isNew);
                        break;
                }


                System.out.println("Retrieved selected data type of " + selectedType);

                stage.show();
            } catch (IOException e) {
                System.out.println("ERROR: an error occurred loading the popup.fxml file");
            }
        }
    }

    /**
     * Method used to update the old routes list with the new list of routes
     *
     * @param newRoutes The new list of routes that will replace the old one
     */
    public void updateRouteList(ObservableList<Route> newRoutes) {
        allRoutes = newRoutes;
        System.out.println("Current list: " + listChoiceBox.getSelectionModel().getSelectedItem());
        DataManager.getRouteArrays().put(listChoiceBox.getSelectionModel().getSelectedItem(), new ObservableSetList<>(newRoutes));
    }

    /**
     * Prompts the user to enter values for the new entry into the currently selected list view.
     */
    @FXML
    private void add() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addNewEntryPopup.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Add new " + selectedType);
            stage.setScene(new Scene(root));

            AddNewEntryPopupController newEntryPopupController = fxmlLoader.getController();

            //Load the correct nodes onto the popup for the user to enter values into
            Object currentList = listChoiceBox.getSelectionModel().getSelectedItem();
            newEntryPopupController.initType(currentList, stage, selectedType);

            stage.show();
        } catch (IOException e) {
            System.out.println("Could not load addNewEntryPopup.fxml");
            e.printStackTrace();
            System.out.flush();
        }

    }

    /**
     * Prompts the user to remove values from the  currently selected list view.
     */
    @FXML
    private void deleteList() {
        if (listChoiceBox.getSelectionModel().getSelectedItem() != null) {
            if (Objects.equals(warning("Are you sure you want to delete this list?\nThis cannot be undone."), "Yes")) {
                try {
                    switch (selectedType) {
                        case "Retailer":
                            DataManager.getRetailersArrays().remove(listChoiceBox.getSelectionModel().getSelectedItem());
                            Files.deleteIfExists(Paths.get(Settings.retailerFolderDirectory + File.separatorChar + currentList + ".csv"));
                            break;
                        case "Route":
                            DataManager.getRouteArrays().remove(listChoiceBox.getSelectionModel().getSelectedItem());
                            Files.deleteIfExists(Paths.get(Settings.routeFolderDirectory + File.separatorChar + currentList + ".csv"));
                            break;
                        case "WiFi":
                            DataManager.getWifiArrays().remove(listChoiceBox.getSelectionModel().getSelectedItem());
                            Files.deleteIfExists(Paths.get(Settings.wifiFolderDirectory + File.separatorChar + currentList + ".csv"));
                            break;
                        case "Bike Station":
                            DataManager.getBikeStationArrays().remove(listChoiceBox.getSelectionModel().getSelectedItem());
                            Files.deleteIfExists(Paths.get(Settings.bikeStationFolderDirectory + File.separatorChar + currentList + ".csv"));
                            break;
                    }
                    changeListChoiceBox();
                } catch (IOException e) {
                    error("Error deleting the chosen file. Do you have permission?");
                }
            }
        }
    }

    /**
     * Sorts the data by the attribute given by 'field'
     *
     * @param field The attribute to sort on
     */
    @FXML
    private void sortBy(String field) {
        switch (selectedType) {
            case "Retailer":
                switch (sortChoiceBox.getSelectionModel().getSelectedItem()) {
                    case "Name":
                        displayedRetails.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                        break;
                    case "Street Name":
                        displayedRetails.sort((o1, o2) -> o1.getStreetName().compareToIgnoreCase(o2.getStreetName()));
                        break;
                }
                break;
            case "Route":
                switch (sortChoiceBox.getSelectionModel().getSelectedItem()) {
                    case "Distance":
                        displayedRoutes.sort(Comparator.comparingDouble(Route::getRouteDistance));
                        break;
                    case "Duration":
                        displayedRoutes.sort(Comparator.comparingDouble(Route::getRouteDuration));
                        break;
                    case "Date/Time":
                        displayedRoutes.sort(Comparator.comparing(Route::getDate));
                }
                break;
            case "WiFi":
                switch (sortChoiceBox.getSelectionModel().getSelectedItem()) {
                    case "Name":
                        displayedWifis.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                        break;
                    case "SSID":
                        displayedWifis.sort((o1, o2) -> o1.getSsid().compareToIgnoreCase(o2.getSsid()));
                        break;
                }
                break;
            case "Bike Station":
                switch (sortChoiceBox.getSelectionModel().getSelectedItem()) {
                    case "Name":
                        displayedBikeStations.sort((o1, o2) -> o1.getName().compareToIgnoreCase(o2.getName()));
                        break;
                }
                break;
        }
    }

    /**
     * The button to display the selected item form the list view on the map is pressed.errorDetailLabel.setVisible(true);
     */
    @FXML
    private void showSelectedEntryOnMap() {

        if (retailListView.getSelectionModel().getSelectedItem() != null && selectedType.equals("Retailer")) {

            errorDetailLabel.setVisible(false);

            MapController.controller.clearRetailer();

            MapController.controller.retailerWifiNearby.setDisable(false);
            MapController.controller.clearRetailerButton.setDisable(false);

            Retailer selectedRetailer = retailListView.getSelectionModel().getSelectedItem();
            currentlySelectedRetailer = selectedRetailer;

            MapController.map.setCenter(new LatLong(selectedRetailer.getLocation().getLatitude(), selectedRetailer.getLocation().getLongitude()));


            //Take you to the map and show the route on the map
            MapController.controller.displayRetailer(selectedRetailer);
            SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
            selectionModel.select(MAP_PANE_INDEX);

        } else if (wifiListView.getSelectionModel().getSelectedItem() != null && selectedType.equals("WiFi")) {

            errorDetailLabel.setVisible(false);

            MapController.controller.clearWifi();

            MapController.controller.clearWifiButton.setDisable(false);

            WiFi selectedWiFi = wifiListView.getSelectionModel().getSelectedItem();
            currentlySelectedWiFi = selectedWiFi;

            MapController.map.setCenter(new LatLong(currentlySelectedWiFi.getLocation().getLatitude(), currentlySelectedWiFi.getLocation().getLongitude()));

            //Take you to the map and show the route on the map
            MapController.controller.displayWiFi(selectedWiFi);
            SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
            selectionModel.select(MAP_PANE_INDEX);

        } else if (routeListView.getSelectionModel().getSelectedItem() != null && selectedType.equals("Route")) {

            errorDetailLabel.setVisible(false);

            MapController.controller.clearRoute();

            MapController.controller.routeRetailerNearby.setDisable(false);
            MapController.controller.routeWifiNearby.setDisable(false);
            MapController.controller.routeBikeStationNearby.setDisable(false);
            MapController.controller.clearRouteButton.setDisable(false);

            Route selectedRoute = routeListView.getSelectionModel().getSelectedItem();
            currentlySelectedRoute = selectedRoute;

            //Take you to the map and show the route on the map
            MapController.controller.displayRoute(selectedRoute.getRouteStartStationName(), selectedRoute.getRouteEndStationName(), new Location(selectedRoute.getRouteStartStationLatitude(), selectedRoute.getRouteStartStationLongitude()), new Location(selectedRoute.getRouteEndStationLatitude(), selectedRoute.getRouteEndStationLongitude()));
            SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
            selectionModel.select(MAP_PANE_INDEX);
        } else if (bikeStationListView.getSelectionModel().getSelectedItem() != null && selectedType.equals("Bike Station")) {
            errorDetailLabel.setVisible(false);

            //Get rid of the previously
            MapController.controller.clearBikeStation();

            BikeStation selectedBikeStation = bikeStationListView.getSelectionModel().getSelectedItem();
            currentlySelectedBikeStation = selectedBikeStation;

            MapController.map.setCenter(new LatLong(selectedBikeStation.getLocation().getLatitude(), selectedBikeStation.getLocation().getLongitude()));


            MapController.controller.displayBikeStation(selectedBikeStation);

            //Now have the option of clearing the selected and displayed bike station from the map.
            MapController.controller.clearBikeStationButton.setDisable(false);

            //Take you to the map and where the bike station is displayed
            SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
            selectionModel.select(MAP_PANE_INDEX);

        } else {
            errorDetailLabel.setVisible(true);
        }
    }

    /**
     * The button to change tabpane to the Statistic pane and show statistic details about the selected route.
     * If the route is not selected, error label will be displayed.
     */
    @FXML
    private void showStatistic() {
        if (routeListView.getSelectionModel().getSelectedItem() != null && selectedType.equals("Route")) {
            Route selectedRoute = routeListView.getSelectionModel().getSelectedItem();
            currentlySelectedRoute = selectedRoute;
            Location start = new Location(selectedRoute.getRouteStartStationLatitude(), selectedRoute.getRouteStartStationLongitude());
            Location end = new Location(selectedRoute.getRouteEndStationLatitude(), selectedRoute.getRouteEndStationLongitude());
            StatisticController.controller.initData(selectedRoute.getRouteStartStationName(), selectedRoute.getRouteEndStationName(), start, end);
            StatisticController.controller.setTextField();

            SingleSelectionModel<Tab> selectionModel = Main.tabPane.getSelectionModel();
            selectionModel.select(2);
        } else {
            errorDetailLabel.setVisible(true);
        }
    }

    /**
     * Bound to the showFavourite button, when clicked the listView will only display favourite data.
     * Set visibility of a RemoveFavourite button as true.
     * Set visibility of a addFavourite button as false.
     */
    @FXML
    private void showFavourite() {
        displayedWifis = favouriteWifis;
        displayedRoutes = favouriteRoutes;
        displayedRetails = favouriteRetails;
        displayedBikeStations = favouriteBikeStations;
        retailListView.setItems(displayedRetails);
        wifiListView.setItems(displayedWifis);
        routeListView.setItems(displayedRoutes);
        bikeStationListView.setItems(displayedBikeStations);
        removeFavouriteButton.setVisible(true);
        addFavouriteButton.setVisible(false);
        changeFilterDetailComboBox(); //Update the filter details.
    }

    /**
     * Bound to the addFavourite buttondisplayedWifis = allWifis;, when clicked the selected record will be marked as favourite.
     * Displays an error message, when no records has been selected.
     * Checks if the selected object does not already exists.
     */
    @FXML
    private void addFavourite() {
        errorRecordLabel.setVisible(false);
        if (selectedType.equals("Retailer") && !favouriteRetails.contains(retailListView.getSelectionModel().getSelectedItem())) { //Checks if the retail does not exist in the current list.
            if (retailListView.getSelectionModel().getSelectedItems() == null) { //If function checks if the user has selected an record.
                errorRecordLabel.setVisible(true);
            } else {
                favouriteRetails.add(retailListView.getSelectionModel().getSelectedItem()); //Adds to the selected record to favourite.
            }
        } else if (selectedType.equals("WiFi") && !favouriteWifis.contains(wifiListView.getSelectionModel().getSelectedItem())) {
            if (wifiListView.getSelectionModel().getSelectedItems() == null) {
                errorRecordLabel.setVisible(true);
            } else {
                favouriteWifis.add(wifiListView.getSelectionModel().getSelectedItem());
            }
        } else if (selectedType.equals("Route") && !favouriteRoutes.contains(routeListView.getSelectionModel().getSelectedItem())) {
            if (routeListView.getSelectionModel().getSelectedItems() == null) {
                errorRecordLabel.setVisible(true);
            } else {
                favouriteRoutes.add(routeListView.getSelectionModel().getSelectedItem());
            }
        } else if (selectedType.equals("Bike Station") && !favouriteBikeStations.contains(bikeStationListView.getSelectionModel().getSelectedItem())) {
            if (bikeStationListView.getSelectionModel().getSelectedItems() == null) {
                errorRecordLabel.setVisible(true);
            } else {
                favouriteBikeStations.add(bikeStationListView.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**
     * Bound to the removeFavourite button, when clicked the selected favourite record will be deleted.
     * Displays an error message, when no records has been selected.
     */
    @FXML
    private void removeFavourite() {
        errorRecordLabel.setVisible(false);
        switch (selectedType) {
            case "Retailer":
                if (retailListView.getSelectionModel().getSelectedItems() == null) {
                    errorRecordLabel.setVisible(true);
                } else {
                    favouriteRetails.remove(retailListView.getSelectionModel().getSelectedItem()); //Removes the selected favourite retail
                }
                break;
            case "WiFi":
                if (wifiListView.getSelectionModel().getSelectedItems() == null) {
                    errorRecordLabel.setVisible(true);
                } else {
                    favouriteWifis.remove(wifiListView.getSelectionModel().getSelectedItem());
                }
                break;
            case "Route":
                if (routeListView.getSelectionModel().getSelectedItems() == null) {
                    errorRecordLabel.setVisible(true);
                } else {
                    favouriteRoutes.remove(routeListView.getSelectionModel().getSelectedItem());
                }
                break;
            case "Bike Station":
                if (bikeStationListView.getSelectionModel().getSelectedItems() == null) {
                    errorRecordLabel.setVisible(true);
                } else {
                    favouriteBikeStations.remove(bikeStationListView.getSelectionModel().getSelectedItem());
                }
        }
        changeFilterDetailComboBox();
    }

    /**
     * Bound to the showDefault button, when clicked listView changes to the default view.
     * Set visibility of a RemoveFavourite button as false.
     * Set visibility of a addFavourite button as true.
     */
    @FXML
    private void showDefault() {
        //Refreshes the ListView to the origin
        displayedRetails = allRetails;
        displayedWifis = allWifis;
        displayedRoutes = allRoutes;
        displayedBikeStations = allBikeStations;
        retailListView.setItems(displayedRetails);
        wifiListView.setItems(displayedWifis);
        routeListView.setItems(displayedRoutes);
        bikeStationListView.setItems(displayedBikeStations);
        removeFavouriteButton.setVisible(false);
        addFavouriteButton.setVisible(true);

        searchText.clear(); //Clear the specified search text

        changeFilterDetailComboBox(); //Update the filter details.
    }

    /**
     * Bound to the applyFilterButton, when clicked if the filter type and details are selected.
     * ListView displays only the selected type and details.
     * Exception is called when the filter type or detail has not been selected.
     */
    @FXML
    public void applyFilter() {
        try {
            String selectedFilterDetail = filterDetailComboBox.getSelectionModel().getSelectedItem();
            errorFilterTypeLabel.setVisible(false);
            //Refreshes the ListView to the origin
            retailListView.setItems(displayedRetails);
            wifiListView.setItems(displayedWifis);
            routeListView.setItems(displayedRoutes);
            Filter filter = new Filter(); //Creates a filter class to use the filter functionality.
            //The if functions check which filter type has being selected.
            switch (selectedFilterType) {
                case "Street name":
                    displayedRetails = filter.filterRetailerStreet(displayedRetails, selectedFilterDetail); //Applies the filter

                    retailListView.setItems(displayedRetails);
                    break;
                case "ZIP":
                    displayedRetails = filter.filterRetailerZIP(displayedRetails, selectedFilterDetail); //Applies the filter

                    retailListView.setItems(displayedRetails);
                    break;
                case "Primary":
                    displayedRetails = filter.filterRetailerPrimary(displayedRetails, selectedFilterDetail); //Applies the filter

                    retailListView.setItems(displayedRetails);
                    break;
                case "Borough":
                    displayedWifis = filter.filterWiFiBorough(displayedWifis, selectedFilterDetail); //Applies the filter

                    wifiListView.setItems(displayedWifis);
                    break;
                case "Type":
                    displayedWifis = filter.filterWiFiType(displayedWifis, selectedFilterDetail); //Applies the filter

                    wifiListView.setItems(displayedWifis);
                    break;
                case "Provider":
                    displayedWifis = filter.filterWiFiProvider(displayedWifis, selectedFilterDetail); //Applies the filter

                    wifiListView.setItems(displayedWifis);
                    break;
                case "Start location":
                    displayedRoutes = filter.filterRouteStartLocation(displayedRoutes, selectedFilterDetail); //Applies the filter

                    routeListView.setItems(displayedRoutes);
                    break;
                case "End location":
                    displayedRoutes = filter.filterRouteEndLocation(displayedRoutes, selectedFilterDetail); //Applies the filter

                    routeListView.setItems(displayedRoutes);
                    break;
                case "Bike ID":
                    displayedRoutes = filter.filterRouteBikeID(displayedRoutes, selectedFilterDetail); //Applies the filter

                    routeListView.setItems(displayedRoutes);
                    break;
                case "Gender":
                    displayedRoutes = filter.filterRouteGender(displayedRoutes, selectedFilterDetail); //Applies the filter

                    routeListView.setItems(displayedRoutes);
                    break;
                case "Name":
                    displayedBikeStations = filter.filterBikeStationName(displayedBikeStations, selectedFilterDetail); //Applies the filter

                    bikeStationListView.setItems(displayedBikeStations);
            }
            changeFilterDetailComboBox(); //Updates the filterDetailComboBox
        } catch (Exception e) { //When the filter is not selected.
            errorFilterTypeLabel.setVisible(true); //Displays an error message.
        }

    }

    /**
     * Bound to the searchButton, when clicked the listView displays the data relating to the text field.
     * Error message will be shown when the user has not selected a search type.
     */
    @FXML
    public void showSearch() {
        try {
            String selectedSearchType = searchChoiceBox.getSelectionModel().getSelectedItem();
            String searchedText = searchText.getText();
            errorSearchTypeLabel.setVisible(false);
            Filter filter = new Filter(); //Creates a filter class to use the filter functionality.
            //The if functions check which filter type has being selected.
            switch (selectedSearchType) {
                case "Street name":
                    displayedRetails = filter.searchRetailerStreet(allRetails, searchedText); //searches the data.

                    retailListView.setItems(displayedRetails);
                    break;
                case "ZIP":
                    displayedRetails = filter.searchRetailerZIP(allRetails, searchedText);
                    retailListView.setItems(displayedRetails);
                    break;
                case "Primary":
                    displayedRetails = filter.searchRetailerPrimary(allRetails, searchedText);
                    retailListView.setItems(displayedRetails);
                    break;
                case "Borough":
                    displayedWifis = filter.searchWiFiBorough(allWifis, searchedText);
                    wifiListView.setItems(displayedWifis);
                    break;
                case "Provider":
                    displayedWifis = filter.searchWiFiProvider(allWifis, searchedText);
                    wifiListView.setItems(displayedWifis);
                    break;
                case "Type":
                    displayedWifis = filter.searchWiFiType(allWifis, searchedText);
                    wifiListView.setItems(displayedWifis);
                    break;
                case "Start location":
                    displayedRoutes = filter.searchRouteStartLocation(allRoutes, searchedText);
                    routeListView.setItems(displayedRoutes);
                    break;
                case "End location":
                    displayedRoutes = filter.searchRouteEndLocation(allRoutes, searchedText);
                    routeListView.setItems(displayedRoutes);
                    break;
                case "Gender":
                    displayedRoutes = filter.searchRouteGender(allRoutes, searchedText);
                    routeListView.setItems(displayedRoutes);
                    break;
                case "Bike ID":
                    displayedRoutes = filter.searchRouteBikeID(allRoutes, searchedText);
                    routeListView.setItems(displayedRoutes);
                    break;
                case "Name":
                    displayedBikeStations = filter.searchBikeStationName(allBikeStations, searchedText);
                    bikeStationListView.setItems(displayedBikeStations);
            }
        } catch (Exception e) { //When the search type has not being selected.
            errorSearchTypeLabel.setVisible(true); //Displays an error message.
        }
    }


    /**
     * Method used to set the desired listview to be visible while hiding the rest.
     */
    private void showSelectedListView() {
        switch (selectedType) {
            case "Retailer":
                retailListView.setVisible(true);
                routeListView.setVisible(false);
                wifiListView.setVisible(false);
                bikeStationListView.setVisible(false);
                break;
            case "WiFi":
                retailListView.setVisible(false);
                routeListView.setVisible(false);
                wifiListView.setVisible(true);
                bikeStationListView.setVisible(false);
                break;
            case "Route":
                retailListView.setVisible(false);
                routeListView.setVisible(true);
                wifiListView.setVisible(false);
                bikeStationListView.setVisible(false);
                break;
            case "Bike Station":
                retailListView.setVisible(false);
                routeListView.setVisible(false);
                wifiListView.setVisible(false);
                bikeStationListView.setVisible(true);
                break;
        }
    }

    /**
     * Method used to set the types of filtering option depending on the type of data.
     */
    private void changeChoiceBox() {
        switch (selectedType) {
            case "Retailer":
                filterTypeChoiceBox.getItems().addAll(retailTypeChoices);
                searchChoiceBox.getItems().addAll(retailTypeChoices);
                sortChoiceBox.getItems().addAll(retailSortChoices);
                break;
            case "WiFi":
                filterTypeChoiceBox.getItems().addAll(wifiTypeChoices);
                searchChoiceBox.getItems().addAll(wifiTypeChoices);
                sortChoiceBox.getItems().addAll(wifiSortChoices);
                break;
            case "Route":
                filterTypeChoiceBox.getItems().addAll(routeTypeChoices);
                searchChoiceBox.getItems().addAll(routeTypeChoices);
                sortChoiceBox.getItems().addAll(routeSortChoices);
                break;
            case "Bike Station":
                filterTypeChoiceBox.getItems().addAll(bikeStationTypeChoices);
                searchChoiceBox.getItems().addAll(bikeStationTypeChoices);
                sortChoiceBox.getItems().addAll(bikeStationSortChoices);
                break;
        }
    }

    /**
     * Set the content (imported files) of the lists ChoiceBox
     */
    public void changeListChoiceBox() {
        System.out.print("changeListChocieBox was called, currently has: ");
        System.out.println(listChoiceBox.getItems());
        System.out.flush();
        listChoiceBox.getItems().clear(); // Clear the list of available sets
        switch (selectedType) {
            case "Retailer":
                System.out.print("List to be added are : ");
                System.out.println(DataManager.getRetailersArrays().keySet());
                listChoiceBox.getItems().addAll(DataManager.getRetailersArrays().keySet());
                break;
            case "WiFi":
                listChoiceBox.getItems().addAll(DataManager.getWifiArrays().keySet());
                break;
            case "Route":
                listChoiceBox.getItems().addAll(DataManager.getRouteArrays().keySet());
                break;
            case "Bike Station":
                listChoiceBox.getItems().addAll(DataManager.getBikeStationArrays().keySet());
                break;
        }
        listChoiceBox.getSelectionModel().selectFirst(); //Display the listChoiceBox if it exists
    }

    /**
     * Method used to show the details available to filter depending on the filter type.
     */
    private void changeFilterDetailComboBox() {
        Filter filter = new Filter(); //Used to display the unique filter details.
        filterDetailComboBox.getItems().clear(); //Cleans the box
        System.out.print("Selected Filter Type: ");
        System.out.println(selectedFilterType);
        System.out.flush();

        switch (selectedType) {
            case "Retailer":
                switch (selectedFilterType) {
                    case "Street name":
                        filterDetails = filter.getUniqueStreet(displayedRetails);
                        break;
                    case "Primary":
                        filterDetails = filter.getUniquePrimary(displayedRetails);
                        break;
                    case "ZIP":
                        filterDetails = filter.getUniqueZip(displayedRetails);
                        break;
                    default:
                        break; //If filterTypeChoiceBox has not being selected.
                }
            case "WiFi":
                switch (selectedFilterType) {
                    case "Borough":
                        filterDetails = filter.getUniqueBoroughs(displayedWifis);
                        break;
                    case "Type":
                        filterDetails = filter.getUniqueTypes(displayedWifis);
                        break;
                    case "Provider":
                        filterDetails = filter.getUniqueProvider(displayedWifis);
                        break;
                    default:
                        break; //If filterTypeChoiceBox has not being selected.
                }
            case "Route":
                switch (selectedFilterType) {
                    case "Start location":
                        filterDetails = filter.getUniqueStart(displayedRoutes);
                        break;
                    case "End location":
                        filterDetails = filter.getUniqueEnd(displayedRoutes);
                        break;
                    case "Bike ID":
                        filterDetails = filter.getUniqueBikeId(displayedRoutes);
                        break;
                    case "Gender":
                        filterDetails = filter.getUniqueGender(displayedRoutes);
                        break;
                    default: //If filterTypeChoiceBox has not being selected.
                        break;
                }
            case "Bike Station":
                switch (selectedFilterType) {
                    case "Name":
                        filterDetails = filter.getUniqueName(displayedBikeStations);
                        break;
                    default: //If filterTypeBox has not been selected
                        break;
                }
        }
        filterDetailComboBox.getItems().addAll(filterDetails);
    }

    /**
     * Initialization method is called when the rawData.fxml is first loaded. Sets up the Observable array lists and
     * list views. Sets a default list view at the beginning. Cell factories are used to be able to store objects
     * from a list.
     */
    @FXML
    private void initialize() {
        System.out.println("The raw data viewer controller has been initialized");
        controller = this;

        statisticButton.setDisable(true);
        //Add all the options to the choice boxs and set a default option.
        dataTypeChoiceBox.getItems().addAll(dataTypeChoices);
        dataTypeChoiceBox.getSelectionModel().selectFirst();

        selectedType = dataTypeChoiceBox.getItems().get(0); //Keep track of the default selected type
        //Update the list view to be shown when the user selects a different type of data
        changeChoiceBox(); //Change types of data to filter depending on which type of data.
        dataTypeChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            errorFilterTypeLabel.setVisible(false);
            errorSearchTypeLabel.setVisible(false);
            errorRecordLabel.setVisible(false);
            listErrorLabel.setVisible(false);
            removeFavouriteButton.setVisible(false);
            addFavouriteButton.setVisible(true);
            statisticButton.setDisable(true);

            filterTypeChoiceBox.getItems().clear(); //Cleans the choice box.
            filterDetailComboBox.getItems().clear(); //Cleans the choice box.
            sortChoiceBox.getItems().clear(); //Clears the sort choice box
            searchChoiceBox.getItems().clear(); //Cleans the choice box.
            selectedType = dataTypeChoiceBox.getItems().get((Integer) newValue); //update the current selection


            System.out.print("The list choice box currently has: ");
            System.out.println(listChoiceBox.getItems());
            //
            changeListChoiceBox(); // Update the list of available sets

            //System.out.println("Selected: " + selectedType);
            changeChoiceBox(); //Change types of data to filter depending on which type of data.
            showSelectedListView(); //Change to the appropriate listview
            refreshAllListViews(); //Reset all the listView.


            //The type shown has changed, need to change the text showing the Add and Remove buttons
            if (selectedType == null) {
                addButton.setDisable(true);
                deleteButton.setDisable(true);
            } else if (selectedType.equals("Retailer")) {
                addButton.setText("Add Retailer");
                deleteButton.setText("Delete Retailer");
            } else if (selectedType.equals("WiFi")) {
                addButton.setText("Add Hotspot");
                deleteButton.setText("Delete Hotspot");
            } else if (selectedType.equals("Route")) {
                statisticButton.setDisable(false);
                addButton.setText("Add Route");
                deleteButton.setText("Delete Route");
            } else if (selectedType.equals("Bike Station")) {
                addButton.setText("Add Bike Station");
                deleteButton.setText("Remove Bike Station");
            }


        });

        filterTypeChoiceBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                selectedFilterType = filterTypeChoiceBox.getItems().get((Integer) newValue); //Update the current filter type.
                changeFilterDetailComboBox();
            } catch (IndexOutOfBoundsException e) {
                //Do nothing if the user has not selected anything to filter.
            }
        });

        //Sort the data when a sort by option is selected
        sortChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                sortBy(newValue);
            }
        });

        //Create the retailListView which is used to show retailers only.
        retailListView.setCellFactory(new Callback<ListView<Retailer>, ListCell<Retailer>>() {
            @Override
            public ListCell<Retailer> call(ListView<Retailer> param) {
                return new ListCell<Retailer>() {

                    @Override
                    protected void updateItem(Retailer retailer, boolean empty) {
                        super.updateItem(retailer, empty);
                        if (retailer != null) {
                            //Format the info to be displayed on the listview
                            String retailerInfo = String.format("Name: %s\nAddress: %s, %s", retailer.getName(), retailer.getAddressLine1(), retailer.getCity());
                            setText(retailerInfo);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        //Create the bikeStationListview which is used to show bikeStations only.
        bikeStationListView.setCellFactory(new Callback<ListView<BikeStation>, ListCell<BikeStation>>() {
            @Override
            public ListCell<BikeStation> call(ListView<BikeStation> param) {
                return new ListCell<BikeStation>() {

                    @Override
                    protected void updateItem(BikeStation bikeStation, boolean empty) {
                        super.updateItem(bikeStation, empty);
                        if (bikeStation != null) {
                            //Format the information to be displayed on the list view
                            String bikeStationInfo = String.format("Name: %s\nLocation: %s", bikeStation.getName(), bikeStation.getLocation());
                            setText(bikeStationInfo);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        //Create the retailListView which is used to show Wifi only.
        wifiListView.setCellFactory(new Callback<ListView<WiFi>, ListCell<WiFi>>() {
            @Override
            public ListCell<WiFi> call(ListView<WiFi> param) {
                return new ListCell<WiFi>() {

                    @Override
                    protected void updateItem(WiFi wifi, boolean empty) {
                        super.updateItem(wifi, empty);
                        if (wifi != null) {
                            //Format the info to be displayed on the listview
                            String wifiInfo = String.format("Name: %s\nType: %s\nCity: %s", wifi.getName(), wifi.getType(), wifi.getCity());
                            setText(wifiInfo);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });


        //Create the retailListView which is used to show Routes only.
        routeListView.setCellFactory(new Callback<ListView<Route>, ListCell<Route>>() {
            @Override
            public ListCell<Route> call(ListView<Route> param) {
                return new ListCell<Route>() {

                    @Override
                    protected void updateItem(Route route, boolean empty) {
                        super.updateItem(route, empty);
                        if (route != null) {
                            String routeInfo = String.format("Duration (Seconds): %.2f\nStart Station: %s\nEnd Station: %s", route.getRouteDuration(), route.getRouteStartStationName(), route.getRouteEndStationName());
                            setText(routeInfo);
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        // Update the displayed data when the user selects a list.
        listChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            currentList = (String) newValue;
            System.out.println("Has detected a change!");
            System.out.print("The current value for the list chocie box is: ");
            System.out.println(listChoiceBox.getSelectionModel().getSelectedIndex());

            //First must check if the change detected is the listChoiceBox being cleared of values.
            //If it isn't then it must mean it has been loaded a new set of lists to show the user.
            if (listChoiceBox.getSelectionModel().getSelectedItem() == null) {
                retailListView.setItems(null);
                routeListView.setItems(null);
                wifiListView.setItems(null);
                bikeStationListView.setItems(null);

                //There are no lists, disable the button to add and remove entries into the list view
                // as well as the button to delete the current list.
                disableAddDeleteButtons();

            } else {

                //There are lists, enable the button to add and remove entries into the list view
                // as well as the button to delete the current list.
                enableAddDeleteButtons();
                switch (selectedType) {
                    case "Retailer":
                        //Need to remove all the old values stored in allDetails
                        //allRetails.clear();
                        //Add the new values
                        allRetails = (DataManager.getRetailersArrays().get(listChoiceBox.getSelectionModel().getSelectedItem()).getList());
                        displayedRetails = allRetails; //Displays all the retailers by default
                        retailListView.setItems(displayedRetails);
                        break;
                    case "WiFi":
                        //Remove all the old values
                        //allWifis.clear();
                        //Add the new values
                        allWifis = (DataManager.getWifiArrays().get(listChoiceBox.getSelectionModel().getSelectedItem()).getList());
                        displayedWifis = allWifis; // Display all the wifis by default
                        wifiListView.setItems(displayedWifis);
                        break;
                    case "Route":
                        //Remove the old values
                        //allRoutes.clear();
                        //Add the new values
                        System.out.print("Chosen to load the list: ");
                        System.out.println(listChoiceBox.getSelectionModel().getSelectedItem());
                        System.out.flush();

                        allRoutes = (DataManager.getRouteArrays().get(listChoiceBox.getSelectionModel().getSelectedItem()).getList());
                        displayedRoutes = allRoutes; // Display all the route by default
                        routeListView.setItems(displayedRoutes);
                        break;
                    case "Bike Station":
                        allBikeStations = (DataManager.getBikeStationArrays().get(listChoiceBox.getSelectionModel().getSelectedItem()).getList());
                        displayedBikeStations = allBikeStations; //Display all the bike stations by default
                        bikeStationListView.setItems(displayedBikeStations);
                }
            }
        });

        showSelectedListView(); //Show the default selected list view
        //Load the default sets of data from CSV files stored in the application.
        loadValuesFromCSV(); //Loads the default data onto the associated list views on start up and sets the
        //list views to the related observable lists. Fill the listChoiceBox with the available lists to choose form.

        //Set all the list views to have the default lists showing
        changeListChoiceBox();

        retailListView.setItems(displayedRetails);
        wifiListView.setItems(displayedWifis);
        routeListView.setItems(displayedRoutes);
        bikeStationListView.setItems(displayedBikeStations);
        refreshAllListViews();
    }

    /**
     * Disables the button to add and delete a new entry into the current list view  as well as the button
     * to delete lists if there are no lists for the current data type.
     */
    private void disableAddDeleteButtons() {
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        removeListBtn.setDisable(true);
    }

    /**
     * Enables the buttons to add and delete entries into the current list view as well as the button to delete lists
     * if there are lists for the current data type.
     */
    private void enableAddDeleteButtons() {
        addButton.setDisable(false);
        deleteButton.setDisable(false);
        removeListBtn.setDisable(false);
    }

    /**
     * Method used to refresh all the list views to show the updated information.
     */
    private void refreshAllListViews() {
        retailListView.refresh();
        routeListView.refresh();
        wifiListView.refresh();
    }

    /**
     * Loads values from the stored CSV, adds these values with the corresponding list name to the HashMaps
     * in data manager which stores the values to be put on the ListViews and in the ListChoiceBox
     */
    private void loadValuesFromCSV() {

        //Load all the values from separate CSV to the associate
        try {
            Settings.setUpDirectories();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Attempting to load all values from the CSV folder to the ListViews");
        Settings.loadFiles();
    }


    /**
     * Removes an item from both the list view and the actual data list.
     * Note that there is no undo function and to regain the data the original CSV must be reloaded.
     */
    @FXML
    private void delete() {
        if (listChoiceBox.getSelectionModel().getSelectedItem() != null) {
            Object key = listChoiceBox.getSelectionModel().getSelectedItem();

            switch (selectedType) {
                case "Route":
                    //The route to be removed
                    Route routeToBeRemoved = routeListView.getSelectionModel().getSelectedItem();
                    //First remove the unwated route from all the routes and store the new list
                    DataManager.removeRoute(key, routeToBeRemoved);
                    //Remove the unwated route from the list that's being displayed and update the listview
                    //DataManager.removeRoute(key, routeToBeRemoved);
                    displayedRoutes.remove(routeToBeRemoved);
                    routeListView.setItems(displayedRoutes);
                    routeListView.refresh();
                    break;
                case "Retailer":
                    //The retailer to be removed
                    Retailer retailerToBeRemoved = retailListView.getSelectionModel().getSelectedItem();
                    //First remove the unwated retailer from all the retailers and store the new list
                    DataManager.removeretailer(key, retailerToBeRemoved);
                    //Remove the unwated retailer from the list that's being displayed and update the listview
                    //DataManager.removeretailer(key, retailerToBeRemoved);
                    displayedRetails.remove(retailerToBeRemoved);
                    retailListView.setItems(displayedRetails);
                    retailListView.refresh();
                    break;

                case "WiFi":
                    //The wifi to be removed
                    WiFi wifiToBeRemoved = wifiListView.getSelectionModel().getSelectedItem();
                    //First remove the unwanted wifi from all the wifis and store the new list
                    DataManager.removewifi(key, wifiToBeRemoved);
                    //Remove the unwanted wifi from the list that's being displayed and update the listview
                    //DataManager.removewifi(key, wifiToBeRemoved);
                    displayedWifis.remove(wifiToBeRemoved);
                    wifiListView.setItems(displayedWifis);
                    routeListView.refresh();
                    break;
                case "Bike Station":
                    //The bike station to be removed
                    BikeStation bikeStationToBeRemoved = bikeStationListView.getSelectionModel().getSelectedItem();
                    //First remove the unwanted bike station from all the bike station and stor ethe new list.
                    DataManager.removeBikeStation(key, bikeStationToBeRemoved);
                    //Remove the unwanted bike station form the list that's currently being displayed and update the listview
                    displayedBikeStations.remove(bikeStationToBeRemoved);
                    bikeStationListView.setItems(displayedBikeStations);
                    bikeStationListView.refresh();
                    break;
            }

        }

    }


}