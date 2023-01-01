package com.itinerant.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/{userLogin}")
public class ChatEndpoint {

	private static Map<String, String> usersMap = new ConcurrentHashMap<>();
	private static final Set<ChatEndpoint> connections = new CopyOnWriteArraySet<>();
	private String userLogin;
	private Session session;
		  
	public ChatEndpoint() {
		userLogin = null;
	}
	@OnOpen
	public void onOpen(Session session, @PathParam("userLogin") String login) {
		System.out.println("ONOPEN");
		//String login = session.getQueryString().split("=")[1];
		//sessions.put(login, session);
		this.session = session;
		connections.add(this);
		this.userLogin = login;
		usersMap.put(session.getId(), login);
	}

	@OnClose
	public void onClose(Session session) {
		System.out.println("ONCLOSE");
		//String login = session.getQueryString().split("=")[1];
		//sessions.remove(login, session);
		connections.remove(this);
		usersMap.remove(session.getId());
	}

	@OnMessage
	public void onMessage(String message, Session session) throws IOException {
		System.out.println(message);
		/*String[] parts = message.split(":");
        String to = parts[0];
        String msg = parts[1];
        session.get(to).getAsyncRemote().sendText(msg);*/
		/*
		String from = extractFrom(message); //extracting sender
		String to = extractTo(message); //extracting recipient
		String actualMessage = extractActualMessage(message); //extracting actual message
		sendMessageToUser(to, from, actualMessage);*/
	}
	/*
	@OnError
	public void onError(Throwable t) {
	  // log error
	}*/
	/*
	private String extractActualMessage(String message) {
		String[] firstSplit = message.split("\\|");
		String[] secondSplit = firstSplit[3].split("~");
		String match = "MSG";
		if (secondSplit[0].equals(match)) {
			return secondSplit[1];
		}
		return null;
	}

	private String extractTo(String message) {
		String[] firstSplit = message.split("\\|");
		String[] secondSplit = firstSplit[2].split("~");
		String match = "TO";
		if (secondSplit[0].equals(match)) {
			return secondSplit[1];
		}
		return null;
	}

	private String extractFrom(String message) {
		String[] firstSplit = message.split("\\|");
		String[] secondSplit = firstSplit[1].split("~");
		String match = "FROM";
		if (secondSplit[0].equals(match)) {
			return secondSplit[1];
		}
		return null;
	}

	private void sendMessageToUser(String to, String from, String actualMessage) {
		String toSessionId = getSessionIdOfUser(to); // getting sessionid of recipient
		String messageToSend = prepareMessage(to, from, actualMessage); // preparing proper format of msg
		broadcast(messageToSend, toSessionId); // sending the message to recipient
	}

	private void broadcast(String messageToSend, String toSessionId) {
		for (ChatEndpoint client : connections) {
			try {
				synchronized (client) {
					// comparing the session id
					if (client.session.getId().equals(toSessionId)) {
						client.session.getBasicRemote().sendText(messageToSend); // send message to the user
					}
				}
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
				}
				String message = String.format("* %s %s", client.userLogin, "has been disconnected.");
				broadcast(message);
			}
		}
	}

	private static void broadcast(String msg) {
		for (ChatEndpoint client : connections) {
			try {
				synchronized (client) {
					client.session.getBasicRemote().sendText(msg); // broadcasting to all connected clients
				}
			} catch (IOException e) {
				connections.remove(client);
				try {
					client.session.close();
				} catch (IOException e1) {
				}
				String message = String.format("* %s %s", client.userLogin, "has been disconnected.");
				broadcast(message);
			}
		}
	}*/
}
