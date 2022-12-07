package com.itinerant.entity;
// Generated 16 ago 2022 11:43:03 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Cita generated by hbm2java
 */
@Entity
@NamedQueries({
	 @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c ORDER BY c.horaInicio"),
	 @NamedQuery(name = "Cita.countAll", query = "SELECT count(*) FROM Cita c"),
})
@Table(name = "cita", catalog = "itinerant_db")
public class Cita implements java.io.Serializable {

	private CitaId id;
	private Ciudadano ciudadano;
	private Visita visita;
	private Date horaInicio;
	private String direccion;
	private String anotaciones;
	private String review;
	private Integer puntuacion;

	public Cita() {
	}

	public Cita(CitaId id, Ciudadano ciudadano, Visita visita, Date horaInicio, String direccion) {
		this.id = id;
		this.ciudadano = ciudadano;
		this.visita = visita;
		this.horaInicio = horaInicio;
		this.direccion = direccion;
	}

	public Cita(CitaId id, Ciudadano ciudadano, Visita visita, Date horaInicio, String direccion, String anotaciones, String review, Integer puntuacion) {
		this.id = id;
		this.ciudadano = ciudadano;
		this.visita = visita;
		this.horaInicio = horaInicio;
		this.direccion = direccion;
		this.anotaciones = anotaciones;
		this.review = review;
		this.puntuacion = puntuacion;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idVisita", column = @Column(name = "id_visita", nullable = false)),
			@AttributeOverride(name = "idCiudadano", column = @Column(name = "id_ciudadano", nullable = false, length = 30)) })
	public CitaId getId() {
		return this.id;
	}

	public void setId(CitaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ciudadano", nullable = false, insertable = false, updatable = false)
	public Ciudadano getCiudadano() {
		return this.ciudadano;
	}

	public void setCiudadano(Ciudadano ciudadano) {
		this.ciudadano = ciudadano;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_visita", nullable = false, insertable = false, updatable = false)
	public Visita getVisita() {
		return this.visita;
	}

	public void setVisita(Visita visita) {
		this.visita = visita;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "hora_inicio", nullable = false, length = 19)
	public Date getHoraInicio() {
		return this.horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	@Column(name = "direccion", nullable = false, length = 100)
	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Column(name = "anotaciones", length = 200)
	public String getAnotaciones() {
		return this.anotaciones;
	}

	public void setAnotaciones(String anotaciones) {
		this.anotaciones = anotaciones;
	}
	
	@Column(name = "review", length = 200)
	public String getReview() {
		return this.review;
	}

	public void setReview(String review) {
		this.review = review;
	}
	
	@Column(name = "puntuacion")
	public Integer getPuntuacion() {
		return this.puntuacion;
	}

	public void setPuntuacion(Integer puntuacion) {
		this.puntuacion = puntuacion;
	}

}
