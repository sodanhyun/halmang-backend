package com.goormthon.halmang.domain.sse;

import lombok.Getter;

@Getter
public class CustomEvent {
    private final String message;

    public CustomEvent(String message) {
        this.message = message;
    }

}
