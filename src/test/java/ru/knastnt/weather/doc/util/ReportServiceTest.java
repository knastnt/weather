package ru.knastnt.weather.doc.util;

import net.sf.jasperreports.engine.JasperReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.knastnt.weather.WeatherApplicationTests;

import static org.junit.jupiter.api.Assertions.*;

class ReportServiceTest extends WeatherApplicationTests {
    @Autowired
    private ReportService reportService;
    @Test
    void createReport() {
        JasperReport report = reportService.createReport("templates/weather.jrxml");
        assertNotNull(report);
        assertEquals("weather", report.getName());

        //cached
        assertSame(report, reportService.createReport("templates/weather.jrxml"));
    }
}