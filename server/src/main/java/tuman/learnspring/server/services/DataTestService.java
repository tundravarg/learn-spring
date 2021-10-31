package tuman.learnspring.server.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuman.learnspring.server.entities.LocationEntity;
import tuman.learnspring.server.repositories.LocationRepository;
import tuman.learnspring.server.repositories.LocationRepositoryImpl;

@Component
public class DataTestService {

	@Autowired
	private LocationRepository locationRepository;
	@Autowired
	private LocationRepositoryImpl locationRepositoryImpl;


	public void testDataAccess() {
		testGetLocationsViaCrud();
	}

	@Transactional
	private void testGetLocationsViaCrud() {
		Stream<LocationEntity> locationsStream;

		// Iterable<LocationEntity> allLocations = locationRepository.findAll();
		// locationsStream = StreamSupport.stream(allLocations.spliterator(), false);

		List<LocationEntity> allLocations = locationRepositoryImpl.findAll();
		locationsStream = allLocations.stream();

		locationsStream
				.forEach(location -> {
					System.out.println("---- Location: " + locationToString(location));
					System.out.println("-------- Parent: " + locationToString(location.getParent()));
					System.out.println("-------- Children: ");
					List<LocationEntity> children;
					// children = location.getChildren();
					children = locationRepository.getChildLocations(location.getId());
					children.stream()
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
