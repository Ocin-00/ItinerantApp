package com.itinerant.entity;
// Generated 5 jul 2022 14:47:27 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.itinerant.enums.Rol;

/**
 * Supervisor generated by hbm2java
 */
@Entity
@NamedQueries({
	 @NamedQuery(name = "Administrador.findAll", query = "SELECT a FROM Administrador a ORDER BY a.apellidos"),
	 @NamedQuery(name = "Administrador.findByEmail", query = "SELECT a FROM Administrador a WHERE a.email = :email"),
	 @NamedQuery(name = "Administrador.countAll", query = "SELECT count(*) FROM Administrador a"),
	 @NamedQuery(name = "Administrador.checkLogin", query = "SELECT a FROM Administrador a WHERE a.login = :login AND a.password = :password")

})
@Table(name = "administrador", catalog = "itinerant_db")
public class Administrador extends UsuarioPrivilegiado {

	public Administrador() {
	}

	public Administrador(String login, String password, String email, String nombre,String apellidos, 
			Date fechaNac, String telefono, String nss,String organismoCoordinador) {
		super(login, password, email, nombre, apellidos,Rol.ADMINISTRADOR.toString(), fechaNac, telefono, nss, organismoCoordinador);
	}
	
	public Administrador(String login, String password, String email, String nombre,String apellidos, 
			Date fechaNac, String telefono, String nss,String organismoCoordinador,String imagenRuta, 
			Set<ChatMensaje> chatMensajesForIdRecipient, Set<Alerta> alertas, Set<Chat> chatsForIdRecipient, 
			Set<Chat> chatsForIdSender, Set<ChatMensaje> chatMensajesForIdSender) {
		super(login, password, email, nombre, apellidos, Rol.ADMINISTRADOR.toString(), fechaNac, telefono, nss, organismoCoordinador,
				imagenRuta, chatMensajesForIdRecipient, alertas, chatsForIdRecipient, chatsForIdSender, chatMensajesForIdSender);
	}

}
