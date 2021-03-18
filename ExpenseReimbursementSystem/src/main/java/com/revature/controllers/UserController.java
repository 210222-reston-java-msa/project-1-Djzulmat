package com.revature.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.models.User;
import com.revature.services.UserService;

public class UserController {

	public static UserService userv = new UserService();

	public void processGet(HttpServletRequest request, HttpServletResponse response) {
		String actualURI = request.getRequestURI().substring(request.getContextPath().length());

		switch (actualURI) {
		
		case "/users/logout":
			
			processLogout(request, response);
			break;
			
		}
		
	}

	public void processPost(HttpServletRequest request, HttpServletResponse response) {
		String actualURL = request.getRequestURI().substring(request.getContextPath().length());

		switch (actualURL) {
		case "/users/login":
			processLogin(request, response);
			break;
		case "/users/register":
			processRegister(request, response);
			break;
		}
	}

	private void processRegister(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			String json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();
			ObjectMapper om = new ObjectMapper();
			User u = om.readValue(json, User.class);
			boolean newUser = UserService.register(u);
			//u.setId(newUser);

			
			if (newUser == false) {
				
				response.setStatus(401);
				
			} else {
				
				request.getSession().setAttribute("user", newUser);
			}
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	private void processLogin(HttpServletRequest request, HttpServletResponse response) {
		
		
		
		try {
			
			String json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();
			ObjectMapper om = new ObjectMapper();
			User u = om.readValue(json, User.class);
			User currentUser = UserService.login(u);
			ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
			String jsonUser = ow.writeValueAsString(currentUser);

			if (currentUser == null) {

				response.setStatus(401);

			} else {

				request.getSession().setAttribute("user", currentUser.getId());
				response.setHeader("user", jsonUser);
			}

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	private void processLogout(HttpServletRequest request, HttpServletResponse response) {

		request.getSession().setAttribute("user", null);

	}

}
