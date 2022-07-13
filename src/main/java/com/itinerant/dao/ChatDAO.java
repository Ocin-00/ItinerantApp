package com.itinerant.dao;

import java.util.List;
import javax.persistence.EntityManager;
import com.itinerant.entity.Chat;

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
}
