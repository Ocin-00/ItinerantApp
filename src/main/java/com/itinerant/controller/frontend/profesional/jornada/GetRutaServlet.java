package com.itinerant.controller.frontend.profesional.jornada;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itinerant.controller.BaseServlet;
import com.itinerant.service.AlertaServicios;
import com.itinerant.service.JornadaServicios;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class AnularCertificadoServlet
 */
@WebServlet("/profesional/get_ruta")
public class GetRutaServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    public GetRutaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Client client = ClientBuilder.newClient();
		 //List<Double[]> waypoints = new ArrayList<Double[]>();
	        // Parse the waypoints from the request
		// Entity<String> PAY2 = Entity.json({"coordinates":[[8.681495,49.41461],[8.686507,49.41943],[8.687872,49.420318]]});
		 	//Entity<String> payload = Entity.json({"coordinates":request.getParameter("waypoints").toString()});
	        String[] coordinates = request.getParameter("waypoints").split(";");
	        JSONArray waypoints = new JSONArray();
	        for (String coord : coordinates) {
	            String[] latLng = coord.split(",");
	            //Double[] waypoint = { Double.parseDouble(latLng[0]), Double.parseDouble(latLng[1]) };
	            //waypoints.add(waypoint);
	            
	            waypoints.put(new JSONArray().put(Double.parseDouble(latLng[0])).put(Double.parseDouble(latLng[1])));
	        }
	        JSONObject payloadjson = new JSONObject();
	        payloadjson.put("coordinates", waypoints);
	        String payloadStr = payloadjson.toString();
	        Entity<String> payload = Entity.json(payloadStr);

	        Response resp = client.target("https://api.openrouteservice.org/v2/directions/driving-car/json")
	        		  .request()
	        		  .header("Authorization", "5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc")
	        		  .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
	        		  .header("Content-Type", "application/json; charset=utf-8")
	        		  .post(payload);
	        /*
	        StringBuilder urlBuilder = new StringBuilder("https://api.openrouteservice.org/v2/directions/driving-car/json");
	        urlBuilder.append("?api_key=5b3ce3597851110001cf62488e46d28f49534f3094ceb181a7bfe9cc");
	        urlBuilder.append("&coordinates=");
	        for (Double[] waypoint : waypoints) {
	            urlBuilder.append(waypoint[0]);
	            urlBuilder.append(",");
	            urlBuilder.append(waypoint[1]);
	            urlBuilder.append(";");
	        }
	        urlBuilder.setLength(urlBuilder.length() - 1);

	        URL url = new URL(urlBuilder.toString());
	        ObjectMapper mapper = new ObjectMapper();
	        JsonNode route = mapper.readTree(url);
*/
	        
	        response.setContentType("application/json");
	        response.getWriter().write(resp.readEntity(String.class));
	}

}
