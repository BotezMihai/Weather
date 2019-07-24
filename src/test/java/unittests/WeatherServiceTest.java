package unittests;

import static org.assertj.core.api.Assertions.*;

import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import static org.mockito.BDDMockito.given;

/**
 * Class containing tests for WeatherService
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    /**
     * Verifies if given a city the getWeatherNow() method from WeatherService
     * returns a json response with the actual data but in String format
     */
    @Test
    public void givenCity_thenReturnStringResponse() {
        String city = "London";
        Weather weather = new Weather("Clouds", "scattered clouds", 25, 61.0, 4.6, 40.0, "GB", "London");
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.setLenient().create();

        given(weatherService.getWeatherNow(city)).willReturn(gson.toJson(weather).toString());

        String response = weatherService.getWeatherNow(city);
//        assertThat()

    }
}
