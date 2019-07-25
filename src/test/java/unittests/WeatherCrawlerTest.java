package unittests;

import com.example.demo.entity.Weather;
import com.example.demo.handlers.RestTemplateResponseErrorHandler;
import com.example.demo.scheduledtasks.WeatherCrawler;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.json.*;
import org.springframework.boot.test.json.*;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;

import static org.assertj.core.api.Assertions.*;

/**
 * Class containing tests for WeatherCrawler class
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WeatherCrawler.class)
@AutoConfigureJsonTesters
@SuppressWarnings("SpringJavaAutowiringInspection")
@JsonTest
public class WeatherCrawlerTest {

    @Autowired
    private JacksonTester<Weather> jacksonTester;

    @MockBean
    private RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    /**
     * Tests if the json wrote in the .json file is exactly as it's supposed to be
     * @throws Exception
     */
    @Test
    public void testSerialize() throws Exception {
        Weather weather = new Weather("Clouds", "scattered clouds", 25, 61.0, 4.6, 40.0, "GB", "London");

        assertThat(this.jacksonTester.write(weather)).hasJsonPathValue("@.main", "@.description", "@.temperature", "@.umidity", "@.windSpeed", "@.clouds", "@.country", "@.city");
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathStringValue("@.main").isEqualTo(weather.getMain());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathStringValue("@.description").isEqualTo(weather.getDescription());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathNumberValue("@.temperature").isEqualTo(weather.getTemperature());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathNumberValue("@.umidity").isEqualTo(weather.getUmidity());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathNumberValue("@.windSpeed").isEqualTo(weather.getWindSpeed());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathNumberValue("@.clouds").isEqualTo(weather.getClouds());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathStringValue("@.country").isEqualTo(weather.getCountry());
        assertThat(this.jacksonTester.write(weather)).extractingJsonPathStringValue("@.city").isEqualTo(weather.getCity());
    }

    /**
     * Tests if the object get from a .json file is exactly as it's supposed to be
     * @throws Exception
     */
    @Test
    public void deserializeTest() throws Exception {
        String content = "{\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"temperature\":25,\"umidity\":61.0,\"windSpeed\":4.6,\"clouds\":40.0,\"country\":\"GB\",\"city\":\"London\"}";

        assertThat(this.jacksonTester.parseObject(content).getMain()).isEqualTo("Clouds");
        assertThat(this.jacksonTester.parseObject(content).getDescription()).isEqualTo("scattered clouds");
        assertThat(this.jacksonTester.parseObject(content).getTemperature()).isEqualTo(25);
        assertThat(this.jacksonTester.parseObject(content).getUmidity()).isEqualTo(61.0);
        assertThat(this.jacksonTester.parseObject(content).getWindSpeed()).isEqualTo(4.6);
        assertThat(this.jacksonTester.parseObject(content).getClouds()).isEqualTo(40.0);
        assertThat(this.jacksonTester.parseObject(content).getCountry()).isEqualTo("GB");
        assertThat(this.jacksonTester.parseObject(content).getCity()).isEqualTo("London");
    }
}
