package Data;

/**
 * Contains methods to check the syntax of user inputted data
 */
public abstract class SyntaxChecker {

    /**
     * Returns whether the given string contains only letters
     *
     * @param string String to check
     * @return Whether the string contains only letters
     */
    public static Boolean isAlpha(String string) {
        return string.matches("[a-zA-Z]+");
    }

    /**
     * Returns whether the given string is a number
     *
     * @param string String to check
     * @return Whether the given string is a number
     */
    public static Boolean isNum(String string) {
        return string.matches("-?\\d+(\\.\\d+)?");
    }

    /**
     * Returns whether the given string is an integer
     *
     * @param string String to check
     * @return Whether the given string is an integer
     */
    public static Boolean isInt(String string) {
        return string.matches("-?\\d+");
    }

    /**
     * Returns whether the given latitude value is correct
     *
     * @param stringLat Latitude Value
     * @return Whether the latitude value is valid
     */
    public static Boolean latValid(String stringLat) {
        if (isNum(stringLat)) {
            double latitude = Double.parseDouble(stringLat);
            return latitude >= -90 && latitude <= 90;
        } else {
            return false;
        }
    }

    /**
     * Returns whether the given latitude value is correct
     *
     * @param stringLong longitude value is valid
     * @return Whether the longitude value is valid
     */
    public static Boolean longValid(String stringLong) {
        if (isNum(stringLong)) {
            double latitude = Double.parseDouble(stringLong);
            return latitude >= -180 && latitude <= 180;
        } else {
            return false;
        }
    }

    /**
     * Returns whether the given string is a valid gender
     *
     * @param string gender given
     * @return Returns whether the given gender is valid
     */
    public static Boolean checkGender(String string) {
        return string.toLowerCase().equals("male") || string.toLowerCase().equals("female") || string.toLowerCase().equals("unknown");
    }
}
