package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;

public class ProfesionalDAO extends UsuarioInternoDAO {

	public ProfesionalDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("Profesional.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Profesional.countAll");
	}

}
