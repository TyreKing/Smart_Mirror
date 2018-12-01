package weatherApp;

/**The Service being used is OpenWeatherMap
 * OpenWeatherMap.org
 */
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.NoSuchElementException;

import org.json.JSONException;

import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

public class WeatherClass {
    
    //Global Weather Object
    private CurrentWeather currentWeather;

    /**
     * Constructor class
     * 
     * <p>This method creates the Weather class object
     * 
     * @param city  The name of the city that will be used to get the weather
     * @param key   The OpenWeatherMap API key
     * @throws IOException
     * @throws JSONException
     */
    public WeatherClass(String city, String key)
            throws IOException, JSONException {

        OpenWeatherMap owm = new OpenWeatherMap(key);
        currentWeather = owm.currentWeatherByCityName(city);

    }

    /**
     * This method returns a DATE object.
     * @return  the time and date in the set city.
     */
    public Date getTime() {
        return this.currentWeather.getDateTime();
        
    }

    /**
     * This method returns a Double
     * @return  the temperature in the set city.
     */
    public double getTemp() {
        return this.currentWeather.getMainInstance().getTemperature();
    }
    /**
     * This method returns a String
     * @return the name of the city 
     */
    public String getCity() {
        return this.currentWeather.getCityName();
    }
   
    /**
     * returns the temperature high of the main instance of the weather object
     * @return  the temperature high 
     */
    public double getTempHigh() {        
        return this.currentWeather.getMainInstance().getMaxTemperature();
    }
    /**
     * This method returns the type of weather of the set city.
     * @return the name of the weather type
     */
   
    public String getWeatherType() {
        return this.currentWeather.getWeatherInstance(0).getWeatherName();
    }
    /**
     * The method provides a description of the current weather.
     * @return  the description of the current weather in the set city
     */
    public String getDescription() {
        return this.currentWeather.getWeatherInstance(0).getWeatherDescription();
    }
    
}
