package tuman.learnspring.server.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/ping")
    public String ping(
        @RequestParam(value = "i", required = false)
        Integer i
    ) {
        String answer = "pong";
        if (i != null) {
            answer += " " + (i + 1);
        }
        return answer;
    }

}
