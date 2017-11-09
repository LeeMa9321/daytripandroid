package leema.com.daytrip1;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class WeatherPage extends AppCompatActivity {

    final int REQUEST_CODE = 123;

    final String WEATHER_URL = "https://api.darksky.net/forecast/";

    final private String API_KEY = "0f86ecb681eebd0c25e08cee34a88d40/";

    final long UPDATE_TIME = 5000;

    final float MIN_DISTANCE = 1000;

    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    private TextView weatherView;
    private TextView currentSumm;
    private TextView precipProb;

    private String NEW_URL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_page);

        weatherView = findViewById(R.id.weather_text);
        currentSumm = findViewById(R.id.summary_current);
        precipProb = findViewById(R.id.conditions_future);

        ImageButton goBack = findViewById(R.id.back_weather);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goBackWeather = new Intent(WeatherPage.this, FullscreenActivity.class);
                startActivity(goBackWeather);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        getWeatherForCurrentLocation();

    }

    private void getWeatherForCurrentLocation() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String longitude = String.valueOf(location.getLongitude());
                String latitude = String.valueOf(location.getLatitude());

                Log.d("DayTrip" , "Longitude: " + longitude);
                Log.d("DayTrip", "Latitude: " + latitude);

                NEW_URL = WEATHER_URL + API_KEY + latitude + "," + longitude + "?units=auto";

                networking();

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(this, new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);

            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, UPDATE_TIME, MIN_DISTANCE, mLocationListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(REQUEST_CODE == requestCode) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getWeatherForCurrentLocation();
            }
        }
        else {

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void networking() {
        Log.d("DayTrip" , "IS this working?");
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(NEW_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DayTrip" , "JSON: " + response.toString());

                WeatherUpdate weatherUpdate = WeatherUpdate.fromJSON(response);
                updateUI(weatherUpdate);
            }
        });


    }

    private void updateUI(WeatherUpdate weatherUpdate) {
        weatherView.setText(weatherUpdate.getCurrentTemperature());
        currentSumm.setText(weatherUpdate.getCurrentSummary());
        precipProb.setText(weatherUpdate.getPrecipProb());
    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }
}
