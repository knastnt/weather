package ru.knastnt.weather.transport;

public interface TransportService {
    void sendContent(byte[] content, String address);
}
