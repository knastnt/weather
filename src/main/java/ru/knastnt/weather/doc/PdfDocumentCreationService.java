package ru.knastnt.weather.doc;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class PdfDocumentCreationService implements DocumentCreationService {
    @Autowired
    private DocParamMapper mapper;
    @Override
    public byte[] createWeatherDocument(WeatherDto weather) {
        log.debug("Create weather document for: {}", weather);

        try {
            Map<String, Object> parameters = mapper.mapParams(weather);
            JRDataSource dataSource = mapper.mapDataSource(weather);

            URL resource = PdfDocumentCreationService.class.getClassLoader().getResource("templates/weather.jrxml");
            JasperCompileManager.compileReportToFile(resource.getFile());

            InputStream resourceAsStream = PdfDocumentCreationService.class.getClassLoader().getResourceAsStream("templates/weather.jasper");
            JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, dataSource);

            byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
            Files.write(Path.of("e:/example.pdf"), bytes);

        } catch (Exception e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            throw new RuntimeException(e);
        }

        return new byte[0];
    }
}
