package ru.knastnt.weather.doc.util;

import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Slf4j
@Service
public class ReportService {

    @Cacheable(cacheNames="jasperReport", key="#reportFilePath")
    public JasperReport createReport(String reportFilePath) {
        log.info("Create report reportFilePath: {}", reportFilePath);
        try {
            InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(reportFilePath);
            return JasperCompileManager.compileReport(resourceAsStream);

        } catch (Exception e) {
            throw new RuntimeException("Can't create report reportFilePath: " + reportFilePath, e);
        }
    }
}
