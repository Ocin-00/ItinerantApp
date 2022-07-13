package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Cita;
import com.itinerant.entity.Visita;

public class VisitaDAO extends JpaDAO<Visita> implements GenericDAO<Visita> {

	public VisitaDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public Visita create(Visita visita) {
		return super.create(visita);
	}

	@Override
	public Visita update(Visita visita) {
		return super.update(visita);
	}

	@Override
	public Visita get(Object visitaId) {
		return super.find(Visita.class, visitaId);
	}

	@Override
	public void delete(Object visitaId) {
		super.delete(Visita.class, visitaId);
	}

	@Override
	public List<Visita> listAll() {
		return super.findWithNamedQuery("Visita.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Visita.countAll");
	}
}
