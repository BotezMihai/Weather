package unittests;

import com.example.demo.controllers.WeatherController;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Class containing tests for WeatherController
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WeatherController.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    /**
     * Verifies if given a city the getWeatherNow() method from WeatherController
     * returns a json response with actual data
     */
//    @Test
//    public void givenCity_thenReturnJsonResponse() {
//        String city = "London";
//        Weather weather = new Weather("Clouds", "scattered clouds", 25, 61.0, 4.6, 40.0, "GB", "London");
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.setLenient().create();
//
//        given(weatherService.getWeatherNow(city)).willReturn(gson.toJson(weather));
//
//        try {
//            mvc.perform(get("/weather/" + city)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isOk())
//                    .andExpect(jsonPath("$.city", is(weather.getCity())))
//                    .andExpect(jsonPath("$.country", is(weather.getCountry())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * Verifies if not given an existing/correct city the getWeatherNow() method from WeatherController
     * returns a json response with a message and an error code.
     */
//    @Test
//    public void whenCityIsInvalid_thenReturnJsonResponse() {
//        String city = "kjhdal";
//        JsonObject expectedResponse = new JsonObject();
//        expectedResponse.addProperty("message", "This city is not in our database!");
//        expectedResponse.addProperty("code", "404");
//
//        given(weatherService.getWeatherNow(city)).willReturn(expectedResponse.toString());
//
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        Gson gson = gsonBuilder.setLenient().create();
//
//        try {
//            mvc.perform(get("/weather/" + city)
//                    .contentType(MediaType.APPLICATION_JSON))
//                    .andExpect(status().isNotFound())
//                    .andExpect(jsonPath("$.message", is(expectedResponse.get("message").getAsString())))
//                    .andExpect(jsonPath("$.code", is(expectedResponse.get("code").getAsString())));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
