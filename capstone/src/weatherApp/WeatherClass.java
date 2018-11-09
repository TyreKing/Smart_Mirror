package weatherApp;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.NoSuchElementException;

import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

public class WeatherClass {

    private CurrentWeather currentWeather;

    public WeatherClass(String city, String key)
            throws IOException, JSONException {

        OpenWeatherMap owm = new OpenWeatherMap(key);
        currentWeather = owm.currentWeatherByCityName(city);

    }

    public Date getTime() {
        // TODO Auto-generated method stub

        return this.currentWeather.getDateTime();

    }

    public double getTemp() {
        return this.currentWeather.getMainInstance().getTemperature();
    }

    
    public String getCity() {
        return this.currentWeather.getCityName();
    }
    
    
    public double getRain() {
        return this.currentWeather.getRainInstance().getRain();
    }
    
    
    public double getTempHigh() {        
        return this.currentWeather.getMainInstance().getMaxTemperature();
    }
    

}
