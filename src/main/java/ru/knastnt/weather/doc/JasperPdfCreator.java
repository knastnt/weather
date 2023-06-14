package ru.knastnt.weather.doc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
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

        URL resource = JasperPdfCreator.class.getClassLoader().getResource("templates/weather.jrxml");
        String s = JasperCompileManager.compileReportToFile(resource.getFile());

        InputStream resourceAsStream = JasperPdfCreator.class.getClassLoader().getResourceAsStream("templates/weather1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, getDataSource());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        Files.write(Path.of("e:/example.pdf"), bytes);
    }

    private static JRDataSource getDataSource() {
        Collection<BeanWithList> coll = new ArrayList<BeanWithList>();
        coll.add(new BeanWithList(Arrays.asList(WUnit.of("London"), WUnit.of("Paris")), "сегодня"));
        coll.add(new BeanWithList(Arrays.asList(WUnit.of("London"), WUnit.of("Madrid"), WUnit.of("Moscow"), WUnit.of("one"), WUnit.of("two"), WUnit.of("three"), WUnit.of("four"), WUnit.of("five"), WUnit.of("six")), "завтра"));
        coll.add(new BeanWithList(Arrays.asList(WUnit.of("Rome")), "послезавтра"));

        return new JRBeanCollectionDataSource(coll);
    }

    @Data
    @AllArgsConstructor
    public static class BeanWithList {
        private List<WUnit> cities;
        private String id;
    }

    @Data
    public static class WUnit {
        private String f1;
        private String f2;

        private static AtomicInteger counter = new AtomicInteger(0);
        public static WUnit of(String f1) {
            WUnit res = new WUnit();
            res.setF1(f1);
            res.setF2("value" + counter.incrementAndGet());
            return res;
        }
    }
}