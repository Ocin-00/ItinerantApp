package com.itinerant.entity;
// Generated 5 jul 2022 14:47:27 by Hibernate Tools 4.3.6.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Localidad generated by hbm2java
 */
@Entity
@NamedQueries({
	 @NamedQuery(name = "Localidad.findAll", query = "SELECT l FROM Localidad l ORDER BY l.nombre"),
	 @NamedQuery(name = "Localidad.countAll", query = "SELECT count(*) FROM Localidad l"),
	 @NamedQuery(name = "Localidad.findMancomunidades", query = "SELECT DISTINCT l.mancomunidad FROM Localidad l where l.mancomunidad is not null ORDER BY l.mancomunidad"),
	 @NamedQuery(name = "Localidad.findLocalidadesByMancomunidad", query = "SELECT l FROM Localidad l WHERE l.mancomunidad = :mancomunidad ORDER BY l.nombre"),
	 @NamedQuery(name = "Localidad.findByName", query = "SELECT l FROM Localidad l WHERE l.nombre = :nombre"),
})
@Table(name = "localidad", catalog = "itinerant_db")
public class Localidad implements java.io.Serializable {

	private int codigoPostal;
	private String nombre;
	private String comarca;
	private int poblacion;
	private String mancomunidad;
	private String provincia;
	private Set<Visita> visitas = new HashSet<Visita>(0);

	public Localidad() {
	}

	public Localidad(int codigoPostal, String nombre, String comarca, String provincia, int poblacion) {
		this.codigoPostal = codigoPostal;
		this.nombre = nombre;
		this.comarca = comarca;
		this.poblacion = poblacion;
		this.provincia = provincia;
	}

	public Localidad(int codigoPostal, String nombre, String comarca, String provincia, int poblacion, String mancomunidad, 
			Set<Visita> visitas) {
		this.codigoPostal = codigoPostal;
		this.nombre = nombre;
		this.comarca = comarca;
		this.poblacion = poblacion;
		this.mancomunidad = mancomunidad;
		this.visitas = visitas;
		this.provincia = provincia;
	}

	@Id

	@Column(name = "codigo_postal", unique = true, nullable = false)
	public int getCodigoPostal() {
		return this.codigoPostal;
	}

	public void setCodigoPostal(int codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	@Column(name = "nombre", nullable = false, length = 50)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "comarca", nullable = false, length = 100)
	public String getComarca() {
		return this.comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}
	
	@Column(name = "provincia", nullable = false, length = 100)
	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	@Column(name = "poblacion", nullable = false)
	public int getPoblacion() {
		return this.poblacion;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}

	@Column(name = "mancomunidad", length = 100)
	public String getMancomunidad() {
		return this.mancomunidad;
	}

	public void setMancomunidad(String mancomunidad) {
		this.mancomunidad = mancomunidad;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "localidad")
	public Set<Visita> getVisitas() {
		return this.visitas;
	}

	public void setVisitas(Set<Visita> visitas) {
		this.visitas = visitas;
	}

	public String toString() {
		return this.nombre;
	}

}
