package tuman.learnspring.server.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuman.learnspring.server.entities.LocationEntity;
import tuman.learnspring.server.repositories.LocationRepository;

@Component
public class DataTestService {

	@Autowired
	private LocationRepository locationRepository;


	public void testDataAccess() {
		testGetLocationsViaCrud();
	}

	@Transactional
	private void testGetLocationsViaCrud() {
		Iterable<LocationEntity> allLocations = locationRepository.findAll();
		StreamSupport.stream(allLocations.spliterator(), false)
				.forEach(location -> {
					System.out.println("---- Location: " + locationToString(location));
					System.out.println("-------- Parent: " + locationToString(location.getParent()));
					System.out.println("-------- Children: ");
					// TODO Lazy operations
					location.getChildren().stream()
							.forEach(child -> {
								System.out.println("------------ Child: " + locationToString(child));
							});
				});
	}

	private static String locationToString(LocationEntity location) {
		if (location == null) {
			return null;
		}
		return String.format(
				"{id: %d, name: \"%s\", type: \"%s\"}",
				location.getId(),
				location.getName(),
				location.getType());
	}

}
