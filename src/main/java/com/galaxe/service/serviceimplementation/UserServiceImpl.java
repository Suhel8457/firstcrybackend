package com.galaxe.service.serviceimplementation;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxe.entities.User;
import com.galaxe.enums.AccountStatus;
import com.galaxe.enums.Role;
import com.galaxe.exceptions.EmailAlreadyExistsException;
import com.galaxe.exceptions.InputFieldsCannotBeEmptyException;
import com.galaxe.exceptions.UsersListEmptyException;
import com.galaxe.repository.UserRepository;
import com.galaxe.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
/** Implementation of Unimplemented Methods Of User Service Layer */
public class UserServiceImpl implements UserService {
	public UserServiceImpl() {

	}

	@Autowired
	UserRepository userRepository;

	/**
	 * In this Method saving the User Details.If User Email is Already registered
	 * then Exception is thrown
	 */
	@Override
	public String saveUser(User user) {
		if (user.getEmailId() == null || user.getMobileNumber() == null || user.getFullName() == null) {
			throw new InputFieldsCannotBeEmptyException("Input Fields Cannot Be Empty");
		} else if (emailAlreadyExists(user.getEmailId())) {
			throw new EmailAlreadyExistsException("This Email is Already Registerd");
		}
		// Setting the Role Of user Based On the Actor
		user.setRole(Role.user);
		// Setting the state to active ;On Account Creation
		user.setAccountStatus(AccountStatus.active);
		user.setCode(generateCode());
		userRepository.save(user);
		return "Registration Success";
	}

	/** In this Method Finding All the List Of Users Available */
	@Override
	public List<User> getUser() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			throw new UsersListEmptyException("There are No Users Available In database");
		}
		return users;
	}

	/** In this method If Paricular user email is already present in database */
	@Override
	public boolean emailAlreadyExists(String email) {
		// Finding the User By Email ID
		User user = userRepository.findByEmailId(email);
		if(user==null) {
			return false;
		}
		else if (user.getEmailId().equals(email)) {
			return true;
		} else {
			return false;
		}

	}

	/** In this Method Generating Otp of six digits */
	@Override
	public long generateOTP(String email) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		long upperbound = 100000;
		long random = rand.nextInt((int) upperbound);
		System.out.println("Random integer value from 0 to" + (upperbound - 1) + " : " + random);
		List<User> users = userRepository.findAll();
		for (User user : users) {
			if (user.getEmailId().equals(email)) {
				user.setOtp(random);
				userRepository.save(user);
			}
		}
		return random;
	}

	// In this random Code is generated for user to make active in website
	@Override
	public String generateCode() {
		String uniqueID = UUID.randomUUID().toString();
		return uniqueID;
	}
	@Override
	public User getUserByEmail(String email) {

		return userRepository.findByEmailId(email);
	}

}
