package ru.knastnt.weather.doc.additions;

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
        
        set(Params.CITY, weather.getCity(), parameters);
        set(Params.COUNTRY, weather.getCountry(), parameters);
        set(Params.WEATHER, weather.getWeather(), parameters);
        set(Params.ICON, getIconFile(weather.getIconName()), parameters);
        set(Params.TEMPERATURE, weather.getTemperature(), parameters);
        set(Params.TEMPERATURE_UNIT, weather.getTemperatureUnit(), parameters);
        set(Params.COORDINATES, weather.getCoordinates(), parameters);
        set(Params.ZONE_OFFSET, weather.getZoneOffset(), parameters);
        set(Params.WIND_DIRECTION, ofNullable(weather.getWindDirection()).map(WindDirection::getDescription).orElse(""), parameters);
        set(Params.WIND_SPEED, weather.getWindSpeed(), parameters);
        set(Params.WIND_GUST, weather.getWindGust(), parameters);
        set(Params.WIND_UNIT, weather.getWindUnit(), parameters);
        
        return parameters;
    }

    public JRDataSource mapDataSource(WeatherDto weather) {
        List<WUnitDateList> dates = new ArrayList<>();

        Map<LocalDate, List<TimeWeatherDto>> dateWeatherTimes = weather.getTimeWeather().stream()
                .collect(Collectors.groupingBy(timeWeatherDto -> ofNullable(timeWeatherDto.getTime()).map(LocalDateTime::toLocalDate).orElse(LocalDate.now())));

        for (Map.Entry<LocalDate, List<TimeWeatherDto>> dateListEntry : dateWeatherTimes.entrySet()) {
            dates.add(new WUnitDateList(mapTimeList(dateListEntry.getValue(), weather), dateListEntry.getKey().toString()));
        }

        dates.sort(Comparator.comparing(WUnitDateList::getDate));

        return new JRBeanCollectionDataSource(dates);
    }

    private static String getIconFile(String iconName) {
        return iconName == null ? "" : "icons/" + iconName + ".png";
    }

    private void set(String name, Object value, Map<String, Object> parameters) {
        parameters.put(name, value == null ? "" : String.valueOf(value));
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
        res.setTemperature(ofNullable(timeWeatherDto.getTemperature()).map(String::valueOf).orElse(""));
        res.setTemperatureUnit(ofNullable(weather.getTemperatureUnit()).orElse(""));
        res.setWeather(ofNullable(timeWeatherDto.getWeather()).orElse(""));
        res.setWindDirection(ofNullable(timeWeatherDto.getWindDirection()).map(WindDirection::getDescription).orElse(""));
        res.setWindSpeed(ofNullable(timeWeatherDto.getWindSpeed()).map(String::valueOf).orElse(""));
        res.setWindUnit(ofNullable(weather.getWindUnit()).orElse(""));

        return res;
    }
}
