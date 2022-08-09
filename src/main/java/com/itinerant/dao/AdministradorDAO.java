package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Administrador;

public class AdministradorDAO extends JpaDAO<Administrador> implements GenericDAO<Administrador> {

	public AdministradorDAO(EntityManager entitiyManager) {
		super(entitiyManager);
		//TODO Auto-generated constructor stub
	}
	
	@Override
	public Administrador create(Administrador usuario) {
		return super.create(usuario);
	}

	@Override
	public Administrador update(Administrador usuario) {
		return super.update(usuario);
	}
	
	@Override
	public Administrador get(Object usuarioId) {
		return super.find(Administrador.class, usuarioId);
	}
	
	public Administrador findByEmail(String email) {
		List<Administrador> usuario = super.findWithNamedQuery("Administrador.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(Administrador.class, usuarioId);
	}
	
	@Override
	public List<Administrador> listAll() {
		return super.findWithNamedQuery("Administrador.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Administrador.countAll");
	}

}
