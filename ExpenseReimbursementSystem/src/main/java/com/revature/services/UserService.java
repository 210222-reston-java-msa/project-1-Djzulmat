package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserDAO;
import com.revature.repositories.UserDAOImpl;

public class UserService {
	
	private static UserDAO uDao = new UserDAOImpl();
	
	public static User login(User u) {
		
		return uDao.findUserByUsernameAndPassword(u.getUsername(), u.getPassword());
		
	}
	
	public static boolean register(User u) {
		
		return uDao.addUser(u);
		
	}

}
