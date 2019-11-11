package GUI;

import Data.SyntaxChecker;
import Model.Location;
import Model.WiFi;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import static GUI.UserAlerts.error;

/**
 * Displays in-depth details about the specific wifi. The details can be edited and the program checks if it's a valid
 * entry. Also provides functionality to create a and return a new WiFi instance based on the values entered
 * in the text fields.
 */
public class WifiInfoController implements Editable {
    @FXML
    private TextField nameTxtFld = new TextField();
    @FXML
    private TextField boroughTxtFld = new TextField();
    @FXML
    private TextField typeTxtFld = new TextField();
    @FXML
    private TextField cityTxtFld = new TextField();
    @FXML
    private TextField providerTxtFld = new TextField();
    @FXML
    private TextField remarksTxtFld = new TextField();
    @FXML
    private TextField ssidTxtFld = new TextField();
    @FXML
    private TextField idTxtFld = new TextField();
    @FXML
    private TextField latTxtFld = new TextField();
    @FXML
    private TextField longTxtFld = new TextField();

    private WiFi chosenWifi;
    private ListView<WiFi> listView;

    /**
     * Method called to pass values to this controller to be used.
     *
     * @param wifi         The point of data whose infromation will be displayed and potentially edited.
     * @param wifiListView The listView which must be refreshed to reflect any changes.
     */
    void initData(WiFi wifi, ListView<WiFi> wifiListView) {
        chosenWifi = wifi;
        listView = wifiListView;
    }

    /**
     * Method to set the appropriate fields
     *
     * @param isNew Checks if the data is new or it is an existing data.
     */
    void setTextFields(boolean isNew) {
        nameTxtFld.setText(chosenWifi.getName());
        boroughTxtFld.setText(chosenWifi.getBorough());
        typeTxtFld.setText(chosenWifi.getType());
        cityTxtFld.setText(chosenWifi.getCity());
        providerTxtFld.setText(chosenWifi.getProvider());
        remarksTxtFld.setText(chosenWifi.getRemarks());
        ssidTxtFld.setText(chosenWifi.getSsid());
        idTxtFld.setText(String.format("%d", chosenWifi.getObjectId()));
        latTxtFld.setText(Double.toString(chosenWifi.getLocation().getLatitude()));
        longTxtFld.setText(Double.toString(chosenWifi.getLocation().getLongitude()));
        latTxtFld.setDisable(!isNew);
        longTxtFld.setDisable(!isNew);


    }

    /**
     * When the user clicks the applyEditsBtn, the entered values in the text fields will be set to the
     * appropriate properties in the Wifi object.
     */
    boolean applyEdits() {
        if (areFieldsEmpty()) {
            return false;
        }

        if (!areFieldsValid()) {
            return false;
        }

        chosenWifi.setName(nameTxtFld.getText());
        chosenWifi.setBorough(boroughTxtFld.getText());
        chosenWifi.setType(typeTxtFld.getText());
        chosenWifi.setCity(cityTxtFld.getText());
        chosenWifi.setProvider(providerTxtFld.getText());
        chosenWifi.setRemarks(remarksTxtFld.getText());
        chosenWifi.setSsid(ssidTxtFld.getText());
        chosenWifi.setObjectId(Integer.parseInt(idTxtFld.getText()));
        listView.refresh();
        return true;
    }


    /**
     * If any fields are empty (has the empty string) return true otherwise return false.
     *
     * @return True if any of the fields are emtpy, otherwise return false.
     */
    public boolean areFieldsEmpty() {
        if (nameTxtFld.getText().equals("") ||
                boroughTxtFld.getText().equals("") ||
                typeTxtFld.getText().equals("") ||
                cityTxtFld.getText().equals("") ||
                providerTxtFld.getText().equals("") ||
                remarksTxtFld.getText().equals("") ||
                ssidTxtFld.getText().equals("") ||
                idTxtFld.getText().equals("") ||
                latTxtFld.getText().equals("") ||
                longTxtFld.getText().equals("")
                ) {
            error("No field can be left blank.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * If all fields are valid and not emtpy return ture, otherwise return false.
     *
     * @return True if the fields contain valid information and false otherwise
     */
    public boolean areFieldsValid() {
        if (areFieldsEmpty()) {
            return false;
        }

        if (!SyntaxChecker.latValid(latTxtFld.getText()) || !SyntaxChecker.longValid(longTxtFld.getText())) {
            error("Latitude and Longitude must be decimal numbers between -180 and 180.");
            return false;
        } else if (!SyntaxChecker.isInt(idTxtFld.getText())) {
            error("The WiFi ID must be an integer.");
            return false;
        }

        return true;
    }

    /**
     * Use the current values in the text fields to create a new WiFi instance. No error checking is done.
     *
     * @return Return a new WiFi instance using the current values in the text fields.
     */
    public WiFi getNewWiFi() {
        Location newLocation = new Location(Double.parseDouble(latTxtFld.getText()), Double.parseDouble(longTxtFld.getText()));
        String newName = nameTxtFld.getText();
        int newObjectId = Integer.parseInt(idTxtFld.getText());
        String newBorough = boroughTxtFld.getText();
        String newType = typeTxtFld.getText();
        String newProvider = providerTxtFld.getText();
        String newRemarks = remarksTxtFld.getText();
        String newCity = cityTxtFld.getText();
        String newSsid = ssidTxtFld.getText();

        return new WiFi(newLocation, newName, newObjectId, newBorough, newType, newProvider, newRemarks, newCity, newSsid);
    }


}
