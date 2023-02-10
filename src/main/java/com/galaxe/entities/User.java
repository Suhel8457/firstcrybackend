package com.galaxe.entities;

import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.galaxe.enums.AccountStatus;
import com.galaxe.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table
@Entity
/** Creation Of Model Class related to User */
public class User {

	public User() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int userId;
	@Enumerated(EnumType.STRING)
	// Role Of user
	private Role role;
	// Unique code
	private String code;
	// User Name
	private String fullName;
	@Column(unique = true)
	// User MobileNumber
	private String mobileNumber;
	@Column(unique = true)
	// User EmailId
	private String emailId;
	// User Security password
	private String password;
	// User Account status
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	// On Change password generating Otp
	private long otp;
	// RelationShip of items Added To Cart
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(referencedColumnName = "userId")
	private List<Cart> cartItems;

}
