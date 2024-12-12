package com.goormthon.halmang.domain.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomEventPublisher {
    private final ApplicationEventPublisher publisher;

    public void publishEvent(String message) {
        CustomEvent event = new CustomEvent(message);
        publisher.publishEvent(event);
    }
}
