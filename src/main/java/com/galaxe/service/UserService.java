package com.galaxe.service;

import java.util.List;

import com.galaxe.entities.User;

/** Declaration of unimplemented Methods in ServiceLayer */
public interface UserService {
	// Saving the deatails to database
	public String saveUser(User user);

	// Getting List Of All Users
	public List<User> getUser();

	// Checking the Email is already available in database
	public boolean emailAlreadyExists(String email);

	// Generating Code for Unique User
	public String generateCode();

	// Generate Otp For ForgotPassword
	public long generateOTP(String email);

	// Getting User Details By Email
	public User getUserByEmail(String email);
}
