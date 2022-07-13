package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;

public class CiudadanoDAO extends UsuarioInternoDAO {

	public CiudadanoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("Ciudadano.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Ciudadano.countAll");
	}

}
