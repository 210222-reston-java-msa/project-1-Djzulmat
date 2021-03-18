package com.revature.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;

public class DispatcherServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ReimbursementController rctrl = new ReimbursementController();
	private UserController uctrl = new UserController();
	
	// transfers doGet requests to their controllers
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String actualURL = request.getRequestURI().substring(request.getContextPath().length());
		
		if(actualURL.startsWith("/Static")) {
			super.doGet(request, response);
			return;
		}
		else if(actualURL.startsWith("/reimbursements")) {
			rctrl.processGet(request, response);
		}
		else if(actualURL.startsWith("/users/")){
			uctrl.processGet(request, response);
		}
	}
	
	
	// transfers doPost requests to their controllers
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String actualURL = request.getRequestURI().substring(request.getContextPath().length());
		
		if(actualURL.startsWith("/users/")) {
			uctrl.processPost(request, response);
		}
		else if(actualURL.startsWith("/reimbursements")) {
			rctrl.processPost(request,response);
		}
	}
}