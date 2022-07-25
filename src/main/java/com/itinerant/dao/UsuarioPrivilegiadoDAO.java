package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.UsuarioPrivilegiado;



public class UsuarioPrivilegiadoDAO extends JpaDAO<UsuarioPrivilegiado> implements GenericDAO<UsuarioPrivilegiado> {

	public UsuarioPrivilegiadoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public UsuarioPrivilegiado create(UsuarioPrivilegiado usuario) {
		return super.create(usuario);
	}

	@Override
	public UsuarioPrivilegiado update(UsuarioPrivilegiado usuario) {
		return super.update(usuario);
	}
	
	@Override
	public UsuarioPrivilegiado get(Object usuarioId) {
		return super.find(UsuarioPrivilegiado.class, usuarioId);
	}

	public UsuarioPrivilegiado findByEmail(String email) {
		List<UsuarioPrivilegiado> usuario = super.findWithNamedQuery("UsuarioPrivilegiado.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}
	
	@Override
	public void delete(Object usuarioId) {
		super.delete(UsuarioPrivilegiado.class, usuarioId);
	}

	@Override
	public List<UsuarioPrivilegiado> listAll() {
		return super.findWithNamedQuery("UsuarioPrivilegiado.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("UsuarioPrivilegiado.countAll");
	}
}
