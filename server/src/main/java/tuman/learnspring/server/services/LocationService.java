package tuman.learnspring.server.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import tuman.learnspring.converters.LocationConverter;
import tuman.learnspring.server.dto.LocationDto;
import tuman.learnspring.server.entities.LocationEntity;
import tuman.learnspring.server.repositories.LocationRepositoryImpl;

@Component
public class LocationService {

	@Autowired
	private LocationRepositoryImpl locationRepositoryIml;


	@Transactional
	public List<LocationDto> getRooms(String flatName) {
		List<LocationEntity> locations = locationRepositoryIml.findRoomsInFlat(flatName);
		List<LocationDto> locationDtos = locations.stream().map(LocationConverter::toDto).collect(Collectors.toList());
		return locationDtos;
	}

}
