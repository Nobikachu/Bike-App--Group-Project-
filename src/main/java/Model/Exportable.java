package Model;

import java.util.List;

/**
 * Interface provides functionality for an object to be converted to a CSVRecord to be used by the CSVWriter
 */
public interface Exportable {

    /**
     * Returns a representation of the object (as a list of attributes) to be stored in a comma delimited (CSV) file.
     *
     * @return a representation of the object as a List.
     */
    List toCSV();

}
