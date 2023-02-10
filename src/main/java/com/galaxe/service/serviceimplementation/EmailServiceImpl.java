package com.galaxe.service.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.galaxe.exceptions.EmailNotFoundException;
import com.galaxe.exceptions.EmailNotSentException;
import com.galaxe.service.EmailService;
import com.galaxe.service.UserService;

/** Implementation of EmailServiceImpl unimplemented methods in ServiceLayer */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserService userService;

	@Value("${spring.mail.username}")
	private String sender;

	/**
	 * In this Method Mail is Sent To user when user wants to reset password ; Only
	 * if Email is Registered to Website.
	 */
	@Override
	public String sendSimpleMail(String email) {
		if (userService.emailAlreadyExists(email)) {
			try {
				SimpleMailMessage mailMessage = new SimpleMailMessage();
				// From Address Mail Id
				mailMessage.setFrom(sender);
				// To Address Mail Id
				mailMessage.setTo(email);
				// Body Of Message
				mailMessage.setText("The otp for reset password is\n" + userService.generateOTP(email));
				// Subject Of Mail
				mailMessage.setSubject("OTP for forget Password");
				javaMailSender.send(mailMessage);
				return "Mail Sent Successfully";
			} catch (EmailNotSentException e) {
				System.out.println(e);
				return "Error while Sending Mail";
			}
		} else {
			throw new EmailNotFoundException("Email not exists");
		}
	}

	/**
	 * In this method After registration A welcome Message is Sent to user ;If Email
	 * is not registered with our website earlier
	 */
	@Override
	public String sendWelcomeMail(String email) {
		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			// Setting the sender Mail Address
			mailMessage.setFrom(sender);
			mailMessage.setTo(email);
			mailMessage.setText("Thanks for registering to First Cry Website");
			mailMessage.setSubject("Registration Success");
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully";
		} catch (EmailNotSentException e) {
			System.out.println(e);
			return "Error while Sending Mail";
		}
	}
}
