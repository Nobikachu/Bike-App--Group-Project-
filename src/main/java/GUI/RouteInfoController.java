package GUI;

import Data.SyntaxChecker;
import Model.Route;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.text.ParseException;

import static GUI.UserAlerts.error;
import static Model.Route.dateParser;

/**
 * Displays in-depth details about the specific route. The details can be edited and the program checks if it's a
 * valid entry. Also provides functionality to create and return a new route instance using the current values
 * within the text fields.
 */
public class RouteInfoController implements Editable {
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

    private Route chosenRoute;
    private ListView<Route> listView;

    /**
     * Method called to pass values to this controller to be used.
     *
     * @param route         The point of data whose infromation will be displayed and potentially edited.
     * @param routeListView The listView which must be refreshed to reflect any changes.
     */
    void initData(Route route, ListView<Route> routeListView) {
        chosenRoute = route;
        listView = routeListView;
    }

    /**
     * Method called to pass values to this controller to be used.
     *
     * @param routeList     The points of data whose infromation will be displayed and potentially edited.
     * @param routeListView The listView which must be refreshed to reflect any changes.
     */
    void initData(ObservableList<Route> routeList, ListView<Route> routeListView) {
        listView = routeListView;
    }

    /**
     * Method to set the appropriate fields
     *
     * @param isNew Checks if the data is new or it is an exisiting data.
     */
    void setTextFields(boolean isNew) {
        routeDurationTxtFld.setText(String.valueOf(chosenRoute.getRouteDuration()));
        startTimeTxtFld.setText(String.valueOf(chosenRoute.getRouteStartTime()));
        stopTimeTxtFld.setText(String.valueOf(chosenRoute.getRouteEndTime()));
        startStationIdTxtFld.setText(chosenRoute.getRouteStartStationID());
        startStationNameTxtFld.setText(chosenRoute.getRouteStartStationName());
        startStationLatitudeTxtFld.setText(String.valueOf(chosenRoute.getRouteStartStationLatitude()));
        startStationLongitudeTxtFld.setText(String.valueOf(chosenRoute.getRouteStartStationLongitude()));
        endStationIdTxtFld.setText(chosenRoute.getRouteEndStationID());
        endStationNameTxtFld.setText(chosenRoute.getRouteEndStationName());
        endStationLatitudeTxtFld.setText(String.valueOf(chosenRoute.getRouteEndStationLatitude()));
        endStationLongitudeTxtFld.setText(String.valueOf(chosenRoute.getRouteEndStationLongitude()));
        bikeIdTxtFld.setText(chosenRoute.getRouteBikeID());
        userTypeTxtFld.setText(chosenRoute.getRouteUserType());
        birthYearTxtFld.setText(String.valueOf(chosenRoute.getRouteBirthYear()));
        genderTxtFld.setText(chosenRoute.getRouteGender());
    }

    /**
     * When the user clicks the applyEditsBtn, the entered values in the text fields will be set to the
     * appropriate properties in the route object.
     *
     * @return Returns true if the input is valid.
     * @throws ParseException When an error occurs unexpectedly while parsing.
     */
    boolean applyEdits() throws ParseException {
        if (areFieldsEmpty()) {
            return false;
        } else if (!areFieldsValid()) {
            return false;
        }

        chosenRoute.setRouteDuration(Double.parseDouble(routeDurationTxtFld.getText()));
        chosenRoute.setRouteStartTime((startTimeTxtFld.getText()));
        chosenRoute.setRouteEndTime((stopTimeTxtFld.getText()));
        chosenRoute.setRouteStartStationID(startStationIdTxtFld.getText());
        chosenRoute.setRouteStartStationName(startStationNameTxtFld.getText());
        chosenRoute.setRouteStartStationLatitude(Double.parseDouble(startStationLatitudeTxtFld.getText()));
        chosenRoute.setRouteStartStationLongitude(Double.parseDouble(startStationLongitudeTxtFld.getText()));
        chosenRoute.setRouteEndStationID(endStationIdTxtFld.getText());
        chosenRoute.setRouteEndStationName(endStationNameTxtFld.getText());
        chosenRoute.setRouteEndStationLatitude(Double.parseDouble(endStationLatitudeTxtFld.getText()));
        chosenRoute.setRouteEndStationLongitude(Double.parseDouble(endStationLongitudeTxtFld.getText()));
        chosenRoute.setRouteBikeID(bikeIdTxtFld.getText());
        chosenRoute.setRouteUserType(userTypeTxtFld.getText());
        chosenRoute.setRouteBirthYear(birthYearTxtFld.getText());
        chosenRoute.setRouteGender(genderTxtFld.getText());

        listView.refresh();
        return true;
    }

