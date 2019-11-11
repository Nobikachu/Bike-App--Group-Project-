package GUI;

import Model.Route;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.text.ParseException;
import java.util.Date;

import static GUI.UserAlerts.error;

/**
 * Controller for the pop which is used to add new routes
 */
public class AddRouteController {
    @FXML
    private TextField routeDurationTxtFld = new TextField();
    @FXML
    private TextField startTimeTxtFld = new TextField();
    @FXML
    private TextField stopTimeTxtFld = new TextField();
    @FXML
    private TextField startStationIdTxtFld = new TextField();
    @FXML
    private TextField startStationNameTxtFld = new TextField();
    @FXML
    private TextField startStationLatitudeTxtFld = new TextField();
    @FXML
    private TextField startStationLongitudeTxtFld = new TextField();
    @FXML
    private TextField endStationIdTxtFld = new TextField();
    @FXML
    private TextField endStationNameTxtFld = new TextField();
    @FXML
    private TextField endStationLatitudeTxtFld = new TextField();
    @FXML
    private TextField endStationLongitudeTxtFld = new TextField();
    @FXML
    private TextField bikeIdTxtFld = new TextField();
    @FXML
    private TextField userTypeTxtFld = new TextField();
    @FXML
    private TextField birthYearTxtFld = new TextField();
    @FXML
    private TextField genderTxtFld = new TextField();

    private ListView<Route> listView;
    private ObservableList<Route> routes;
    private Stage root;

    /**
     * Method used to pass data to the from the RawDataViewercontroller to the AddRouteController
     *
     * @param stage         The stage which is holding the scene containing the fxml.
     * @param routeList     The list of all the routes in the currently shown list.
     * @param routeListView The list view showing the route entries.
     */
    public void initData(Stage stage, ObservableList<Route> routeList, ListView<Route> routeListView) {
        routes = routeList;
        listView = routeListView;
        root = stage;
    }


    /**
     * When the 'Add' button is pressed the entered values are used to create a new Route entry.
     */
    @FXML
    public void createEntry() {
        Date start;
        Date end;
        try {
            start = Route.dateParser.parse(startTimeTxtFld.getText());
        } catch (ParseException e) {
            error("Error: Improperly formatted start date/time.\nShould be yyyy-mm-dd HH:MM:SS");
            return;
        }
        try {
            end = Route.dateParser.parse(stopTimeTxtFld.getText());
        } catch (ParseException e) {
            error("Error: Improperly formatted end date/time.\nShould be yyyy-mm-dd HH:MM:SS");
            return;
        }
        if (
                routeDurationTxtFld.getText().equals("") ||
                        startTimeTxtFld.getText().equals("") ||
                        stopTimeTxtFld.getText().equals("") ||
                        startStationIdTxtFld.getText().equals("") ||
                        startStationNameTxtFld.getText().equals("") ||
                        startStationLatitudeTxtFld.getText().equals("") ||
                        startStationLongitudeTxtFld.getText().equals("") ||
                        endStationIdTxtFld.getText().equals("") ||
                        endStationNameTxtFld.getText().equals("") ||
                        endStationLatitudeTxtFld.getText().equals("") ||
                        endStationLongitudeTxtFld.getText().equals("") ||
                        bikeIdTxtFld.getText().equals("") ||
                        userTypeTxtFld.getText().equals("") ||
                        birthYearTxtFld.getText().equals("") ||
                        genderTxtFld.getText().equals("")
                ) {
            error("No entries may be left blank");
        } else if (start.after(end)) {
            error("Start time cannot be before stop time");
        }
        Route newRoute = new Route(Double.parseDouble(routeDurationTxtFld.getText()),
                start,
                end,
                startStationIdTxtFld.getText(),
                startStationNameTxtFld.getText(),
                Double.parseDouble(startStationLatitudeTxtFld.getText()),
                Double.parseDouble(startStationLongitudeTxtFld.getText()),
                endStationIdTxtFld.getText(),
                endStationIdTxtFld.getText(),
                Double.parseDouble(endStationLatitudeTxtFld.getText()),
                Double.parseDouble(endStationLongitudeTxtFld.getText()),
                bikeIdTxtFld.getText(),
                userTypeTxtFld.getText(),
                birthYearTxtFld.getText(),
                genderTxtFld.getText()
        );

        routes.add(newRoute);

        RawDataViewerController.controller.updateRouteList(routes);
        listView.refresh();
        root.close();
    }

    /**
     * User clicked the button to close the pop up, close the stage.
     */
    @FXML
    public void closePopUp() {
        root.close();
    }

}
