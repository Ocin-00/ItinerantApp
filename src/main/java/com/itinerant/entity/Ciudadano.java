package com.itinerant.entity;
// Generated 5 jul 2022 14:47:27 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.itinerant.enums.Rol;

/**
 * Ciudadano generated by hbm2java
 */
@Entity
@NamedQueries({
	 @NamedQuery(name = "Ciudadano.findAll", query = "SELECT c FROM Ciudadano c ORDER BY c.apellidos"),
	 @NamedQuery(name = "Ciudadano.findByEmail", query = "SELECT c FROM Ciudadano c WHERE c.email = :email"),
	 @NamedQuery(name = "Ciudadano.countAll", query = "SELECT count(*) FROM Ciudadano c"),
	 @NamedQuery(name = "Ciudadano.checkLogin", query = "SELECT c FROM Ciudadano c WHERE c.login = :login AND c.password = :password"),
	 @NamedQuery(name = "Ciudadano.findAllSanctioned", query = "SELECT c FROM Ciudadano c WHERE c.propuestoSancion is true")

})
@Table(name = "ciudadano", catalog = "itinerant_db")
public class Ciudadano extends UsuarioInterno {

	private String localizacion;
	private Localidad localidad;
	private boolean propuestoSancion;
	private String sexo;
	private String estadoCivil;
	private String formacion;
	private String telefono;
	private Date finSancion;
	//private String direccion;
	private Set<Cita> citas = new HashSet<Cita>(0);

	public Ciudadano() {
	}

	public Ciudadano(String login, String password, String email, String nombre,
			String apellidos, Date fechaNac, String localizacion, boolean propuestoSancion, Localidad localidad) {
		super(login, password, email, nombre, apellidos, Rol.CIUDADANO.toString(), fechaNac);
		this.localizacion = localizacion;
		this.propuestoSancion = propuestoSancion;
		this.localidad = localidad;
	}

	public Ciudadano(String login, String password, String email, String nombre,
			String apellidos, Date fechaNac,String localizacion, boolean propuestoSancion, Localidad localidad, String sexo,
			String estadoCivil, String formacion, String telefono, String imagenRuta, Date finSancion, 
			Set<Cita> citas, Set<ChatMensaje> chatMensajesForIdRecipient, Set<Alerta> alertas,
			  Set<Chat> chatsForIdRecipient, Set<Chat> chatsForIdSender, Set<ChatMensaje> chatMensajesForIdSender) {
		super(login, password, email, nombre, apellidos, Rol.CIUDADANO.toString(), fechaNac, imagenRuta, chatMensajesForIdRecipient, alertas, 
				chatsForIdRecipient, chatsForIdSender, chatMensajesForIdSender);
		this.localizacion = localizacion;
		this.propuestoSancion = propuestoSancion;
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.formacion = formacion;
		this.telefono = telefono;
		this.finSancion = finSancion;
		this.citas = citas;
		this.localidad = localidad;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "localidad", nullable = false)
	public Localidad getLocalidad() {
		return this.localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}
	
	@Column(name = "localizacion", nullable = false, length = 100)
	public String getLocalizacion() {
		return this.localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	@Column(name = "propuesto_sancion", nullable = false)
	public boolean getPropuestoSancion() {
		return this.propuestoSancion;
	}

	public void setPropuestoSancion(boolean propuestoSancion) {
		this.propuestoSancion = propuestoSancion;
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

	@Column(name = "formacion", length = 200)
	public String getFormacion() {
		return this.formacion;
	}

	public void setFormacion(String formacion) {
		this.formacion = formacion;
	}

	@Column(name = "telefono", length = 10)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	@Column(name = "fin_sancion", length = 10)
	public Date getFinSancion() {
		return this.finSancion;
	}

	public void setFinSancion(Date finSancion) {
		this.finSancion = finSancion;
	}
	/*
	@Column(name = "direccion", length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	*/
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ciudadano")
	public Set<Cita> getCitas() {
		return this.citas;
	}

	public void setCitas(Set<Cita> citas) {
		this.citas = citas;
	}

}
