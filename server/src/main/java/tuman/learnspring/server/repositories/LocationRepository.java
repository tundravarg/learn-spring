package tuman.learnspring.server.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import tuman.learnspring.server.entities.LocationEntity;

public interface LocationRepository extends CrudRepository<LocationEntity, Integer> {

	@Query("""
		SELECT c FROM LocationEntity c
		WHERE c.parent.id = :parentId
	""")
	public List<LocationEntity> findChildLocations(@Param("parentId") Integer parentId);

	public List<LocationEntity> findLocationsByParentId(@Param("parentId") Integer parentId);

}
