package ru.knastnt.weather.doc;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Component;
import ru.knastnt.weather.doc.dto.WUnit;
import ru.knastnt.weather.doc.dto.WUnitDateList;
import ru.knastnt.weather.weatherparser.dtos.TimeWeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;
import ru.knastnt.weather.weatherparser.dtos.WindDirection;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;

@Component
public class DocParamMapper {
    public Map<String, Object> mapParams(WeatherDto weather) {
        Map<String, Object> parameters = new HashMap<>();
        
        set("CITY", weather.getCity(), parameters);
        set("COUNTRY", weather.getCountry(), parameters);
        set("WEATHER", weather.getWeather(), parameters);
        set("ICON", getIconFile(weather.getIconName()), parameters);
        set("TEMPERATURE", weather.getTemperature(), parameters);
        set("TEMPERATURE_UNIT", weather.getTemperatureUnit(), parameters);
        set("COORDINATES", weather.getCoordinates(), parameters);
        set("ZONE_OFFSET", weather.getZoneOffset(), parameters);
        set("WIND_DIRECTION", ofNullable(weather.getWindDirection()).map(WindDirection::getDescription).orElse(""), parameters);
        set("WIND_SPEED", weather.getWindSpeed(), parameters);
        set("WIND_GUST", weather.getWindGust(), parameters);
        set("WIND_UNIT", weather.getWindUnit(), parameters);
        
        return parameters;
    }

    private static String getIconFile(String iconName) {
        return iconName == null ? null : "icons/" + iconName + ".png";
    }

    private void set(String name, Object value, Map<String, Object> parameters) {
        parameters.put(name, value == null ? "" : String.valueOf(value));
    }

    public JRDataSource mapDataSource(WeatherDto weather) {
        List<WUnitDateList> dates = new ArrayList<>();

        Map<LocalDate, List<TimeWeatherDto>> dateWeatherTimes = weather.getTimeWeather().stream()
                .collect(Collectors.groupingBy(timeWeatherDto -> timeWeatherDto.getTime().toLocalDate()));

        for (Map.Entry<LocalDate, List<TimeWeatherDto>> dateListEntry : dateWeatherTimes.entrySet()) {
            dates.add(new WUnitDateList(mapTimeList(dateListEntry.getValue(), weather), dateListEntry.getKey().toString()));
        }

        dates.sort(Comparator.comparing(WUnitDateList::getDate));

        return new JRBeanCollectionDataSource(dates);
    }

    private List<WUnit> mapTimeList(List<TimeWeatherDto> timeList, WeatherDto weather) {
        List<WUnit> res = new ArrayList<>();

        for (TimeWeatherDto timeWeatherDto : timeList) {
            res.add(mapTime(timeWeatherDto, weather));
        }

        return res;
    }

    private WUnit mapTime(TimeWeatherDto timeWeatherDto, WeatherDto weather) {
        WUnit res = new WUnit();

        res.setTime(ofNullable(timeWeatherDto.getTime()).map(LocalDateTime::toLocalTime).map(LocalTime::toString).orElse(""));
        res.setIcon(getIconFile(timeWeatherDto.getIconName()));
        res.setTemperature(timeWeatherDto.getTemperature());
        res.setTemperatureUnit(weather.getTemperatureUnit());
        res.setWeather(ofNullable(timeWeatherDto.getWeather()).orElse(""));
        res.setWindDirection(ofNullable(timeWeatherDto.getWindDirection()).map(WindDirection::getDescription).orElse(""));
        res.setWindSpeed(timeWeatherDto.getWindSpeed());
        res.setWindUnit(weather.getWindUnit());

        return res;
    }
}
