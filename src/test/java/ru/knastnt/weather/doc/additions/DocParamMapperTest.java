package ru.knastnt.weather.doc.additions;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.knastnt.weather.doc.dto.WUnit;
import ru.knastnt.weather.doc.dto.WUnitDateList;
import ru.knastnt.weather.weatherparser.dtos.TimeWeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WindDirection;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ru.knastnt.weather.weatherparser.dtos.WindDirection.EAST;

class DocParamMapperTest {
    DocParamMapper mapper = new DocParamMapper();

    @Nested
    class MapParams {
        @Test
        void preventNulls() {
            Map<String, Object> stringObjectMap = mapper.mapParams(new WeatherDto());

            stringObjectMap
                .entrySet()
                .stream()
                .peek(System.out::println)
                .forEach(stringObjectEntry -> assertEquals("", stringObjectEntry.getValue()));
        }
        @Test
        void mapping() {
            Map<String, Object> stringObjectMap = mapper.mapParams(new WeatherDto() {{
                setCity("ct");
                setCountry("cc");
                setWeather("cloudy");
                setIconName("icn");
                setTemperature(13);
                setTemperatureUnit("grd");
                setCoordinates("12° 20′ 42″, 67° 53′ 24″");
                setZoneOffset("+10:00");
                setWindDirection(EAST);
                setWindSpeed(2);
                setWindGust(3);
                setWindUnit("м/с");
            }});

            assertEquals("ct", stringObjectMap.get(Params.CITY));
            assertEquals("cc", stringObjectMap.get(Params.COUNTRY));
            assertEquals("cloudy", stringObjectMap.get(Params.WEATHER));
            assertEquals("icons/icn.png", stringObjectMap.get(Params.ICON));
            assertEquals("13", stringObjectMap.get(Params.TEMPERATURE));
            assertEquals("grd", stringObjectMap.get(Params.TEMPERATURE_UNIT));
            assertEquals("12° 20′ 42″, 67° 53′ 24″", stringObjectMap.get(Params.COORDINATES));
            assertEquals("+10:00", stringObjectMap.get(Params.ZONE_OFFSET));
            assertEquals("Восточный", stringObjectMap.get(Params.WIND_DIRECTION));
            assertEquals("2", stringObjectMap.get(Params.WIND_SPEED));
            assertEquals("3", stringObjectMap.get(Params.WIND_GUST));
            assertEquals("м/с", stringObjectMap.get(Params.WIND_UNIT));
        }
    }

    @Nested
    class MapDataSource {
        @Test
        @SneakyThrows
        void preventNulls() {
            JRDataSource dataSource = mapper.mapDataSource(new WeatherDto() {{
                getTimeWeather().add(new TimeWeatherDto());
            }});

            WUnit wUnit = ((WUnitDateList)((JRBeanCollectionDataSource) dataSource).getData().iterator().next()).getWunits().get(0);
            for (Field declaredField : wUnit.getClass().getDeclaredFields()) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(wUnit);
                System.out.println(declaredField.getName() + "=" + value);
                assertEquals("", value);
            }
        }

        @Test
        void mapping() {
            JRDataSource dataSource = mapper.mapDataSource(new WeatherDto() {{
                setWindUnit("wu");
                setTemperatureUnit("tu");
                getTimeWeather().add(new TimeWeatherDto() {{
                    setTime(LocalDateTime.of(2023,6,14,13,37,0));
                    setWeather("норм");
                    setIconName("if");
                    setTemperature(25);
                    setWindDirection(WindDirection.SOUTH_EAST);
                    setWindSpeed(2);
                }});
            }});

            WUnit wUnit = ((WUnitDateList)((JRBeanCollectionDataSource) dataSource).getData().iterator().next()).getWunits().get(0);

            assertEquals("13:37", wUnit.getTime());
            assertEquals("норм", wUnit.getWeather());
            assertEquals("icons/if.png", wUnit.getIcon());
            assertEquals("25", wUnit.getTemperature());
            assertEquals("Юго-восточный", wUnit.getWindDirection());
            assertEquals("2", wUnit.getWindSpeed());
            assertEquals("tu", wUnit.getTemperatureUnit());
            assertEquals("wu", wUnit.getWindUnit());
        }
    }
}