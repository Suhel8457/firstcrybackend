package com.galaxe.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.galaxe.entities.Cart;
import com.galaxe.exceptions.CartEmptyException;
import com.galaxe.exceptions.ItemAlreadyExistsInCartException;
import com.galaxe.exceptions.ProductNotEmptyException;
import com.galaxe.exceptions.QuantityCannotBeNegativeException;
import com.galaxe.service.CartService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
/** Implementation of Cart Controller Class */
public class CartController {

	public CartController() {
	}

	@Autowired
	CartService cartService;

	/**
	 * In this Method Product Item is Saved to Concerned User Cart.If the Product
	 * Already exists Exception is thrown in service layer is handled here
	 */
	@PostMapping("saveCart/{productId}/{email}")
	public ResponseEntity<String> saveCart(@PathVariable("productId") int productid,
			@PathVariable("email") String email) {

		try {
			String message = cartService.saveCartItems(productid, email);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (ItemAlreadyExistsInCartException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (ProductNotEmptyException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);

		}

	}

	/**
	 * In this Method Getting List Of Items In To Cart Which Are In Active State.If
	 * there are No Cart Items Exception Message is Throen
	 */

	@GetMapping("getListOfItems/{email}")
	public ResponseEntity<?> getAllCartByUser(@PathVariable("email") String email) {

		try {
			List<Cart> cartItems = cartService.getcartItems(email);
			return new ResponseEntity<List<Cart>>(cartItems, HttpStatus.OK);
		} catch (CartEmptyException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		}

	}

	/** In this product Items are removed from cart */
	@DeleteMapping("/deleteItem/{itemId}")
	public ResponseEntity<String> deleteItem(@PathVariable("itemId") int itemid) {
		return new ResponseEntity<String>(cartService.deleteById(itemid), HttpStatus.OK);

	}

	/**
	 * In this method Updating the selected Quantity.If the Inventory Count of
	 * Particular product is Less then Exception is thrown
	 */
	@PutMapping("updateSelectedQuantity/{selectedQuantity}/{itemId}")
	public ResponseEntity<String> updateQuantity(@PathVariable("selectedQuantity") int selectedQuantity,
			@PathVariable("itemId") int itemId) {

		try {
			String response = cartService.updateQuantity(selectedQuantity, itemId);
			return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
		} catch (QuantityCannotBeNegativeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}

	}

	/**
	 * In this Method we update the No of Items Available In cart i.e Inventory.If
	 * the Selected Quantity is greater than stock,An exception message is thrown
	 */
	@PutMapping("updateInventoryQuantityCart/{cartId}")
	public ResponseEntity<String> updateInventory(@PathVariable("cartId") int cartId) {
		try {
			String message = cartService.updateInventory(cartId);
			return new ResponseEntity<String>(message, HttpStatus.OK);
		} catch (QuantityCannotBeNegativeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	/** In this On placing Order We get Price Details */
	@GetMapping("getPriceDetails/{email}")
	public ResponseEntity<?> getPriceDetails(@PathVariable("email") String email) {

		try {
			List<BigDecimal> priceList = cartService.getPriceDetails(email);
			return new ResponseEntity<List<BigDecimal>>(priceList, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);

		}
	}
}
