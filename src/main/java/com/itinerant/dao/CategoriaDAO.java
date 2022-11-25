package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Categoria;
import com.itinerant.entity.Visita;


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
}
