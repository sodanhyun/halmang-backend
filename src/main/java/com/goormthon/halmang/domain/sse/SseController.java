package com.goormthon.halmang.domain.sse;

import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class SseController {
    private final List<SseEmitter> emitters = new ArrayList<>();

    @GetMapping(value = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamSse() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        try{
            emitter.send("nice connection");
        }catch (IOException e) {
            emitters.remove(emitter);
            emitter.complete();
        }
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> {
            emitters.remove(emitter);
            emitter.complete();
        });

        return emitter;
    }

    @EventListener
    public void handleCustomEvent(CustomEvent event) {
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(event.getMessage());
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);
            }
        }
    }
}
