package ru.knastnt.weather.doc;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
//import net.sf.jasperreports.export.exporter.PdfExporter;

public class JasperPdfCreator {
    @SneakyThrows
    public static void main(String[] args) {
        createPdf();
    }

    public static File createPdf() throws JRException, SQLException, IOException {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("CITY", "city_город");
        parameters.put("COUNTRY", "страна_country");

        URL resource = JasperPdfCreator.class.getClassLoader().getResource("templates/weather.jrxml");
        String s = JasperCompileManager.compileReportToFile(resource.getFile());

//        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Arrays.asList(
//                new ExampleData("WEATHER", "WEATHER норм"),
//                new ExampleData("baz", "qux")));

        InputStream resourceAsStream = JasperPdfCreator.class.getClassLoader().getResourceAsStream("templates/weather1.jasper");
        JasperPrint jasperPrint = JasperFillManager.fillReport(resourceAsStream, parameters, getDataSource());

        byte[] bytes = JasperExportManager.exportReportToPdf(jasperPrint);
        Files.write(Path.of("e:/example.pdf"), bytes);

        /*OutputStream outputStream = new FileOutputStream(pdfFile);

        PdfExporter exporter = new PdfExporter();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        SimplePdfExporterConfiguration configuration = new SimplePdfExporterConfiguration();
        exporter.setConfiguration(configuration);

        exporter.exportReport();*/

        return null;
    }

    private static JRDataSource getDataSource() {
        Collection<BeanWithList> coll = new ArrayList<BeanWithList>();
        coll.add(new BeanWithList(Arrays.asList("London", "Paris"), 1));
        coll.add(new BeanWithList(Arrays.asList("London", "Madrid", "Moscow", "one", "two", "three", "four", "five", "six"), 2));
        coll.add(new BeanWithList(Arrays.asList("Rome"), 3));

        return new JRBeanCollectionDataSource(coll);
    }

    public static class BeanWithList {

        // The member's name can be any. The JR engine is using public getter for extracting field's value
        private List<String> cities;
        private Integer id;

        public BeanWithList(List<String> cities, Integer id) {
            this.cities = cities;
            this.id = id;
        }

        // getter should be public
        public List<String> getCities() {
            return this.cities;
        }

        public Integer getId() {
            return this.id;
        }
    }
}

class ExampleData {
    private final String field1;
    private final String field2;

    public ExampleData(String field1, String field2) {
        this.field1 = field1;
        this.field2 = field2;
    }

    public String getField1() {
        return field1;
    }

    public String getField2() {
        return field2;
    }
}