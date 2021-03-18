package com.revature.repositories;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {

	// retrieves a user from the DB by the ID.
	public User findUserbyId(int id);

	// retrieves all users from the DB.
	public List<User> getAllUsers();

	// adds a new User to the DB.
	public boolean addUser(User u);

	// used to find a user by username and password.
	public User findUserByUsernameAndPassword(String username, String password);

}
