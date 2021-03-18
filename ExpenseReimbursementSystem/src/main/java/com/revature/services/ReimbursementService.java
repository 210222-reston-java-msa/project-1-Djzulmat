package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.repositories.ReimbursementDAO;
import com.revature.repositories.ReimbursementDAOImpl;

public class ReimbursementService {
	
	private ReimbursementDAO rDao = new ReimbursementDAOImpl();
	
	public List<Reimbursement> getAllReimbersements() {
		
		return rDao.getAllReimbursements();
		
	}
	
	public List<Reimbursement> findByUserId(int id) {
		
		return rDao.getReimbursementsByUser(id);
		
	}
	
	public int addReimbursement(Reimbursement reimb) {
		
		return rDao.addReimbursement(reimb);
		
	}
	
	public boolean changeStatus(Reimbursement reimb, String choice, int resolverId) {
		
		return rDao.approveOrDeny(choice, reimb.getId(), resolverId);
		
	}
	

}
