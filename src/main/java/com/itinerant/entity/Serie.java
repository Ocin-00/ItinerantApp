package com.itinerant.entity;
// Generated 26 ene 2023 18:37:14 by Hibernate Tools 4.3.6.Final

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Serie generated by hbm2java
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "Serie.findAll", query = "SELECT s FROM Serie s"),
	 @NamedQuery(name = "Serie.findAllByLogin", query = "SELECT s FROM Serie s WHERE s.profesional.login = :login"),
	 @NamedQuery(name = "Serie.countAll", query = "SELECT count(*) FROM Serie s"),
})
@Table(name = "serie", catalog = "itinerant_db")
public class Serie implements java.io.Serializable {

	private Integer idSerie;
	private Profesional profesional;
	private String nombre;
	private Set<SerieJornadas> serieJornadases = new HashSet<SerieJornadas>(0);

	public Serie() {
	}

	public Serie(Profesional profesional, String nombre) {
		this.profesional = profesional;
		this.nombre = nombre;
	}

	public Serie(Profesional profesional, String nombre, Set<SerieJornadas> serieJornadases) {
		this.profesional = profesional;
		this.nombre = nombre;
		this.serieJornadases = serieJornadases;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id_serie", unique = true, nullable = false)
	public Integer getIdSerie() {
		return this.idSerie;
	}

	public void setIdSerie(Integer idSerie) {
		this.idSerie = idSerie;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_profesional", nullable = false)
	public Profesional getProfesional() {
		return this.profesional;
	}

	public void setProfesional(Profesional profesional) {
		this.profesional = profesional;
	}

	@Column(name = "nombre", nullable = false, length = 100)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serie")
	public Set<SerieJornadas> getSerieJornadases() {
		return this.serieJornadases;
	}

	public void setSerieJornadases(Set<SerieJornadas> serieJornadases) {
		this.serieJornadases = serieJornadases;
	}

}