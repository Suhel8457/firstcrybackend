package com.galaxe.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.galaxe.entities.Cart;

/** Declaration Of UnImplemented Methods Of CartService */
public interface CartService {
	// Adding items to Cart to Concerned User
	public String saveCartItems(int productId, String email);

	// Getting All the Cart Items in the List related to concerned user
	public List<Cart> getcartItems(String email);

	// Deleting Item in cart By Item Id
	public String deleteById(int itemId);

	// Updating the Selected Quantity for particular Item
	public String updateQuantity(int selectedQuantity, int itemId);

	// Updaing the quantity available in product Table
	public String updateInventory(int cartId);

	// Getting the Price Details of particular User on placing order
	public List<BigDecimal> getPriceDetails(String email);

}
