package com.itinerant.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.itinerant.dao.LocalidadDAO;
import com.itinerant.entity.Localidad;

import org.json.JSONArray;
import org.json.JSONObject;

public class LocalidadServicios {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private LocalidadDAO localidadDAO;

	public LocalidadServicios(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		localidadDAO = new LocalidadDAO(entityManager);
		this.request = request;
		this.response = response;		
	}
	public Localidad getLocalidad(int codPostal) {
		return localidadDAO.get(localidadDAO);
	}
	public List<Localidad> listarLocalidades() {
		return localidadDAO.listAll();
	}

	public String getDireccion(Localidad localidad) {
		return localidad.getNombre() + ", VC, España";
	}
	
	public double[] getCoordenadas(Localidad localiad) {
		String direccion = getDireccion(localiad);
		String encodedDireccion = URLEncoder.encode(direccion, StandardCharsets.UTF_8);
		Client client = ClientBuilder.newClient();
		Response response = client.target(
		"https://api.openrouteservice.org/geocode/search"
		+ "?api_key=5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc"
		+ "&text=" + encodedDireccion + "&boundary.country=ES&layers=locality")
				.request(MediaType.TEXT_PLAIN_TYPE)
				.header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
				.get();

		String responseString = response.readEntity(String.class);
		System.out.println(responseString);
		JSONObject json = new JSONObject(responseString);
		JSONArray features = json.getJSONArray("features");
		JSONObject feature = features.getJSONObject(0);
		JSONObject geometry = feature.getJSONObject("geometry");
		double[] coords = new double[2];
		coords[0] = geometry.getJSONArray("coordinates").getDouble(0);
		coords[1] = geometry.getJSONArray("coordinates").getDouble(1);
		System.out.println(coords[0] + "; " + coords[1]);
		
		return coords;
	}
}
