package com.itinerant.dao;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class JpaDAO<E> {
	protected EntityManager entityManager;

	public JpaDAO(EntityManager entitiyManager) {
		super();
		this.entityManager = entitiyManager;
	}
	
	public E create(E entity) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.flush();
			entityManager.refresh(entity);
			entityManager.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
	
	public E update(E entity) {
		try {
			entityManager.getTransaction().begin();
			entity = entityManager.merge(entity);
			entityManager.getTransaction().commit();
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
	}
	
	public E find(Class<E> type, Object id) {
		try {
			E entity = entityManager.find(type, id);
			if(entity != null) {
				entityManager.refresh(entity);			
			}
			return entity;
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public void delete(Class<E> type, Object id) {
		try {
			entityManager.getTransaction().begin();
			Object reference = entityManager.getReference(type, id);
			entityManager.remove(reference);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public List<E> findWithNamedQuery(String queryName) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			return query.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public List<E> findWithNamedQuery(String queryName, String paramName, Object paramValue) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			query.setParameter(paramName, paramValue);
			return query.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public List<E> findWithNamedQuery(String queryName, Map<String, Object> parameters) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			
			Set<Entry<String, Object>> setParameters = parameters.entrySet();
			for (Entry<String, Object> entry : setParameters) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
			return query.getResultList();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}
		
	}
	
	public long countWithNamedQuery(String queryName) {
		try {
			Query query = entityManager.createNamedQuery(queryName);
			return (long) query.getSingleResult();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw e;
		}		
	}
}
