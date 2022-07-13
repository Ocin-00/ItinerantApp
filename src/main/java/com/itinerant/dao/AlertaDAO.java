package com.itinerant.dao;

import java.util.List;
import javax.persistence.EntityManager;
import com.itinerant.entity.Alerta;

public class AlertaDAO extends JpaDAO<Alerta> implements GenericDAO<Alerta> {
	
	public AlertaDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public Alerta create(Alerta alerta) {
		return super.create(alerta);
	}

	@Override
	public Alerta update(Alerta alerta) {
		return super.update(alerta);
	}

	@Override
	public Alerta get(Object alertaId) {
		return super.find(Alerta.class, alertaId);
	}

	@Override
	public void delete(Object alertaId) {
		super.delete(Alerta.class, alertaId);
	}

	@Override
	public List<Alerta> listAll() {
		return super.findWithNamedQuery("Alerta.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Alerta.countAll");
	}
}