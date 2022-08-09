package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Certificado;

public class CertificadoDAO extends JpaDAO<Certificado> implements GenericDAO<Certificado> {

	public CertificadoDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	@Override
	public Certificado create(Certificado certificado) {
		return super.create(certificado);
	}

	@Override
	public Certificado update(Certificado certificado) {
		return super.update(certificado);
	}

	@Override
	public Certificado get(Object certificadoId) {
		return super.find(Certificado.class, certificadoId);
	}

	@Override
	public void delete(Object certificadoId) {
		super.delete(Certificado.class, certificadoId);
	}

	@Override
	public List<Certificado> listAll() {
		return super.findWithNamedQuery("Certificado.findAll");
	}

	public List<Certificado> listAllNotValid() {
		return super.findWithNamedQuery("Certificado.findAllNotValid");
	}
	
	@Override
	public long count() {
		return super.countWithNamedQuery("Certificado.countAll");
	}

}
