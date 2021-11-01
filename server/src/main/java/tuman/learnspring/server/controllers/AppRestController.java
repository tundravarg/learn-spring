package tuman.learnspring.server.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tuman.learnspring.server.dto.LocationDto;
import tuman.learnspring.server.services.LocationService;

@RestController
@RequestMapping(path = {"/api", "/ui"})
public class AppRestController {

	@Autowired
	private LocationService locationService;


	@GetMapping("/ping")
	public String ping() {
		System.out.println("---------------------- PING");
		return "pong";
	}

	@GetMapping("/rooms")
	public List<LocationDto> getRooms() {
		return locationService.getRooms("My Flat");
	}

}
