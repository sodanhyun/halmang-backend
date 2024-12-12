package com.goormthon.halmang.domain.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventService {
    private final CustomEventPublisher publisher;

    public void publish(String message) {
        publisher.publishEvent(message);
    }
}
