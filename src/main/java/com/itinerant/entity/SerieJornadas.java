package com.itinerant.entity;
// Generated 26 ene 2023 18:37:14 by Hibernate Tools 4.3.6.Final

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

/**
 * SerieJornadas generated by hbm2java
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "SerieJornadas.findAll", query = "SELECT j FROM SerieJornadas j"),
	 @NamedQuery(name = "SerieJornadas.findAllById", query = "SELECT j FROM SerieJornadas j WHERE j.id.idSerie = :id"),
	 @NamedQuery(name = "SerieJornadas.countAll", query = "SELECT count(*) FROM SerieJornadas j"),
})
@Table(name = "serie_jornadas", catalog = "itinerant_db")
public class SerieJornadas implements java.io.Serializable {

	private SerieJornadasId id;
	private Serie serie;

	public SerieJornadas() {
	}

	public SerieJornadas(SerieJornadasId id, Serie serie) {
		this.id = id;
		this.serie = serie;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idSerie", column = @Column(name = "id_serie", nullable = false)),
			@AttributeOverride(name = "fecha", column = @Column(name = "fecha", nullable = false, length = 10)) })
	public SerieJornadasId getId() {
		return this.id;
	}

	public void setId(SerieJornadasId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_serie", nullable = false, insertable = false, updatable = false)
	public Serie getSerie() {
		return this.serie;
	}

	public void setSerie(Serie serie) {
		this.serie = serie;
	}

}