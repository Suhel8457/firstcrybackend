package com.galaxe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.galaxe.entities.User;
import com.galaxe.exceptions.EmailAlreadyExistsException;
import com.galaxe.exceptions.MissingInputFieldException;
import com.galaxe.exceptions.MobileNumberAlreadyExistsException;
import com.galaxe.service.EmailService;
import com.galaxe.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
/** Implementation Of User Controller */
public class UserController {

	public UserController() {
	}

	@Autowired
	UserService userService;
	@Autowired
	EmailService emailService;

	/**
	 * In this Method User Details are saved into database;If the email or mobile
	 * Number is already registered then exception message is thrown
	 */
	@PostMapping("/saveUserData")
	public ResponseEntity<String> saveUser(@RequestBody User user) {

		try {

			return new ResponseEntity<String>(userService.saveUser(user), HttpStatus.ACCEPTED);
		} catch (MissingInputFieldException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NO_CONTENT);
		} catch (EmailAlreadyExistsException e) {

			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (MobileNumberAlreadyExistsException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * In this method On registration An Welcome Message Is send to Concerned
	 * registered User
	 */
	@GetMapping("/sendmail/{receiverEmail}")
	public String sendMail(@PathVariable("receiverEmail") String email) {
		return emailService.sendWelcomeMail("suhelsuraj9679@gmail.com");
	}

	/** Getting User Datails by Email */

	@GetMapping("getuserbymail/{email}")
	public User getByEmail(@PathVariable("email") String email) {
		return userService.getUserByEmail(email);

	}

}
