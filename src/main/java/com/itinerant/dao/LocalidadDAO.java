package com.itinerant.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.itinerant.entity.Categoria;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.NivelAcceso;


public class LocalidadDAO extends JpaDAO<Localidad> implements GenericDAO<Localidad> {

	public LocalidadDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	public Localidad create(Localidad localidad) {
		return super.create(localidad);
	}

	@Override
	public Localidad update(Localidad localidad) {
		return super.update(localidad);
	}

	@Override
	public Localidad get(Object localidadId) {
		return super.find(Localidad.class, localidadId);
	}

	@Override
	public void delete(Object localidadId) {
		super.delete(Localidad.class, localidadId);
	}

	@Override
	public List<Localidad> listAll() {
		return super.findWithNamedQuery("Localidad.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Localidad.countAll");
	}
	
	public List<String> listMancomunidades() {
		Query query = entityManager.createNamedQuery("Localidad.findMancomunidades");
		return query.getResultList();
	}
	
	public List<Localidad> listLocalidadesByMancomunidad(String mancomunidad) {
		List<Localidad> localidades = super.findWithNamedQuery("Localidad.findLocalidadesByMancomunidad", "mancomunidad", mancomunidad);
		if(localidades != null && localidades.size() > 0) {
			return localidades;
		}
		
		return null;
	}
	
	public Localidad findByNombre(String nombre) {
		List<Localidad> localidad = super.findWithNamedQuery("Localidad.findByName", "nombre", nombre);
		
		if(localidad != null && localidad.size() > 0) {
			return localidad.get(0);
		}
		
		return null;
			
	}
	
	public Map<Localidad, Long> StatisticsCitas(String ambito, String lugar) {
		String query1Text = "";
		
		TypedQuery<Object[]> query1 = null;		
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT l, count(*) FROM Localidad l JOIN l.visitas v JOIN v.citas ci GROUP BY l ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT l, count(*) FROM Localidad l JOIN l.visitas v JOIN v.citas ci WHERE l.mancomunidad = :lugar GROUP BY l ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT l, count(*) FROM Localidad l JOIN l.visitas v JOIN v.citas ci WHERE l.nombre = :lugar GROUP BY l ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		}
		
		List<Object[]> results1 = query1.getResultList();
		Map<Localidad, Long> resultados = new HashMap<Localidad, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((Localidad) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			return resultados;
		} 
		return null;
	}
}
