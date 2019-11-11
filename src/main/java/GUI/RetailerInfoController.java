package GUI;

import Data.SyntaxChecker;
import Model.Location;
import Model.Retailer;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import static GUI.UserAlerts.error;

/**
 * Displays in-depth details about the specific retailer. Details for attributes are displayed in text fields.
 * The details can be edited using the text fields and the program checks if it's a valid entry. Also provides
 * functionality to create and return a new Retailer instance using the values in the text fields.
 */
public class RetailerInfoController implements Editable {
    @FXML
    private TextField nameTxtFld = new TextField();
    @FXML
    private TextField address1TxtFld = new TextField();
    @FXML
    private TextField address2TxtFld = new TextField();
    @FXML
    private TextField stateTxtFld = new TextField();
    @FXML
    private TextField cityTxtFld = new TextField();
    @FXML
    private TextField primaryTxtFld = new TextField();
    @FXML
    private TextField secondaryTxtFld = new TextField();
    @FXML
    private TextField zipTxtFld = new TextField();
    @FXML
    private TextField latTxtFld = new TextField();
    @FXML
    private TextField longTxtFld = new TextField();

    private Retailer chosenRetailer;
    private ListView<Retailer> listView;

    /**
     * Method called to pass values to this controller to be used.
     *
     * @param retailer       The point of data whose infromation will be displayed and potentially edited.
     * @param retailListView The listView which must be refreshed to reflect any changes.
     */
    void initData(Retailer retailer, ListView<Retailer> retailListView) {
        chosenRetailer = retailer;
        listView = retailListView;
    }

    /**
     * Method to set the appropriate fields
     *
     * @param isNew Checks if the data is a new data or existing data.
     */
    void setTextFields(boolean isNew) {
        nameTxtFld.setText(chosenRetailer.getName());
        address1TxtFld.setText(chosenRetailer.getAddressLine1());
        address2TxtFld.setText(chosenRetailer.getAddressLine2());
        stateTxtFld.setText(chosenRetailer.getState());
        cityTxtFld.setText(chosenRetailer.getCity());
        primaryTxtFld.setText(chosenRetailer.getPrimary());
        secondaryTxtFld.setText(chosenRetailer.getSecondary());
        zipTxtFld.setText(chosenRetailer.getZip());
        latTxtFld.setText(Double.toString(chosenRetailer.getLocation().getLatitude()));
        longTxtFld.setText(Double.toString(chosenRetailer.getLocation().getLongitude()));
        latTxtFld.setDisable(!isNew);
        longTxtFld.setDisable(!isNew);

    }

    /**
     * When the user clicks the applyEditsBtn, the entered values in the text fields will be set to the
     * appropriate properties in the retailer.
     *
     * @return boolean return true if it's valid entry. Else it returns false.
     */
    boolean applyEdits() {

        if (areFieldsEmpty()) {
            //nputError dialog is shown from method call
            return false;
        }


        if (!areFieldsValid()) {
            //inputErorr dialog is shown from the method call
            return false;
        }

        chosenRetailer.setName(nameTxtFld.getText());
        chosenRetailer.setAddressLine1(address1TxtFld.getText());
        chosenRetailer.setAddressLine2(address2TxtFld.getText());
        chosenRetailer.setState(stateTxtFld.getText());
        chosenRetailer.setCity(cityTxtFld.getText());
        chosenRetailer.setPrimary(primaryTxtFld.getText());
        chosenRetailer.setSecondary(secondaryTxtFld.getText());
        chosenRetailer.setZip((zipTxtFld.getText()));

        listView.refresh();
        return true;
    }

    /**
     * Check if the text fields are empty (have the empty string ""), if any of them are emtpy return true.
     * Otherwise return false.
     *
     * @return True if any of the fields are empty, false otherwise.
     */
    @Override
    public boolean areFieldsEmpty() {
        if (nameTxtFld.getText().equals("") ||
                address1TxtFld.getText().equals("") ||
                stateTxtFld.getText().equals("") ||
                cityTxtFld.getText().equals("") ||
                primaryTxtFld.getText().equals("") ||
                latTxtFld.getText().equals("") ||
                longTxtFld.getText().equals("")
                ) {
            error("Name, adress line 1, state, city and primary are requried fields.");
            return true;
        } else {
            return false;
        }
    }

    /**
     * Check if any of the fields are empty or if any of the fields are invalid.
     *
     * @return True if all fields are valid and non-empty, false otherwise.
     */
    @Override
    public boolean areFieldsValid() {
        if (areFieldsEmpty()) {
            return false;
        } else if (!SyntaxChecker.isNum(zipTxtFld.getText())) {
            error("Zip must be an integer");
            return false;
        } else if (!SyntaxChecker.latValid(latTxtFld.getText()) || !SyntaxChecker.longValid(longTxtFld.getText())) {
            error("Latitude and longitude must be decimal numbers");
            return false;
        } else {
            return true;
        }
    }

    /**
     * Create a new Retailer instance using the current values entered in the text fields. No error checking
     * occurs within this method.
     *
     * @return A new Retailer instance based on the values entered in the text fields.
     */
    public Retailer getNewRetailer() {
        Location newLocation = new Location(Double.parseDouble(latTxtFld.getText()),
                Double.parseDouble(longTxtFld.getText()));
        String newName = nameTxtFld.getText();
        String newAddressLine1 = address1TxtFld.getText();
        String newAddressLine2 = address2TxtFld.getText();
        String newCity = cityTxtFld.getText();
        String newState = stateTxtFld.getText();
        String newPrimary = primaryTxtFld.getText();
        String newSecondary = secondaryTxtFld.getText();
        String newZip = zipTxtFld.getText();

        return new Retailer(newLocation, newName, newAddressLine1, newAddressLine2, newCity, newState,
                newPrimary, newSecondary, newZip);

    }

}