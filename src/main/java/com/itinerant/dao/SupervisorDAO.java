package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Supervisor;

public class SupervisorDAO extends JpaDAO<Supervisor> implements GenericDAO<Supervisor> {

	public SupervisorDAO(EntityManager entitiyManager) {
		super(entitiyManager);
		//TODO Auto-generated constructor stub
	}
	
	@Override
	public Supervisor create(Supervisor usuario) {
		return super.create(usuario);
	}

	@Override
	public Supervisor update(Supervisor usuario) {
		return super.update(usuario);
	}
	
	@Override
	public Supervisor get(Object usuarioId) {
		return super.find(Supervisor.class, usuarioId);
	}
	
	public Supervisor findByEmail(String email) {
		List<Supervisor> usuario = super.findWithNamedQuery("Supervisor.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(Supervisor.class, usuarioId);
	}
	
	@Override
	public List<Supervisor> listAll() {
		return super.findWithNamedQuery("Supervisor.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Supervisor.countAll");
	}

}
