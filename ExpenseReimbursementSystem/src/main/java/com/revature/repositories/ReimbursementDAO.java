package com.revature.repositories;

import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDAO {

	// retrieves reimbursement from the DB by Reimbursement ID.
	public Reimbursement getReimbursementByID(int id);

	// retrieves all reimbursements from the DB.
	public List<Reimbursement> getAllReimbursements();

	// retrieves all reimbursements from the DB by a specified User.
	public List<Reimbursement> getReimbursementsByUser(int id);

	// inserts new reimbursement to the DB.
	public int addReimbursement(Reimbursement reimb);

	// approve or deny a specific reimbursement by ID and sets resolver.
	public boolean approveOrDeny(String choice, int id, int resolverId);

}
