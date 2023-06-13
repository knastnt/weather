package ru.knastnt.weather.transport;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MailTransportService implements TransportService {
    @Override
    public void send(String address) {
        log.debug("Send to email: {}", address);

        //проверить адрес
        //отправить несколько попыток с результатом
    }
}
