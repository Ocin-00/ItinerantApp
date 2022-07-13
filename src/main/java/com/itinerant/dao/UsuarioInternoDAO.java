package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;

public class UsuarioInternoDAO extends JpaDAO<UsuarioInterno> implements GenericDAO<UsuarioInterno> {

	public UsuarioInternoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public UsuarioInterno create(UsuarioInterno usuario) {
		return super.create(usuario);
	}

	@Override
	public UsuarioInterno update(UsuarioInterno usuario) {
		return super.update(usuario);
	}
	
	@Override
	public UsuarioInterno get(Object usuarioId) {
		return super.find(UsuarioInterno.class, usuarioId);
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(UsuarioInterno.class, usuarioId);
	}

	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("UsuarioInterno.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("UsuarioInterno.countAll");
	}

}