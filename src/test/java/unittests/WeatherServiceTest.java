package unittests;

import com.example.demo.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Autowired
    private WeatherService weatherService;

    @Test
    public void whenSomething_thenAnotherSomething() {

    }
}
