package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Localidad;

public class LocalidadDAO extends JpaDAO<Localidad> implements GenericDAO<Localidad> {

	public LocalidadDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	public Localidad create(Localidad localidad) {
		return super.create(localidad);
	}

	@Override
	public Localidad update(Localidad localidad) {
		return super.update(localidad);
	}

	@Override
	public Localidad get(Object localidadId) {
		return super.find(Localidad.class, localidadId);
	}

	@Override
	public void delete(Object localidadId) {
		super.delete(Localidad.class, localidadId);
	}

	@Override
	public List<Localidad> listAll() {
		return super.findWithNamedQuery("Localidad.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Localidad.countAll");
	}

}
