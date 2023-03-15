package com.itinerant.dao;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itinerant.entity.UsuarioInterno;
import com.itinerant.entity.Visita;
import com.itinerant.service.CategoriaServicios;

import org.apache.commons.text.StringEscapeUtils;

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
	
	public List<Visita> search(String keyword) {
		String[] splitStr = keyword.split("\\s+");
		
		String queryText = "SELECT DISTINCT v FROM Visita v JOIN v.categorias c ";
		
		boolean first = true;
		boolean last = false;
		Map<String, Object> parameters = new HashMap<>();
		
		if(splitStr != null) {
			for(int i = 0; i < splitStr.length; i++) {
				last = (i == (splitStr.length - 1));
				if(!splitStr[i].isBlank()) {
					parameters.put("param" + i, splitStr[i]);
					String where = "";
					String mal = org.apache.commons.lang3.StringUtils.stripAccents(Normalizer.normalize(StringEscapeUtils.unescapeHtml4(splitStr[i]).replaceAll(" ", "").toLowerCase(), Normalizer.Form.NFKC).replaceAll("\\p{M}", ""));
					parameters.put("paramMal" + i, mal);
					if(first && last) {
						first = false;
						where += "WHERE lower(v.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(c.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(c.nombreMal) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "ORDER BY v.fecha DESC, v.horaInicio DESC";
						
					} else if (first && !last){
						first = false;
						where += "WHERE (lower(v.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "param" + i+ " || '%'"
								+ "OR lower(c.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(c.nombreMal) LIKE '%' || :" + "paramMal" + i + " || '%')";
						
					} else if(!first && last) {
						where += "AND (lower(v.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(c.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(c.nombreMal) LIKE '%' || :" + "paramMal" + i + " || '%')"
								+ "ORDER BY v.fecha DESC, v.horaInicio DESC";
					} else if(!first && !last) {
						where += "AND (lower(v.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR lower(c.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.descripcion) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.localidad.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.nombre) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(v.profesional.apellidos) LIKE '%' || :" + "paramMal" + i + " || '%'"
								+ "OR lower(c.nombreMal) LIKE '%' || :" + "paramMal" + i + " || '%')";
					}
					queryText += where;
				}
			}
			System.out.println(queryText);
			 Query query = entityManager.createQuery(queryText);
			 
			 Set<Entry<String, Object>> setParameters = parameters.entrySet();
			 for (Entry<String, Object> entry : setParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			 }
			 
			 List<Visita> visitas =  query.getResultList();
				
			 if(visitas != null && visitas.size() > 0) {
				return visitas;
			 }
		}
		return null;
	}
	
	public List<Visita> searchAll() {
		return super.findWithNamedQuery("Visita.searchAll");
	}
}
