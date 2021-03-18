package com.revature.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;

public class ReimbursementController {
	
	private ReimbursementService rserv = new ReimbursementService();
	
	
	public void processGet(HttpServletRequest request, HttpServletResponse response) {
		String actualURL = request.getRequestURI().substring(request.getContextPath().length());
	
		switch(actualURL) {
		case "/reimbursements/all":
			displayAllReimbursements(request,response);
			break;
		case "/reimbursements/user":
			getUserReimbursements(request,response);
			break;
		}
	}
	
	
	public void processPost(HttpServletRequest request, HttpServletResponse response) {
		String actualURL = request.getRequestURI().substring(request.getContextPath().length());
		
		switch(actualURL) {
		case "/reimbursements/add":
			addReimbursement(request,response);
			break;
		case "/reimbursements/approve":
			approveReimbursement(request,response);
			break;
		case "/reimbursements/deny":
			denyReimbursement(request,response);
			break;
		}
	}
	
	
	private void addReimbursement(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			String json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();
			ObjectMapper om = new ObjectMapper();
			Reimbursement reimb = om.readValue(json, Reimbursement.class);
			reimb.setAmount(reimb.getAmount());
			reimb.setAuthorId((Integer)request.getSession().getAttribute("user"));
			int newReimbursement = rserv.addReimbursement(reimb);
			
			reimb.setId(newReimbursement);
			
			
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private void denyReimbursement(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			String json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();
			ObjectMapper om = new ObjectMapper();
			Reimbursement reimb = om.readValue(json, Reimbursement.class);
			boolean success = rserv.changeStatus(reimb, "DENY", (Integer)request.getSession().getAttribute("user")); //(Integer)request.getSession().getAttribute("user")
			
			if(success == false) {
				
				response.setStatus(401);
				
			} else {
				
				response.setStatus(200);
				
			}
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
	}
	
	
	private void approveReimbursement(HttpServletRequest request, HttpServletResponse response) {
		
		
		try {
			
			String json = request.getReader().lines().reduce((acc, curr) -> acc + curr).get();			
			ObjectMapper om = new ObjectMapper();
			Reimbursement reimb = om.readValue(json, Reimbursement.class);
			boolean success = rserv.changeStatus(reimb, "APPROVE", (Integer)request.getSession().getAttribute("user"));
			
			if (success == false) {
				
				response.setStatus(401);
				response.setHeader("error", "manager");
				
			} else {
				
				response.setStatus(200);
				
			}
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	private void displayAllReimbursements(HttpServletRequest request, HttpServletResponse response) {
		
		
		List<Reimbursement> allReimbursements = rserv.getAllReimbersements();
		
		try {
			
			ObjectMapper om = new ObjectMapper();
			ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(allReimbursements);
			PrintWriter writer = response.getWriter();
			writer.write(json);
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	
	private void getUserReimbursements(HttpServletRequest request, HttpServletResponse response) {
		
		List<Reimbursement> userReimbursement = rserv.findByUserId((Integer)request.getSession().getAttribute("user"));
				
		try {
			
			ObjectMapper om = new ObjectMapper();
			ObjectWriter ow = om.writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(userReimbursement);
			PrintWriter writer = response.getWriter();
			writer.write(json);
			
		} catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}
	

}
