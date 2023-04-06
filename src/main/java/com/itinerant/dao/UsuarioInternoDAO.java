package com.itinerant.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.itinerant.entity.Categoria;
import com.itinerant.entity.Localidad;
import com.itinerant.entity.Profesional;
import com.itinerant.entity.UsuarioInterno;
import com.itinerant.enums.NivelAcceso;

public class UsuarioInternoDAO extends JpaDAO<UsuarioInterno> implements GenericDAO<UsuarioInterno> {

	public UsuarioInternoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public UsuarioInterno create(UsuarioInterno usuario) {
		return super.create(usuario);
	}

	@Override
	public UsuarioInterno update(UsuarioInterno usuario) {
		return super.update(usuario);
	}
	
	@Override
	public UsuarioInterno get(Object usuarioId) {
		return super.find(UsuarioInterno.class, usuarioId);
	}
	
	public UsuarioInterno findByEmail(String email) {
		List<UsuarioInterno> usuario = super.findWithNamedQuery("UsuarioInterno.findByEmail", "email", email);
		
		if(usuario != null && usuario.size() > 0) {
			return usuario.get(0);
		}
		
		return null;
			
	}

	@Override
	public void delete(Object usuarioId) {
		super.delete(UsuarioInterno.class, usuarioId);
	}

	@Override
	public List<UsuarioInterno> listAll() {
		return super.findWithNamedQuery("UsuarioInterno.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("UsuarioInterno.countAll");
	}

	public boolean checkLogin(String login, String password) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("login", login);
		parameters.put("password", password);
		
		List<UsuarioInterno> listaUsuarios = super.findWithNamedQuery("UsuarioInterno.checkLogin", parameters);
		
		if(listaUsuarios.size() == 1) {
			return true;
		}
		return false;
	}
	
	//Estadísticas
	
