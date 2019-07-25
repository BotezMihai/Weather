package unittests;

import static org.assertj.core.api.Assertions.*;

import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import static org.mockito.BDDMockito.given;

/**
 * Class containing tests for WeatherService
 */
@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Mock
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

        given(weatherService.getWeatherNow(city)).willReturn(gson.toJson(weather));

        String response = weatherService.getWeatherNow(city);
        assertThat(gson.toJson(weather)).isEqualTo(response);
    }

    /**
     * Verifies if not given an existing/correct city the getWeatherNow() method from WeatherService
     * returns a json response with a message and an error code.
     */
    @Test
    public void whenCityIsInvalid_thenReturnJsonResponse() {
        String city = "idjakdaca";
        JsonObject expectedResponse = new JsonObject();
        expectedResponse.addProperty("message", "This city is not in our database!");
        expectedResponse.addProperty("code", "404");

        given(weatherService.getWeatherNow(city)).willReturn(expectedResponse.toString());

        String response = weatherService.getWeatherNow(city);
        JsonParser jsonParser = new JsonParser();
        JsonObject objectFromString = jsonParser.parse(response).getAsJsonObject();

        assertThat(objectFromString.get("message").getAsString()).isEqualTo("This city is not in our database!");
        assertThat(Integer.parseInt(objectFromString.get("code").getAsString())).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}
