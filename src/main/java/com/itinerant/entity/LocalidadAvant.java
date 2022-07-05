package com.itinerant.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "localidades_avant")
public class LocalidadAvant {
	private int CodPostal;
	private String nombre;
	private String comarca;
	private String mancomunidad;
	private int poblacion;

	@Column(name = "codigo_postal")
	@Id
	public int getCodPostal() {
		return CodPostal;
	}

	public void setCodPostal(int codPostal) {
		CodPostal = codPostal;
	}
	
	@Column(name = "nombre")
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "comarca")
	public String getComarca() {
		return comarca;
	}

	public void setComarca(String comarca) {
		this.comarca = comarca;
	}

	@Column(name = "mancomunidad")
	public String getMancomunidad() {
		return mancomunidad;
	}

	public void setMancomunidad(String mancomunidad) {
		this.mancomunidad = mancomunidad;
	}
	
	@Column(name = "poblacion")
	public int getPoblacion() {
		return poblacion;
	}

	public void setPoblacion(int poblacion) {
		this.poblacion = poblacion;
	}
}
