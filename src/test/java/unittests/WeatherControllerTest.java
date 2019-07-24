package unittests;

import com.example.demo.controllers.WeatherController;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WeatherController.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void givenCity_thenReturnJsonResponse() {
        String city = "London";
        Weather weather = new Weather("Clouds", "scattered clouds", 25, 61.0, 4.6, 40.0, "GB", "London");

        given(weatherService.getWeatherNow(city)).willReturn(weather);

        try {
            mvc.perform(get("/weather/London")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.city", is(weather.getCity())))
                    .andExpect(jsonPath("$.country", is(weather.getCountry())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
