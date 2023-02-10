package com.galaxe.service;

/** Declaration of Unimplemented methods using Email Service */
public interface EmailService {
	// Sending mail as text As Otp
	String sendSimpleMail(String email);

	// Sending Welcome Mail As Registeration Success
	String sendWelcomeMail(String email);
}
