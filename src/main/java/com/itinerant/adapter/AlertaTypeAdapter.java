package com.itinerant.adapter;

import java.io.IOException;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.itinerant.entity.Alerta;

public class AlertaTypeAdapter extends TypeAdapter<Alerta>{

	@Override
	public void write(JsonWriter out, Alerta value) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Alerta read(JsonReader in) throws IOException {
		Alerta alerta = new Alerta(); 
	      in.beginObject(); 
	      String fieldname = null; 
	      
	      while (in.hasNext()) { 
	         JsonToken token = in.peek();            
	         
	         if (token.equals(JsonToken.NAME)) {     
	            //get the current token 
	            fieldname = in.nextName(); 
	         } 
	         
	         if ("idAlerta".equals(fieldname)) {       
			     //move to next token 
			     token = in.peek(); 
			     alerta.setIdAlerta(in.nextInt()); 
	         } 
	         
	         if ("titulo".equals(fieldname)) {       
	            //move to next token 
	            token = in.peek(); 
	            alerta.setTitulo(fieldname); 
	         } 
	         
	         if ("cuerpo".equals(fieldname)) {       
			     //move to next token 
			     token = in.peek(); 
			     alerta.setCuerpo(fieldname); 
		     }
	         
	         if ("visto".equals(fieldname)) {       
			     //move to next token 
			     token = in.peek(); 
			     alerta.setVisto(in.nextBoolean()); 
		     }  
	         /*
	         if ("usuarioInterno".equals(fieldname)) {       
			     //move to next token 
			     token = in.peek(); 
			     alerta.setUsuarioInterno(in.); 
		     }  */
	      } 
	      in.endObject(); 
	      return alerta; 
		return null;
	}

}
