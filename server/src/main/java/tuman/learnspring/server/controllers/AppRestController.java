package tuman.learnspring.server.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRestController {

	@GetMapping("/ping")
	public void ping() {
		System.out.println("---------------------- PING");
	}

}
