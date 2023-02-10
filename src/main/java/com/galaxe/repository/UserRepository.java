package com.galaxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxe.entities.User;
@Repository
public interface UserRepository  extends JpaRepository<User, Integer>{
	public User findByEmailId(String email);

}
