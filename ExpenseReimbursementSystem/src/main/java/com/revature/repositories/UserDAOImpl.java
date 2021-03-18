package com.revature.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.models.User;
import com.revature.util.ConnectionUtil;

public class UserDAOImpl implements UserDAO {

	private Connection conn = ConnectionUtil.getConnection();

	private static Logger log = Logger.getLogger(UserDAOImpl.class);

	public User getUserFromResultSet(ResultSet rs) throws SQLException {

		int id = rs.getInt("user_id");
		String firstName = rs.getString("first_name");
		String lastName = rs.getString("last_name");
		String username = rs.getString("username");
		String password = rs.getString("pass_word");
		String email = rs.getString("email");
		int roleId = rs.getInt("role_id");

		User u = new User(id, firstName, lastName, username, password, email, roleId);
		return u;
	}

	public User findUserbyId(int id) {

		try {

			String sql = "SELECT * FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {

				log.info("SUCCESS: User " + rs.getString("first_name") + " has been retrieved from DB");
				return getUserFromResultSet(rs);
			}
		} catch (SQLException e) {
			log.warn("WARNING: Failed to retrive the user from DB");
			e.printStackTrace();
		}
		return null;

	}

	public List<User> getAllUsers() {

		List<User> allUsers = new ArrayList<User>();

		try {

			String sql = "SELECT * FROM users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("user_id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String username = rs.getString("username");
				String password = rs.getString("pass_word");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id");

				User u = new User(id, firstName, lastName, username, password, email, roleId);
				allUsers.add(u);
			}
		} catch (SQLException e) {

			log.warn("WARNING: Failed to retrieve all users from DB", e);
			return null;
		}

		return allUsers;

	}

	public boolean addUser(User u) {

		try {

			String sql = "INSERT INTO users (first_name, last_name, username, pass_word, email, role_id)"
					+ " VALUES (?,?,?,?,?,?)";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setString(1, u.getFirstName());
			stmt.setString(2, u.getLastName());
			stmt.setString(3, u.getUsername());
			stmt.setString(4, u.getPassword());
			stmt.setString(5, u.getEmail());
			stmt.setInt(6, u.getRole());

			
			if (!stmt.execute()) {
				return false;
			}

		} catch (SQLException e) {

			log.warn("WARNING: Failed to add user.", e);
			
		}
		
		log.info("SUCCESS: User " + u.getFirstName() + " has been successfully added.");
		return true;

	}
	

	public User findUserByUsernameAndPassword(String username, String password) {

		try {
			
			String sql = "SELECT * FROM users WHERE username = ? AND pass_word = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("user_id"));
				u.setFirstName(rs.getString("first_name"));
				u.setLastName(rs.getString("last_name"));
				u.setUsername(username);
				u.setPassword(password);
				u.setEmail(rs.getString("email"));
				u.setRole(rs.getInt("role_id"));

				log.info("SUCCESS: User by username = " + username + " and password = " + password + " has been found");
				return u;
			}
			
		} catch (SQLException e) {
			
			log.warn("WARNING: Failed to retrieve user from DB", e);
			
		}
		
		return null;
	}

}
