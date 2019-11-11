package Data;


import GUI.UserAlerts;
import com.lynden.gmapsfx.javascript.object.LatLong;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that contains methods to receive a GeoCoder request from Google and process that request to
 * receive co-ordinates.
 */
public class GeoCoder {


    /**
     * Method to return a JSON object of a request for Google's GeoCoding from an address that contains
     * that address's co-ordinates.
     *
     * @param placesName The address to be converted into co-ordinates.
     * @return JSONObject JSON containing the address's co-ordinates.
     */
    public static JSONObject getLocationFormGoogle(String placesName) {

        String formattedPlacesName = placesName.replaceAll(" ", "+");
        formattedPlacesName = formattedPlacesName.replaceAll(",", "+");

        HttpGet httpGet = new HttpGet("http://maps.google.com/maps/api/geocode/json?address=" + formattedPlacesName + "&ka&sensor=false");
        DefaultHttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int bit;
            while ((bit = stream.read()) != -1) {
                stringBuilder.append((char) bit);
            }
        } catch (ClientProtocolException e) {
            UserAlerts.error("An error occurred while communicating with http://google.com");
        } catch (IOException e) {
            UserAlerts.error("An unexpected error occurred");
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {

            e.printStackTrace();
        }

        return jsonObject;
    }


    /**
     * Method to return a JSON object of a request for Google's GeoCoding from an address that contains
     * that address's co-ordinates.
     *
     * @param jsonObject The JSON containing the address's co-ordinates.
     * @return LatLong The address's co-ordinates.
     */
    public static LatLong getLatLng(JSONObject jsonObject) {

        Double lon = 0d;
        Double lat = 0d;

        try {

            lon = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");

            lat = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new LatLong(lat, lon);

    }

}