    /**
     * Return true if any the fields have do not have a value entered in them (i.e. not the empty string "").
     * Show alert dialog informing user of this issue if need be.
     *
     * @return True if any of the fields are empty, otherwise returns false.
     */
    @Override
    public boolean areFieldsEmpty() {
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
            error("No field may be left blank");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return true if all the fields have valid entires and not empty. Show alert dialog with the apppriate message
     * if needed.
     *
     * @return True if all fields valid are not empty, otherwise return false.
     * @throws ParseException When an error occurs unexpectedly while parsing.
     */
    @Override
    public boolean areFieldsValid() throws ParseException {
        if (dateParser.parse(startTimeTxtFld.getText()).after(dateParser.parse(stopTimeTxtFld.getText()))) {
            error("Start time cannot be before stop time");
            return false;
        } else if (!SyntaxChecker.isNum(routeDurationTxtFld.getText())) {
            error("Route duration must be an integer");
            return false;
        } else if (!SyntaxChecker.isInt(startStationIdTxtFld.getText()) || !SyntaxChecker.isInt(endStationIdTxtFld.getText()) || !SyntaxChecker.isInt(bikeIdTxtFld.getText())) {
            error("IDs must be integers");
            return false;
        } else if (!SyntaxChecker.latValid(startStationLatitudeTxtFld.getText()) || !SyntaxChecker.latValid(endStationLatitudeTxtFld.getText())) {
            error("Latitude must be a number between -90 and 90");
            return false;
        } else if (!SyntaxChecker.longValid(startStationLongitudeTxtFld.getText()) || !SyntaxChecker.longValid(endStationLongitudeTxtFld.getText())) {
            error("Latitude must be a number between -180 and 180");
            return false;
        } else if (!birthYearTxtFld.getText().equals("\\N") && !SyntaxChecker.isNum(birthYearTxtFld.getText())) {
            error("Birth year must be an integer or \"\\N\"");
            return false;

        } else if (!SyntaxChecker.checkGender(genderTxtFld.getText())) {
            error("Gender must be \"Male\", \"Female\", or \"Unknown.\"");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Return a new Route instance using the current values in the text fields. No error checking is done.
     *
     * @return A new instance of Route using the current values in the text fields.
     * @throws ParseException When an error occurs unexpectedly while parsing.
     */
    public Route getNewRoute() throws ParseException {
        double newRouteDuration = Double.parseDouble(routeDurationTxtFld.getText());
        String newStartTime = startTimeTxtFld.getText();
        String newStopTime = stopTimeTxtFld.getText();
        String newStartStationId = startStationIdTxtFld.getText();
        String newStartStationName = startStationNameTxtFld.getText();
        double newStartStationLatitude = Double.parseDouble(startStationLatitudeTxtFld.getText());
        double newStartStationLongitude = Double.parseDouble(startStationLongitudeTxtFld.getText());
        String newEndStationId = endStationIdTxtFld.getText();
        String newEndStationName = endStationNameTxtFld.getText();
        double newEndStationLatitude = Double.parseDouble(endStationLatitudeTxtFld.getText());
        double newEndStationLongitude = Double.parseDouble(endStationLongitudeTxtFld.getText());
        String newBikeId = bikeIdTxtFld.getText();
        String newUserType = userTypeTxtFld.getText();
        String newBirthYear = birthYearTxtFld.getText();
        String newGender = genderTxtFld.getText();

        return new Route(newRouteDuration, newStartTime, newStopTime, newStartStationId, newStartStationName,
                newStartStationLatitude, newStartStationLongitude, newEndStationId, newEndStationName,
                newEndStationLatitude, newEndStationLongitude, newBikeId, newUserType, newBirthYear, newGender);
    }

}
