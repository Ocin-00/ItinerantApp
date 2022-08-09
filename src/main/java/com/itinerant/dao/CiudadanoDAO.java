package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Ciudadano;

public class CiudadanoDAO extends JpaDAO<Ciudadano> implements GenericDAO<Ciudadano> {

	public CiudadanoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public Ciudadano create(Ciudadano usuario) {
		return super.create(usuario);
	}

	@Override
	public Ciudadano update(Ciudadano usuario) {
		return super.update(usuario);
	}
	
	@Override
	public Ciudadano get(Object usuarioId) {
		return super.find(Ciudadano.class, usuarioId);
	}
	
	public Ciudadano findByEmail(String email) {
		List<Ciudadano> usuario = super.findWithNamedQuery("Ciudadano.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(Ciudadano.class, usuarioId);
	}
	
	@Override
	public List<Ciudadano> listAll() {
		return super.findWithNamedQuery("Ciudadano.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Ciudadano.countAll");
	}

}
