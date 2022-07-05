package com.itinerant.entity;
// Generated 5 jul 2022 14:47:27 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * Profesional generated by hbm2java
 */
@Entity
@Table(name = "profesional", catalog = "itinerant_db")
public class Profesional extends UsuarioInterno implements java.io.Serializable {

	private String localizacion;
	private String formacion;
	private String telefono;
	private String sexo;
	private String estadoCivil;
	private String descripcion;
	private Set<Visita> visitas = new HashSet<Visita>(0);
	private Set<Certificado> certificados = new HashSet<Certificado>(0);

	public Profesional() {
	}

	public Profesional(String login, String password, String email, String nombre, String apellidos, Date fechaNac,
			String localizacion, String formacion, String telefono) {
		super(login, password, email, nombre, apellidos, fechaNac);
		this.localizacion = localizacion;
		this.formacion = formacion;
		this.telefono = telefono;
	}

	public Profesional(String login, String password, String email, String nombre, String apellidos, Date fechaNac,
			String localizacion, String formacion, String telefono, String sexo, String estadoCivil, String descripcion,
			byte[] fotoPerfil, Set<Visita> visitas, Set<Certificado> certificados, Set<Chat> chatsForUsuarioFuente,
			Set<Alerta> alertas, Set<Chat> chatsForUsuarioDestino) {
		super(login, password, email, nombre, apellidos, fechaNac, fotoPerfil, chatsForUsuarioFuente, alertas,
				chatsForUsuarioDestino);
		this.localizacion = localizacion;
		this.formacion = formacion;
		this.telefono = telefono;
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.descripcion = descripcion;
		this.visitas = visitas;
		this.certificados = certificados;
	}

	@Column(name = "localizacion", nullable = false, length = 100)
	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	@Column(name = "formacion", nullable = false, length = 200)
	public String getFormacion() {
		return this.formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	@Column(name = "telefono", nullable = false, length = 10)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "sexo", length = 20)
	public String getSexo() {
		return this.sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	@Column(name = "estado_civil", length = 50)
	public String getEstadoCivil() {
		return this.estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	@Column(name = "descripcion", length = 200)
	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profesional")
	public Set<Visita> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(Set<Visita> visitas) {
		this.visitas = visitas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "profesional")
	public Set<Certificado> getCertificados() {
		return this.certificados;
	}

	public void setCertificados(Set<Certificado> certificados) {
		this.certificados = certificados;
	}

}
