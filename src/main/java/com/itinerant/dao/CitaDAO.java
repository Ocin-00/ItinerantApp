package com.itinerant.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.itinerant.entity.Cita;
import com.itinerant.entity.Visita;

public class CitaDAO extends JpaDAO<Cita> implements GenericDAO<Cita> {

	public CitaDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public Cita create(Cita cita) {
		return super.create(cita);
	}

	@Override
	public Cita update(Cita cita) {
		return super.update(cita);
	}

	@Override
	public Cita get(Object citaId) {
		return super.find(Cita.class, citaId);
	}

	@Override
	public void delete(Object citaId) {
		super.delete(Cita.class, citaId);
	}

	@Override
	public List<Cita> listAll() {
		return super.findWithNamedQuery("Cita.findAll");
	}

	public List<Cita> listAllByLogin(String login) {
		List<Cita> citas = super.findWithNamedQuery("Cita.findAllByLogin", "login", login);
		
		if(citas != null && citas.size() > 0) {
			return citas;
		}
		
		return null;
	}
	
	public List<Cita> listAllById(Integer id) {
		List<Cita> citas = super.findWithNamedQuery("Cita.findAllById", "id", id);
		
		if(citas != null && citas.size() > 0) {
			return citas;
		}
		
		return null;
	}
	
	public boolean checkReapted(Integer visitaId, Date hora) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("id", visitaId);
		parameters.put("hora", hora);
		
		List<Cita> citas = super.findWithNamedQuery("Cita.checkRepeated", parameters);
		
		return citas.size() == 1;
	}
	
	@Override
	public long count() {
		return super.countWithNamedQuery("Cita.countAll");
	}
	
}
