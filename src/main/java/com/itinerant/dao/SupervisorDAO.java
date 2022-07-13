package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;

public class SupervisorDAO extends UsuarioPrivilegiadoDAO {

	public SupervisorDAO(EntityManager entitiyManager) {
		super(entitiyManager);
		//TODO Auto-generated constructor stub
	}
	
	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("Supervisor.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Supervisor.countAll");
	}

}
