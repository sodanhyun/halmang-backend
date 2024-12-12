package com.goormthon.halmang;

import com.goormthon.halmang.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HalmangApplication {

    public static void main(String[] args) {
        SpringApplication.run(HalmangApplication.class, args);
    }

}
