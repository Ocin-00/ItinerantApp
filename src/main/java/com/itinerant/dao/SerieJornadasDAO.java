package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itinerant.entity.Serie;
import com.itinerant.entity.SerieJornadas;

public class SerieJornadasDAO extends JpaDAO<SerieJornadas> implements GenericDAO<SerieJornadas> {

	public SerieJornadasDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public SerieJornadas create(SerieJornadas serieJornadas) {
		return super.create(serieJornadas);
	}

	@Override
	public SerieJornadas update(SerieJornadas serieJornadas) {
		return super.update(serieJornadas);
	}

	@Override
	public SerieJornadas get(Object serieJornadasId) {
		return super.find(SerieJornadas.class, serieJornadasId);
	}

	@Override
	public void delete(Object serieJornadasId) {
		super.delete(SerieJornadas.class, serieJornadasId);
	}

	@Override
	public List<SerieJornadas> listAll() {
		return super.findWithNamedQuery("SerieJornadas.findAll");
	}
	
	public List<SerieJornadas> listAllById(Integer id) {
		List<SerieJornadas> serieJornadas = super.findWithNamedQuery("SerieJornadas.findAllById", "id", id);
		
		if(serieJornadas != null && serieJornadas.size() > 0) {
			return serieJornadas;
		}
		
		return null;
	}
	
	public List<SerieJornadas> listAllByLogin(String login) {
		List<SerieJornadas> serieJornadas = super.findWithNamedQuery("SerieJornadas.findAllByLogin", "login", login);
		
		if(serieJornadas != null && serieJornadas.size() > 0) {
			return serieJornadas;
		}
		
		return null;
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("SerieJornadas.countAll");
	}
}
