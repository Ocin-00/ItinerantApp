package com.itinerant.entity;
// Generated 5 jul 2022 14:47:27 by Hibernate Tools 4.3.6.Final

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.itinerant.enums.Rol;


/**
 * UsuarioPrivilegiado generated by hbm2java
 */
@Entity
@NamedQueries({
	 @NamedQuery(name = "UsuarioPrivilegiado.findAll", query = "SELECT up FROM UsuarioPrivilegiado up ORDER BY up.apellidos"),
	 @NamedQuery(name = "UsuarioPrivilegiado.findByEmail", query = "SELECT u FROM UsuarioPrivilegiado u WHERE u.email = :email"),
	 @NamedQuery(name = "UsuarioPrivilegiado.countAll", query = "SELECT count(*) FROM UsuarioPrivilegiado up"),
	 @NamedQuery(name = "UsuarioPrivilegiado.checkLogin", query = "SELECT u FROM UsuarioPrivilegiado u WHERE u.login = :login AND u.password = :password")

})
@Inheritance(
	    strategy = InheritanceType.JOINED
)
@Table(name = "usuario_privilegiado", catalog = "itinerant_db", uniqueConstraints = @UniqueConstraint(columnNames = "nss"))
public class UsuarioPrivilegiado extends UsuarioInterno {

	private String telefono;
	private String nss;
	private String organismoCoordinador;

	public UsuarioPrivilegiado() {
	}

	public UsuarioPrivilegiado(String login, String password, String email, String nombre, 
			String apellidos, String rol, Date fechaNac, String telefono, String nss,
			String organismoCoordinador) {
		super(login, password, email, nombre, apellidos, rol, fechaNac);
		this.telefono = telefono;
		this.nss = nss;
		this.organismoCoordinador = organismoCoordinador;
	}

	public UsuarioPrivilegiado(String login, String password, String email, String nombre, 
			String apellidos, String rol, Date fechaNac, String telefono, String nss, String organismoCoordinador,
			byte[] fotoPerfil, Set<Chat> chatsForUsuarioFuente,Set<Alerta> alertas, 
			Set<Chat> chatsForUsuarioDestino) {
		super(login, password, email, nombre, apellidos, rol, fechaNac, fotoPerfil, chatsForUsuarioFuente, alertas,
				chatsForUsuarioDestino);
		this.telefono = telefono;
		this.nss = nss;
		this.organismoCoordinador = organismoCoordinador;
	}

	@Column(name = "telefono", nullable = false, length = 10)
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Column(name = "nss", unique = true, nullable = false, length = 20)
	public String getNss() {
		return this.nss;
	}

	public void setNss(String nss) {
		this.nss = nss;
	}

	@Column(name = "organismo_coordinador", nullable = false, length = 100)
	public String getOrganismoCoordinador() {
		return this.organismoCoordinador;
	}

	public void setOrganismoCoordinador(String organismoCoordinador) {
		this.organismoCoordinador = organismoCoordinador;
	}

}
