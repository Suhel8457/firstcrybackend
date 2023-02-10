package com.galaxe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.galaxe.entities.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

}
