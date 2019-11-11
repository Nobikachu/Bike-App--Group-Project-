package GUI;

import Data.SyntaxChecker;
import Model.BikeStation;
import Model.Location;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import static GUI.UserAlerts.error;

/**
 * Controller for the bikestationInfo.fxml which displays all attributes for a bike station in text
 * fields. The user can enter new values for text fields and this controller provides functionality to
 * check the entered values for validity and also able to return a new BikeStation object using the current
 * values in the text fields.
 */
public class BikeStationInfoController implements Editable {
    @FXML
    private TextField nameTxtFld = new TextField();
    @FXML
    private TextField latitudeTxtFld = new TextField();
    @FXML
    private TextField longitudeTxtFld = new TextField();


    private BikeStation bikeStation;
    private ListView<BikeStation> bikeStationListView;

    /**
     * Method called to pass values to this controller to be used.
     *
     * @param station  The point of data whose infromation will be displayed and potentially edited.
     * @param listView The listView which must be refreshed to reflect any changes.
     */
    void initData(BikeStation station, ListView<BikeStation> listView) {
        bikeStation = station;
        bikeStationListView = listView;
    }

    /**
     * Method to set the appropriate fields
     *
     * @param isNew Checks if the data is new or it is an existing data.
     */
    void setTextFields(boolean isNew) {
        nameTxtFld.setText(bikeStation.getName());
        latitudeTxtFld.setText(String.format("%f", bikeStation.getLocation().getLatitude()));
        longitudeTxtFld.setText(String.format("%f", bikeStation.getLocation().getLongitude()));
    }

    /**
     * The user has clicked the applyEditsBtn meaning they want to save any changes they've made in the GUI
     * to the object. Call the controller's method to set the new values to the object.
     */
    @FXML
    public void applyEdits() {
        if (areFieldsEmpty()) {
            return;
        }

        if (!areFieldsValid()) {
            return;
        }

        bikeStation.setName(nameTxtFld.getText());
        bikeStation.getLocation().setLatitude(Double.parseDouble(latitudeTxtFld.getText()));
        bikeStation.getLocation().setLongitude(Double.parseDouble(longitudeTxtFld.getText()));

        bikeStationListView.refresh();
    }


    /**
     * Check if any of the text fields are empty.
     *
     * @return True if any of the fields are empty. False otherwise.
     */
    @Override
    public boolean areFieldsEmpty() {
        if (nameTxtFld.getText().equals("") ||
                latitudeTxtFld.getText().equals("") ||
                longitudeTxtFld.getText().equals("")) {
            error("The name, latitude and longitude are required fields.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if any of the fields are empty and or if any of the fields are invalid.
     *
     * @return True if all the fields are valid and non-empty, false otherwise.
     */
    @Override
    public boolean areFieldsValid() {
        if (areFieldsEmpty()) {
            return false;
        } else {
            if (!SyntaxChecker.latValid(latitudeTxtFld.getText())) {
                error("You have entered an invalid latitude.");
                return false;
            }

            if (!SyntaxChecker.longValid(longitudeTxtFld.getText())) {
                error("You have entered an invalid longitude.");
                return false;
            }
            return true;
        }
    }

    /**
     * Use the current values in the text fields to create a new Bike Station instance. This method does
     * not do error checking.
     *
     * @return A BikeStation instance using the current entered values in the text fields.
     */
    public BikeStation getNewBikeStation() {
        String newName = getNameEntry();
        String newLatitude = getLatitudeEntry();
        String newLongitude = getLongitudeEntry();

        return new BikeStation(new Location(Double.parseDouble(newLatitude), Double.parseDouble(newLongitude)), newName);
    }


    /**
     * Returns the text currently in the name text field.
     *
     * @return The current text in the name text field.
     */
    private String getNameEntry() {
        return nameTxtFld.getText();
    }

    /**
     * Returns the text currently in the latitude text field.
     *
     * @return The current text in the latitude text field.
     */
    private String getLatitudeEntry() {
        return latitudeTxtFld.getText();
    }

    /**
     * Returns the text currently in the longitude text field.
     *
     * @return The current text in the longitude text field.
     */
    private String getLongitudeEntry() {
        return longitudeTxtFld.getText();
    }

}