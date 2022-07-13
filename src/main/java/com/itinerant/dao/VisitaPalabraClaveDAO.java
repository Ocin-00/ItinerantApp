package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.VisitaPalabraClave;

public class VisitaPalabraClaveDAO extends JpaDAO<VisitaPalabraClave> implements GenericDAO<VisitaPalabraClave> {

	public VisitaPalabraClaveDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public VisitaPalabraClave create(VisitaPalabraClave palabraClave) {
		return super.create(palabraClave);
	}

	@Override
	public VisitaPalabraClave update(VisitaPalabraClave palabraClave) {
		return super.update(palabraClave);
	}

	@Override
	public VisitaPalabraClave get(Object palabraClaveId) {
		return super.find(VisitaPalabraClave.class, palabraClaveId);
	}

	@Override
	public void delete(Object palabraClaveId) {
		super.delete(VisitaPalabraClave.class, palabraClaveId);
	}

	@Override
	public List<VisitaPalabraClave> listAll() {
		return super.findWithNamedQuery("VisitaPalabraClave.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("VisitaPalabraClave.countAll");
	}
}
