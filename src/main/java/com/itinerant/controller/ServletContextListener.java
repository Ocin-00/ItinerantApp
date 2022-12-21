package com.itinerant.controller;

import java.util.HashMap;
import java.util.Stack;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import com.itinerant.entity.Alerta;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    public ServletContextListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	HashMap<String, Stack<Alerta>> pilasUsuarios = new HashMap<String, Stack<Alerta>>();
    	sce.getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
		//request.getSession().getServletContext().setAttribute("PilasUsuarios", pilasUsuarios);
    }
}
