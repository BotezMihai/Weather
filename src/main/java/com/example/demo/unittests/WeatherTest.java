package com.example.demo.unittests;

import com.example.demo.controllers.WeatherController;
import com.example.demo.entity.Weather;
import com.example.demo.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private WeatherService weatherService;

    @Test
    public void givenCity_thenReturnJsonResponse() {
        String city = "Bacau";

        Weather weather = new Weather();
        weather.setMain("Clouds");

        given(weatherService.getWeatherNow(city)).willReturn(weather);

        try {
            mvc.perform(get("/weather/Bacau"))
                    .contentType(MediaType.APPLICATION_JSON)
                    .andExpect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
