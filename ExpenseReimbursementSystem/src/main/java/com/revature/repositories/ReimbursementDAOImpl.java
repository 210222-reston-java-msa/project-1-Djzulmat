package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.Reimbursement;
import com.revature.util.ConnectionUtil;

public class ReimbursementDAOImpl implements ReimbursementDAO {

	private Connection conn = ConnectionUtil.getConnection();

	private static Logger log = Logger.getLogger(ReimbursementDAOImpl.class);

	public Reimbursement getReimbursementFromResultSet(ResultSet rs) throws SQLException {

		int id = rs.getInt("reimb_id");
		int author = rs.getInt("reimb_author");
		int resolver = rs.getInt("reimb_resolver");
		double amount = rs.getDouble("reimb_amount");
		String description = rs.getString("reimb_description");
		Timestamp submitted = rs.getTimestamp("reimb_submitted");
		Timestamp resolved = rs.getTimestamp("reimb_resolved");
		int statusId = rs.getInt("reimb_status_id");
		int typeId = rs.getInt("reimb_type_id");

		Reimbursement reimb = new Reimbursement(id, author, resolver, amount, description, submitted, resolved,
				statusId, typeId);

		return reimb;

	}

	public Reimbursement getReimbursementByID(int id) {

		try {

			String sql = "SELECT * FROM ers_reimbursement WHERE reimb_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				return getReimbursementFromResultSet(rs);
			}
		} catch (SQLException e) {

			log.warn("WARNING: Failed to retrieve reimbursement from the DB", e);

		}
		return null;

	}

	public List<Reimbursement> getAllReimbursements() {

		List<Reimbursement> allReimbursements = new ArrayList<Reimbursement>();

		try {

			String sql = "SELECT * FROM reimbursement ORDER BY reimb_id";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				int authorId = rs.getInt("reimb_author");
				int resolverId = rs.getInt("reimb_resolver");
				double amount = rs.getDouble("reimb_amount");
				String description = rs.getString("reimb_description");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				int statusId = rs.getInt("reimb_status_id");
				int typeId = rs.getInt("reimb_type_id");

				Reimbursement reimb = new Reimbursement(reimbId, authorId, resolverId, amount, description, submitted,
						resolved, statusId, typeId);

				allReimbursements.add(reimb);
			}
		} catch (SQLException e) {

			log.warn("WARNING: Failed to retrieve all reimbursements from the DB", e);

		}

		log.info("SUCCESS: All reimbursements have been successfully retrieved");
		return allReimbursements;

	}

	public List<Reimbursement> getReimbursementsByUser(int id) {

		List<Reimbursement> userReimbursements = new ArrayList<Reimbursement>();

		try {

			String sql = "SELECT * FROM reimbursement WHERE reimb_author = ? ORDER BY reimb_id";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int reimbId = rs.getInt("reimb_id");
				int authorId = rs.getInt("reimb_author");
				int resolverId = rs.getInt("reimb_resolver");
				double amount = rs.getDouble("reimb_amount");
				String description = rs.getString("reimb_description");
				Timestamp submitted = rs.getTimestamp("reimb_submitted");
				Timestamp resolved = rs.getTimestamp("reimb_resolved");
				int statusId = rs.getInt("reimb_status_id");
				int typeId = rs.getInt("reimb_type_id");

				Reimbursement reimb = new Reimbursement(reimbId, authorId, resolverId, amount, description, submitted,
						resolved, statusId, typeId);

				userReimbursements.add(reimb);
			}

		} catch (SQLException e) {

			log.warn("WARNING: Failed to retrieve reimbursement from DB", e);

		}

		log.info("SUCCESS: Reimbursement has been successfully retrieved");
		return userReimbursements;
	}

	public int addReimbursement(Reimbursement reimb) {

		try {

			String sql = "INSERT INTO reimbursement (reimb_id, reimb_type_id, reimb_author, reimb_resolver, reimb_amount, reimb_description, reimb_submitted, reimb_resolved, reimb_status_id) VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			//stmt.setInt(1, reimb.getId());
			stmt.setInt(2, reimb.getAuthorId());
			Integer resolverId = reimb.getResolverId();
			if (resolverId != null) {
			  stmt.setInt(3, resolverId);
			} else { 
			  stmt.setNull(3, java.sql.Types.INTEGER);
			}
			stmt.setDouble(4, reimb.getAmount());
			stmt.setString(5, reimb.getDescription());
			stmt.setTimestamp(6, reimb.getSubmitted());
			stmt.setTimestamp(7, reimb.getResolved());
			stmt.setInt(8, reimb.getStatus());
			stmt.setInt(1, reimb.getType());

			stmt.execute();

			log.info("SUCCESS: The reimbursement claim has successfully been added to the DB");

		} catch (SQLException e) {

			log.warn("WARNING: Failed to add the reimbursement claim to the DB", e);
			
		}

		return 0;

	}

	public boolean approveOrDeny(String choice, int id, int resolverId) {

		try {
			
			String sql = "UPDATE reimbursement SET reimb_status_id = ?, reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ? AND reimb_author != " + resolverId;
			PreparedStatement stmt = conn.prepareStatement(sql);
					
			stmt.setInt(4, id);

			if (choice.equals("APPROVE")) {
				
				stmt.setInt(1, 1);
				stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				stmt.setInt(3, resolverId);
				
			} else if (choice.equals("DENY")) {
				
				stmt.setInt(1, 2);
				stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				stmt.setInt(3, resolverId);
				
			} else {
				
				stmt.setInt(1, 0);
				
			}
			
			stmt.execute();
			return true;
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}
		
		return false;

	}

}
