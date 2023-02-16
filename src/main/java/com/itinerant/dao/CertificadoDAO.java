package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Certificado;
import com.itinerant.entity.Visita;

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

	public List<Certificado> listAllByLogin(String login) {
		List<Certificado> certificados = super.findWithNamedQuery("Certificado.findAllByLogin", "login", login);
		
		if(certificados != null && certificados.size() > 0) {
			return certificados;
		}
		
		return null;
	}
	
	public List<Certificado> listAllValidByLogin(String login) {
		List<Certificado> certificados = super.findWithNamedQuery("Certificado.findAllValidByLogin", "login", login);
		
		if(certificados != null && certificados.size() > 0) {
			return certificados;
		}
		
		return null;
	}

}
