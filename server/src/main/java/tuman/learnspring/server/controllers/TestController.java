package tuman.learnspring.server.controllers;


import org.springframework.web.bind.annotation.*;
import tuman.learnspring.server.dtos.UserCtx;

import java.util.Map;


@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/ping")
    public String ping(
        @RequestParam(value = "i", required = false)
        Integer i,
        @RequestHeader
        Map<String, String> headers,
        @SessionAttribute
        UserCtx userCtx
    ) {
        System.out.println("PING: i=" + i);
        headers.forEach((key, value) -> {
            System.out.println("PING header: " + key + ":" + value);
        });

        String answer = "pong";
        if (i != null) {
            answer += " " + (i + 1);
        }
        return answer;
    }

}
