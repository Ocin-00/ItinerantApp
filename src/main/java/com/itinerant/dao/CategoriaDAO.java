package com.itinerant.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.itinerant.entity.Categoria;
import com.itinerant.entity.Visita;
import com.itinerant.enums.NivelAcceso;


public class CategoriaDAO extends JpaDAO<Categoria> implements GenericDAO<Categoria> {

	public CategoriaDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	public Categoria create(Categoria categoria) {
		return super.create(categoria);
	}

	@Override
	public Categoria update(Categoria categoria) {
		return super.update(categoria);
	}

	@Override
	public Categoria get(Object categoriaId) {
		return super.find(Categoria.class, categoriaId);
	}
	
	public Categoria getByName(String nombre) {
		List<Categoria> categorias = super.findWithNamedQuery("Categoria.findByName", "nombre", nombre);
		
		if(categorias != null && categorias.size() > 0) {
			return categorias.get(0);
		}
		
		return null;
	}

	@Override
	public void delete(Object categoriaId) {
		super.delete(Categoria.class, categoriaId);
	}

	@Override
	public List<Categoria> listAll() {
		return super.findWithNamedQuery("Categoria.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Categoria.countAll");
	}
	
	public List<Categoria> getByUse() {
		TypedQuery<Object[]> query = entityManager.createNamedQuery("Categoria.getByUse", Object[].class);
		List<Object[]> results = query.getResultList();
		List<Categoria> categorias = new ArrayList<>();
		
		if(results != null && results.size() > 0) {
			for (Object[] result : results) {
			    Categoria categoria = (Categoria) result[0];
			    categorias.add(categoria);
			    //Long count = (Long) result[1];
			    // process the Categoria and count here
			}
			return categorias;
		}
		
		return null;
	}
	
	public Map<Categoria, Long> StatisticsSupply(String ambito, String lugar) {
		String query1Text = "";
		
		TypedQuery<Object[]> query1 = null;		
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v JOIN v.localidad l WHERE l.mancomunidad = :lugar GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v JOIN v.localidad l WHERE l.nombre = :lugar GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		}
		
		List<Object[]> results1 = query1.getResultList();
		Map<Categoria, Long> resultados = new HashMap<Categoria, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((Categoria) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			return resultados;
		} 
		return null;
	}
	
	public Map<Categoria, Long> StatisticsDemand(String ambito, String lugar) {
		String query1Text = "";
		
		TypedQuery<Object[]> query1 = null;		
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v JOIN v.citas ci GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v JOIN v.citas ci JOIN v.localidad l WHERE l.mancomunidad = :lugar GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT c, count(*) FROM Categoria c JOIN c.visitas v JOIN v.citas ci JOIN v.localidad l WHERE l.nombre = :lugar GROUP BY c ORDER BY count(*)";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
		}
		
		List<Object[]> results1 = query1.getResultList();
		Map<Categoria, Long> resultados = new HashMap<Categoria, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((Categoria) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			return resultados;
		} 
		return null;
	}
}
