package ru.knastnt.weather.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import ru.knastnt.weather.weatherparser.dtos.WindDirection;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class JasperPdfCreator {
    @SneakyThrows
    public static void main(String[] args) {
        createPdf();
    }

    public static void createPdf() throws JRException, IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CITY", "city_город");
        parameters.put("COUNTRY", "страна_country");
        parameters.put("ICON", "icons/01d.png");

        URL resource = JasperPdfCreator.class.getClassLoader().getResource("templates/weather.jrxml");
        String s = JasperCompileManager.compileReportToFile(resource.getFile());

        InputStream resourceAsStream = JasperPdfCreator.class.getClassLoader().getResourceAsStream("templates/weather1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, getDataSource());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        Files.write(Path.of("e:/example.pdf"), bytes);
    }

    private static JRDataSource getDataSource() {
        Collection<WUnitDateList> dates = new ArrayList<>();
        dates.add(new WUnitDateList(Arrays.asList(
                new WUnit("10:45", "icons/02d.png", 25, "солнечно", "западный", 1),
                new WUnit("15:00", "icons/04d.png", 23, "солнечно", "северо-западный", 2)
        ), "сегодня"));
        dates.add(new WUnitDateList(Arrays.asList(
                new WUnit("08:00", "icons/01d.png", 25, "солнечно", null, 0),
                new WUnit("16:00", "icons/02d.png", 27, "солнечно", "западный", 1)
        ), "завтра"));

        return new JRBeanCollectionDataSource(dates);
    }

    @Data
    @AllArgsConstructor
    public static class WUnitDateList {
        private List<WUnit> wunits;
        private String date;
    }

    @Data
    @AllArgsConstructor
    public static class WUnit {
        private String time;
        private String icon;
        private Integer temperature;
        private String weather;
        private String windDirection;
        private Integer windSpeed;
    }
}