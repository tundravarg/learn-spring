package tuman.learnspring.server.repositories;

import org.springframework.data.repository.CrudRepository;

import tuman.learnspring.server.entities.LocationEntity;

public interface LocationRepository extends CrudRepository<LocationEntity, Integer> {
	// TODO
}