	public Map<String, Long> getGenderStatistics(String ambito, String lugar, int tipo) {
		String query1Text = "";
		String query2Text = "";
		TypedQuery<Object[]> query1 = null;
		TypedQuery<Object[]> query2 = null;
		
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT p.sexo, COUNT(*) AS count FROM Profesional p GROUP BY p.sexo";
	
			query2Text += "SELECT c.sexo, COUNT(*) AS count FROM Ciudadano c GROUP BY c.sexo";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query2 = entityManager.createQuery(query2Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT p.sexo, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.mancomunidad =:lugar GROUP BY p.sexo";
						
			query2Text += "SELECT c.sexo, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.mancomunidad =:lugar GROUP BY c.sexo";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT p.sexo, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.nombre =:lugar GROUP BY p.sexo";
			query2Text += "SELECT c.sexo, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.nombre =:lugar GROUP BY c.sexo";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		}
		
		List<Object[]> results1 = null;
		List<Object[]> results2 = null;
		if(tipo == 0) {
			results2 = query2.getResultList();
		} else if(tipo == 1) {
			results1 = query1.getResultList();
		} else {
			results1 = query1.getResultList();
			results2 = query2.getResultList();
		}
		
		
		Map<String, Long> resultados = new HashMap<String, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((String) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
					Long count = resultados.get(result[0]);
					if(count != null) {
						count += (Long) result[1];
					} else {
						count = (Long) result[1];
					}
				    resultados.put((String) result[0], count);
				    System.out.println(result[0]);
				}
			}
			return resultados;
		} else {
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
				    resultados.put((String) result[0], (Long) result[1]);
				    System.out.println(result[0]);
				}
				return resultados;
			}
		}
		
		return null;
	}
	
	public Map<String, Long> getMaritalStatusStatistics(String ambito, String lugar, int tipo) {
		String query1Text = "";
		String query2Text = "";
		TypedQuery<Object[]> query1 = null;
		TypedQuery<Object[]> query2 = null;
		
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT p.estadoCivil, COUNT(*) AS count FROM Profesional p GROUP BY p.estadoCivil";
	
			query2Text += "SELECT c.estadoCivil, COUNT(*) AS count FROM Ciudadano c GROUP BY c.estadoCivil";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query2 = entityManager.createQuery(query2Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT p.estadoCivil, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.mancomunidad =:lugar GROUP BY p.estadoCivil";
						
			query2Text += "SELECT c.estadoCivil, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.mancomunidad =:lugar GROUP BY c.estadoCivil";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT p.estadoCivil, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.nombre =:lugar GROUP BY p.estadoCivil";
			query2Text += "SELECT c.estadoCivil, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.nombre =:lugar GROUP BY c.estadoCivil";
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		}
		
		
		List<Object[]> results1 = null;
		List<Object[]> results2 = null;
		if(tipo == 0) {
			results2 = query2.getResultList();
		} else if(tipo == 1) {
			results1 = query1.getResultList();
		} else {
			results1 = query1.getResultList();
			results2 = query2.getResultList();
		}
		Map<String, Long> resultados = new HashMap<String, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((String) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
					Long count = resultados.get(result[0]);
					if(count != null) {
						count += (Long) result[1];
					} else {
						count = (Long) result[1];
					}
					
				    resultados.put((String) result[0], count);
				    System.out.println(result[0]);
				}
			}
			return resultados;
		} else {
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
				    resultados.put((String) result[0], (Long) result[1]);
				    System.out.println(result[0]);
				}
				return resultados;
			}
		}
		
		return null;
	}
	
	public Map<String, Long> getRoleStatistics(String ambito, String lugar, int tipo) {
		String query1Text = "";
		String query2Text = "";
		TypedQuery<Object[]> query1 = null;
		TypedQuery<Object[]> query2 = null;
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT p.rol, COUNT(*) AS count FROM Profesional p GROUP BY p.rol";
			
			query2Text += "SELECT c.rol, COUNT(*) AS count FROM Ciudadano c GROUP BY c.rol";
			
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query2 = entityManager.createQuery(query2Text, Object[].class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {
			query1Text += "SELECT p.rol, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.mancomunidad =:lugar GROUP BY p.rol";
			
			query2Text += "SELECT c.rol, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.mancomunidad =:lugar GROUP BY c.rol";
			
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT p.rol, COUNT(*) AS count FROM Profesional p JOIN p.localidad l WHERE l.nombre =:lugar GROUP BY p.rol";
			
			query2Text += "SELECT c.rol, COUNT(*) AS count FROM Ciudadano c JOIN c.localidad l WHERE l.nombre =:lugar GROUP BY c.rol";
			
			query1 = entityManager.createQuery(query1Text, Object[].class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object[].class);
			query2.setParameter("lugar", lugar);
		}
		
		List<Object[]> results1 = null;
		List<Object[]> results2 = null;
		if(tipo == 0) {
			results2 = query2.getResultList();
		} else if(tipo == 1) {
			results1 = query1.getResultList();
		} else {
			results1 = query1.getResultList();
			results2 = query2.getResultList();
		}
		/*
		List<Object[]> results1 = query1.getResultList();
		List<Object[]> results2 = null;
		if(query2 != null) {
			results2 = query2.getResultList();
		}
		query1.getResultList();
		*/
		Map<String, Long> resultados = new HashMap<String, Long>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object[] result : results1) {
			    resultados.put((String) result[0], (Long) result[1]);
			    System.out.println(result[0]);
			}
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
					Long count = resultados.get(result[0]);
					if(count != null) {
						count += (Long) result[1];
					} else {
						count = (Long) result[1];
					}
					
				    resultados.put((String) result[0], count);
				    System.out.println(result[0]);
				}
			}
			return resultados;
		} else {
			if(results2 != null && results2.size() > 0) {
				for (Object[] result : results2) {
				    resultados.put((String) result[0], (Long) result[1]);
				    System.out.println(result[0]);
				}
				return resultados;
			}
		}
		
		return null;
	}
	
	public List<Long> getAgeStatistics(String ambito, String lugar, int tipo) {
		String query1Text = "";
		String query2Text = "";
		TypedQuery<Object> query1 = null;
		TypedQuery<Object> query2 = null;
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT p.fechaNac FROM Profesional p";
			
			query2Text += "SELECT c.fechaNac FROM Ciudadano c";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query2 = entityManager.createQuery(query2Text, Object.class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {			
			query1Text += "SELECT p.fechaNac FROM Profesional p JOIN p.localidad l WHERE l.mancomunidad =:lugar";
			
			query2Text += "SELECT c.fechaNac FROM Ciudadano c JOIN c.localidad l WHERE l.mancomunidad =:lugar";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object.class);
			query2.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT p.fechaNac FROM Profesional p JOIN p.localidad l WHERE l.nombre =:lugar";
			
			query2Text += "SELECT c.fechaNac FROM Ciudadano c JOIN c.localidad l WHERE l.nombre =:lugar";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object.class);
			query2.setParameter("lugar", lugar);
		}
		

		List<Object> fechasNac1 = null;
		List<Object> fechasNac2 = null;
		if(tipo == 0) {
			fechasNac2 = query2.getResultList();
		} else if(tipo == 1) {
			fechasNac1 = query1.getResultList();
		} else {
			fechasNac1 = query1.getResultList();
			fechasNac2 = query2.getResultList();
		}
				
		//Map<String, Long> resultados = new HashMap<String, Long>();
		Date ahora = new Date();
		List<Long> resultados = new ArrayList<>();
	
		if(fechasNac1 != null && fechasNac1.size() > 0) {
			for (Object result : fechasNac1) {
				//UsuarioInterno usuario = (UsuarioInterno) result[0];
				java.sql.Date sqlDate = (java.sql.Date) result;
				Date fechaNac =  new java.util.Date(sqlDate.getTime());;
				long timeBetween = ahora.getTime() - fechaNac.getTime();
				double yearsBetween = timeBetween / 3.15576e+10;
				int age = (int) Math.floor(yearsBetween);
				resultados.add((long) age);
				System.out.println(age);
				//Double yeasrs = (double) Period.between(fechaNac, ahora).getYears();
				//resultados.add(yeasrs);
			}
		}
		if(fechasNac2 != null && fechasNac2.size() > 0) {
			for (Object result : fechasNac2) {
				//UsuarioInterno usuario = (UsuarioInterno) result[0];
				java.sql.Date sqlDate = (java.sql.Date) result;
				Date fechaNac =  new java.util.Date(sqlDate.getTime());;
				long timeBetween = ahora.getTime() - fechaNac.getTime();
				double yearsBetween = timeBetween / 3.15576e+10;
				int age = (int) Math.floor(yearsBetween);
				resultados.add((long) age);
				System.out.println(age);
				//Double yeasrs = (double) Period.between(fechaNac, ahora).getYears();
				//resultados.add(yeasrs);
			}
			
		}
		if(resultados.size() > 0) {
			System.out.println(resultados.size());
			return resultados;
		} else {
			return null;
		}
		
	}
	
	public List<Localidad> getLocationStatistics(String ambito, String lugar, int tipo) {
		String query1Text = "";
		String query2Text = "";
		TypedQuery<Object> query1 = null;
		TypedQuery<Object> query2 = null;
		if(ambito.equals(NivelAcceso.GENERAL.toString())) {
			query1Text += "SELECT p.localidad FROM Profesional p";
			
			query2Text += "SELECT c.localidad FROM Ciudadano c";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query2 = entityManager.createQuery(query2Text, Object.class);
		} else if(ambito.equals(NivelAcceso.MANCOMUNAL.toString())) {			
			query1Text += "SELECT p.localidad FROM Profesional p JOIN p.localidad l WHERE l.mancomunidad =:lugar";
			
			query2Text += "SELECT c.localidad FROM Ciudadano c JOIN c.localidad l WHERE l.mancomunidad =:lugar";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object.class);
			query2.setParameter("lugar", lugar);
		} else if(ambito.equals(NivelAcceso.MUNICIPAL.toString())) {
			query1Text += "SELECT p.localidad FROM Profesional p JOIN p.localidad l WHERE l.nombre =:lugar";
			
			query2Text += "SELECT c.localidad FROM Ciudadano c JOIN c.localidad l WHERE l.nombre =:lugar";
			
			query1 = entityManager.createQuery(query1Text, Object.class);
			query1.setParameter("lugar", lugar);
			query2 = entityManager.createQuery(query2Text, Object.class);
			query2.setParameter("lugar", lugar);
		}
		

		List<Object> results1 = null;
		List<Object> results2 = null;
		if(tipo == 0) {
			results2 = query2.getResultList();
		} else if(tipo == 1) {
			results1 = query1.getResultList();
		} else {
			results1 = query1.getResultList();
			results2 = query2.getResultList();
		}
				
		//Map<String, Long> resultados = new HashMap<String, Long>();
		Date ahora = new Date();
		List<Localidad> resultados = new ArrayList<>();
	
		if(results1 != null && results1.size() > 0) {
			for (Object result : results1) {
				//UsuarioInterno usuario = (UsuarioInterno) result[0];
				Localidad localidad = (Localidad) result;
				resultados.add(localidad);
				System.out.println(localidad);
				//Double yeasrs = (double) Period.between(fechaNac, ahora).getYears();
				//resultados.add(yeasrs);
			}
		}
		if(results2 != null && results2.size() > 0) {
			for (Object result : results2) {
				//UsuarioInterno usuario = (UsuarioInterno) result[0];
				Localidad localidad = (Localidad) result;
				resultados.add(localidad);
				System.out.println(localidad);
				//Double yeasrs = (double) Period.between(fechaNac, ahora).getYears();
				//resultados.add(yeasrs);
			}
			
		}
		if(resultados.size() > 0) {
			System.out.println(resultados.size());
			return resultados;
		} else {
			return null;
		}
		
	}

	public List<UsuarioInterno> search(String keyword) {
String[] splitStr = keyword.split("\\s+");
		
		String queryText = "SELECT u FROM UsuarioInterno u ";
		
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
						where += "WHERE (u.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR u.apellidos LIKE '%' || :" + "param" + i + " || '%')"
								+ "ORDER BY u.apellidos";
						
					} else if (first && !last){
						first = false;
						where += "WHERE (u.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR u.apellidos LIKE '%' || :" + "param" + i + " || '%')";
						
					} else if(!first && last) {
						where +="AND (u.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR u.apellidos LIKE '%' || :" + "param" + i + " || '%')"
								+ "ORDER BY u.apellidos";
					} else if(!first && !last) {
						where += "AND (u.nombre LIKE '%' || :" + "param" + i + " || '%'"
								+ "OR u.apellidos LIKE '%' || :" + "param" + i + " || '%')";
					}
					queryText += where;
				}
			}
			 Query query = entityManager.createQuery(queryText);
			 
			 Set<Entry<String, Object>> setParameters = parameters.entrySet();
			 for (Entry<String, Object> entry : setParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			 }
			 
			 List<UsuarioInterno> usuarios =  query.getResultList();
				
			 if(usuarios != null && usuarios.size() > 0) {
				return usuarios;
			 }
		}
		return null;
	}
}
