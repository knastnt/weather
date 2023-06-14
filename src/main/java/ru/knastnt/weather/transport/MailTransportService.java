package ru.knastnt.weather.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
@Service
public class MailTransportService implements TransportService {
    @Override
    public void sendContent(byte[] content, String address) {
        log.debug("Send content byte[{}] to email: \"{}\"", content.length, address);

        try {
            Files.write(Path.of("e:/example.pdf"), content);
            System.out.println("DONE file creation");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //проверить адрес
        //отправить несколько попыток с результатом
    }
}
