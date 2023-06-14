package ru.knastnt.weather.doc;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.knastnt.weather.doc.util.ReportService;
import ru.knastnt.weather.weatherparser.dtos.WeatherDto;

import java.util.Map;

@Slf4j
@Service
public class PdfDocumentCreationService implements DocumentCreationService {
    @Autowired
    private DocParamMapper mapper;
    @Autowired
    private ReportService reportService;

    @Override
    public byte[] createWeatherDocument(WeatherDto weather) {
        log.debug("Create weather document for: {}", weather);

        try {
            Map<String, Object> parameters = mapper.mapParams(weather);
            JRDataSource dataSource = mapper.mapDataSource(weather);

            JasperReport report = reportService.createReport("templates/weather.jrxml");

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (Exception e) {
            if (e instanceof RuntimeException) throw (RuntimeException) e;
            throw new RuntimeException(e);
        }
    }
}
