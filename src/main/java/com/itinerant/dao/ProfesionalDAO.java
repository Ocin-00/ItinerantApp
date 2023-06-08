package com.itinerant.dao;

import java.text.Normalizer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.itinerant.entity.Cita;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.Visita;

import org.apache.commons.text.StringEscapeUtils;

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
	
	public List<Profesional> listAllValid() {
		return super.findWithNamedQuery("Profesional.findAllValid");
	}

	public boolean checkValidez(String login) {
		
		List<Profesional> profesional = super.findWithNamedQuery("Profesional.checkValidez", "login", login);
		
		return profesional.size() == 1;
	}
	
	@Override
	public long count() {
		return super.countWithNamedQuery("Profesional.countAll");
	}

	public List<Profesional> search(String keyword) {
		
		/*List<Profesional> profesionales = super.findWithNamedQuery("Profesional.search", "keyword", keyword);
		
		if(profesionales != null && profesionales.size() > 0) {
			return profesionales;
		}
		
		return null;*/
		
		String[] splitStr = keyword.split("\\s+");
		
		String queryText = "SELECT p FROM Profesional p ";
		
		boolean first = true;
		boolean last = false;
		Map<String, Object> parameters = new HashMap<>();
		
		if(splitStr != null) {
			for(int i = 0; i < splitStr.length; i++) {
				last = (i == (splitStr.length - 1));
				if(!splitStr[i].isBlank()) {
					parameters.put("param" + i, splitStr[i]);
					String where = "";
					//String mal = org.apache.commons.lang3.StringUtils.stripAccents(Normalizer.normalize(StringEscapeUtils.unescapeHtml4(splitStr[i]).replaceAll(" ", "").toLowerCase(), Normalizer.Form.NFKC).replaceAll("\\p{M}", ""));
					//parameters.put(mal, mal);
					if(first && last) {
						first = false;
						where += "WHERE (p.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR p.apellidos LIKE '%' || :" + "param" + i + " || '%')"
								+ "AND p.validez is true ORDER BY p.apellidos";
						
					} else if (first && !last){
						first = false;
						where += "WHERE (p.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR p.apellidos LIKE '%' || :" + "param" + i + " || '%')";
						
					} else if(!first && last) {
						where +="AND (p.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR p.apellidos LIKE '%' || :" + "param" + i + " || '%')"
								+ "AND p.validez is true ORDER BY p.apellidos";
					} else if(!first && !last) {
						where += "AND (p.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR p.apellidos LIKE '%' || :" + "param" + i + " || '%')";
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
			 
			 List<Profesional> profesionales =  query.getResultList();
				
			 if(profesionales != null && profesionales.size() > 0) {
				return profesionales;
			 }
		}
		return null;
	}
}
