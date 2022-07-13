package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;

public class UsuarioPrivilegiadoDAO extends UsuarioInternoDAO {

	public UsuarioPrivilegiadoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("UsuarioPrivilegiado.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("UsuarioPrivilegiado.countAll");
	}
}
