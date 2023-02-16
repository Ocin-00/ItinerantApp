package com.itinerant.auxClasses;

public class Viaje {
	private String trayecto;
	private double tiempo;
	
	public Viaje(String trayecto, double tiempo) {
		this.trayecto = trayecto;
		this.tiempo = tiempo;
	}

	public String getTrayecto() {
		return trayecto;
	}

	public void setTrayecto(String trayecto) {
		this.trayecto = trayecto;
	}

	public double getTiempo() {
		return tiempo;
	}

	public void setTiempo(double tiempo) {
		this.tiempo = tiempo;
	}
	
	
}
