package GUI;

import java.text.ParseException;

/**
 * Interface that ensures any pop that allows the the attributes of the instance it is displaying to be edited
 * implements methods to santy and validity checking.
 */
interface Editable {

    /**
     * Check if the current values in the text fields which display information about the instance are valid.
     * Will also check if all the mandatory fields are not-empty.
     *
     * @return true if all the fields are valid and no mandatory text fields are empty.
     */
    boolean areFieldsValid() throws ParseException;

    /**
     * Check if all the non-mantory fields are empty or not.
     *
     * @return true if all mandatory fields are empty, false otherwise.
     */
    boolean areFieldsEmpty();
}
