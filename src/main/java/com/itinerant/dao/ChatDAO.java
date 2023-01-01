package com.itinerant.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import com.itinerant.entity.Chat;
import com.itinerant.entity.UsuarioInterno;

public class ChatDAO extends JpaDAO<Chat> implements GenericDAO<Chat> {
	
	public ChatDAO(EntityManager entitiyManager) {
		super(entitiyManager);
	}
	
	public Chat create(Chat chat) {
		return super.create(chat);
	}

	@Override
	public Chat update(Chat chat) {
		return super.update(chat);
	}

	@Override
	public Chat get(Object chatId) {
		return super.find(Chat.class, chatId);
	}

	@Override
	public void delete(Object chatId) {
		super.delete(Chat.class, chatId);
	}

	@Override
	public List<Chat> listAll() {
		return super.findWithNamedQuery("Chat.findAll");
	}

	@Override
	public long count() {
		return super.countWithNamedQuery("Chat.countAll");
	}

	public List<Chat> findAllByLogin(String login) {
		List<Chat> chats = super.findWithNamedQuery("Chat.findAllByLogin", "login", login);
		
		if(chats != null && chats.size() > 0) {
			return chats;
		}
		
		return null;
			
	}
	
	public Chat findBySenderAndRecipient(String sender, String recipient) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("sender", sender);
		parameters.put("recipient", recipient);
		
		List<Chat> chats  = super.findWithNamedQuery("Chat.findAllBySenderAndRecipient", parameters);
		
		if(chats != null && chats.size() > 0) {
			return chats.get(0);
		}
		
		return null;
			
	}
}
