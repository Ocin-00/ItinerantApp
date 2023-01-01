package com.itinerant.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.itinerant.entity.Alerta;
import com.itinerant.entity.Chat;
import com.itinerant.entity.ChatMensaje;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    public ServletContextListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	HashMap<String, Stack<Alerta>> pilasUsuarios = new HashMap<String, Stack<Alerta>>();
    	sce.getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
    	
    	HashMap<String, List<Chat>> chats = new HashMap<String, List<Chat>>();
    	sce.getServletContext().setAttribute("chats", chats);
    	
    	HashMap<Integer, List<ChatMensaje>> mensajesChat = new HashMap<Integer, List<ChatMensaje>>();
    	sce.getServletContext().setAttribute("mensajesChat", mensajesChat);
    }
}
