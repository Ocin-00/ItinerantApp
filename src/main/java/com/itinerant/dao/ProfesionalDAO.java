package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;

public class ProfesionalDAO extends JpaDAO<Profesional> implements GenericDAO<Profesional> {

	public ProfesionalDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public Profesional create(Profesional usuario) {
		return super.create(usuario);
	}

	@Override
	public Profesional update(Profesional usuario) {
		return super.update(usuario);
	}
	
	@Override
	public Profesional get(Object usuarioId) {
		return super.find(Profesional.class, usuarioId);
	}
	
	public Profesional findByEmail(String email) {
		List<Profesional> usuario = super.findWithNamedQuery("Profesional.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(Profesional.class, usuarioId);
	}
	
	@Override
	public List<Profesional> listAll() {
		return super.findWithNamedQuery("Profesional.findAll");
	}
	
	public List<Profesional> listAllNotValid() {
		return super.findWithNamedQuery("Profesional.findAllNotValid");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Profesional.countAll");
	}

}
