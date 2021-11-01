package tuman.learnspring.converters;

import tuman.learnspring.server.dto.LocationDto;
import tuman.learnspring.server.entities.LocationEntity;

public class LocationConverter {

	public static LocationDto toDto(LocationEntity entity) {
		LocationDto dto = new LocationDto();
		dto
			.setId(entity.getId())
			.setName(entity.getName())
			.setType(entity.getType())
			;
		return dto;
	}

}
