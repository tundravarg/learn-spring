package tuman.learnspring.server.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import tuman.learnspring.server.entities.LocationEntity;

@Repository
public class LocationRepositoryImpl {

	@Autowired
	private EntityManager entityManager;

	public List<LocationEntity> findAll() {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<LocationEntity> query = cb.createQuery(LocationEntity.class);
		Root<LocationEntity> from = query.from(LocationEntity.class);
		return entityManager.createQuery(query).getResultList();
	}

}
