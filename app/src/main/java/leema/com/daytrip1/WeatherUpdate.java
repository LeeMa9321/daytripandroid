package leema.com.daytrip1;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by leema on 2017-11-09.
 */

public class WeatherUpdate {
    private String currentTemperature;
    private String currentSummary;
    private String precipProb;

    public static WeatherUpdate fromJSON(JSONObject jsonObject) {
        WeatherUpdate weatherUpdate = new WeatherUpdate();
        try {
            weatherUpdate.currentTemperature = Double.toString(jsonObject.getJSONObject("currently").getDouble("temperature"));
            weatherUpdate.currentSummary = jsonObject.getJSONObject("currently").getString("summary");
            weatherUpdate.precipProb = Double.toString(jsonObject.getJSONObject("currently").getDouble("precipProbability"));
            return weatherUpdate;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getCurrentTemperature() {
        return currentTemperature + "°C" ;
    }

    public String getCurrentSummary() {
        return currentSummary;
    }

    public String getPrecipProb() {
        return precipProb + "% Probability of Precipitation";
    }
}
