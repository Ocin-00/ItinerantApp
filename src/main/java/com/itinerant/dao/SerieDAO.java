package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itinerant.entity.Serie;

public class SerieDAO extends JpaDAO<Serie> implements GenericDAO<Serie> {

	public SerieDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public Serie create(Serie serie) {
		return super.create(serie);
	}

	@Override
	public Serie update(Serie serie) {
		return super.update(serie);
	}

	@Override
	public Serie get(Object idSerie) {
		return super.find(Serie.class, idSerie);
	}

	@Override
	public void delete(Object idSerie) {
		super.delete(Serie.class, idSerie);
	}

	@Override
	public List<Serie> listAll() {
		return super.findWithNamedQuery("Serie.findAll");
	}
	
	public List<Serie> listAllByLogin(String login) {
		List<Serie> serie = super.findWithNamedQuery("Serie.findAllByLogin", "login", login);
		
		if(serie != null && serie.size() > 0) {
			return serie;
		}
		
		return null;
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Serie.countAll");
	}
}
