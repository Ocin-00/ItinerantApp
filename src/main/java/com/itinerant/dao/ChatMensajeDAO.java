package com.itinerant.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.itinerant.entity.Chat;
import com.itinerant.entity.ChatMensaje;

public class ChatMensajeDAO extends JpaDAO<ChatMensaje> implements GenericDAO<ChatMensaje> {

	public ChatMensajeDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}

	@Override
	public ChatMensaje create(ChatMensaje chatMensaje) {
		return super.create(chatMensaje);
	}

	@Override
	public ChatMensaje update(ChatMensaje chatMensaje) {
		return super.update(chatMensaje);
	}

	@Override
	public ChatMensaje get(Object mensajeId) {
		return super.find(ChatMensaje.class, mensajeId);
	}

	@Override
	public void delete(Object mensajeId) {
		super.delete(ChatMensaje.class, mensajeId);
	}

	@Override
	public List<ChatMensaje> listAll() {
		return super.findWithNamedQuery("ChatMensaje.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("ChatMensaje.countAll");
	}	
	
	public List<ChatMensaje> findAllByChatId(Integer idChat) {
		List<ChatMensaje> mensajes = super.findWithNamedQuery("ChatMensaje.findAllByChatId", "idChat", idChat);
		
		if(mensajes != null && mensajes.size() > 0) {
			return mensajes;
		}
		
		return null;
			
	}
}
