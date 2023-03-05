package com.itinerant.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.Visita;

public class VisitaDAO extends JpaDAO<Visita> implements GenericDAO<Visita> {

	public VisitaDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public Visita create(Visita visita) {
		return super.create(visita);
	}

	@Override
	public Visita update(Visita visita) {
		return super.update(visita);
	}

	@Override
	public Visita get(Object visitaId) {
		return super.find(Visita.class, visitaId);
	}

	@Override
	public void delete(Object visitaId) {
		super.delete(Visita.class, visitaId);
	}

	@Override
	public List<Visita> listAll() {
		return super.findWithNamedQuery("Visita.findAll");
	}
	
	public List<Visita> listAllByLogin(String login) {
		List<Visita> visitas = super.findWithNamedQuery("Visita.findAllByLogin", "login", login);
		
		if(visitas != null && visitas.size() > 0) {
			return visitas;
		}
		
		return null;
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Visita.countAll");
	}
	
	public List<Visita> search(String keyword, String keywordMal) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("keyword", keyword);
		parameters.put("keywordMal", keywordMal);
		
		
		List<Visita> visitas = super.findWithNamedQuery("Visita.search", parameters);
		
		if(visitas != null && visitas.size() > 0) {
			return visitas;
		}
		
		return null;
	}
	
	public List<Visita> searchAll() {
		return super.findWithNamedQuery("Visita.searchAll");
	}
}
