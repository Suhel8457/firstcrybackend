package com.galaxe.service.serviceimplementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.galaxe.entities.Cart;
import com.galaxe.entities.Product;
import com.galaxe.entities.User;
import com.galaxe.enums.CartStatus;
import com.galaxe.exceptions.CartEmptyException;
import com.galaxe.exceptions.ItemAlreadyExistsInCartException;
import com.galaxe.exceptions.ProductNotEmptyException;
import com.galaxe.exceptions.ProductNotFoundException;
import com.galaxe.exceptions.QuantityCannotBeNegativeException;
import com.galaxe.repository.CartRepository;
import com.galaxe.repository.ProductRepository;
import com.galaxe.repository.UserRepository;
import com.galaxe.service.CartService;

import lombok.extern.slf4j.Slf4j;

/** Implementation of Unimplemented Methods in CartServiceImpl */
@Service
@Slf4j
public class CartServiceImpl implements CartService {

	public CartServiceImpl() {

	}

	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	UserRepository userRepository;

	/**
	 * In this Product Items are Added to Cart.If It is Not Present in Cart else
	 * Exception is thrown in service layer.While adding product details ,Empty
	 * Fields are not Allowed.
	 */
	@Override
	public String saveCartItems(int productId, String email) {
		// TODO Auto-generated method stub
		// Checking the ProductId is Present In Repository
		Optional<Product> item = productRepository.findById(productId);
		if (item.isEmpty()) {
			throw new ProductNotFoundException("The Item Your trying To Add Is Not Available In Cart");
		}
		Product product = item.get();
		// Fields are Empty throw Exception
		if (product.getProductName() == null || product.getImageUrl() == null) {
			throw new ProductNotEmptyException("Product Empty Can't Be added To cart");
		} else {

			Cart cart = new Cart();
			List<Cart> cartItems = new ArrayList<>();
			// Setting Values Of product To Cart Item
			cart.setProductId(productId);
			cart.setImageUrl(product.getImageUrl());
			cart.setProductColor(product.getProductColor());
			cart.setDescription(product.getDescription());
			cart.setProductDiscount(product.getProductDiscount());
			cart.setProductFinalPrice(product.getProductFinalPrice());
			cart.setProductName(product.getProductName());
			cart.setProductOriginalPrice(product.getProductOriginalPrice());
			cart.setProductSize(product.getProductSize());
			cart.setBrand(product.getBrand());
			cart.setCartStatus(CartStatus.active);
			cart.setMaterial(product.getMaterial());
			cart.setOccasion(product.getOccasion());
			cart.setQuantityInventory(product.getQuantityInventory());
			cart.setSpecType(product.getSpecType());

			User user = userRepository.findByEmailId(email);
			// Adding The Cart Items to Particular user Logged in
			cartItems.add(cart);
			// Checking,if the Item trying to add is present in cart.If it is available then
			// throw exception
			if (user.getCartItems().size() != 0) {
				for (Cart cartItem : user.getCartItems()) {
					if (cartItem.getProductId() == product.getProductId()&& cartItem.getCartStatus().equals(CartStatus.active)) {
						throw new ItemAlreadyExistsInCartException("Item Already Exists In cart");
					}
				}
				// Adding the cart Items to user
				for (Cart cartItem : user.getCartItems()) {
					cartItems.add(cartItem);
				}

			}
			user.setCartItems(cartItems);
			userRepository.save(user);
			return "Cart Added Successfully";
		}

	}

	/**
	 * In this method ,On passing user Email-all the cart items related to
	 * particular user which are in active state are produced.An Exception is thrown
	 * if there no active items in cart
	 */
	@Override
	public List<Cart> getcartItems(String email) {
		// TODO Auto-generated method stub
		// Finding the user By Email
		User user = userRepository.findByEmailId(email);
		List<Cart> cartList = new ArrayList<>();
		// If there are No Cart items with Concerned user then Exception is thrown
		if (user.getCartItems().size() == 0) {
			throw new CartEmptyException("There are No Cart Items");
		}
		// Adding to List Only the Cart Items which are In active State
		for (Cart cartItem : user.getCartItems()) {
			if (cartItem.getCartStatus() == CartStatus.active) {
				cartList.add(cartItem);
			}
		}
		// If there are no cart Items then throw an exception
		if (cartList.isEmpty()) {
			throw new CartEmptyException("There are No Active Cart Items");
		}
		return cartList;
	}

	/**
	 * In this method removal of Items From Cart i.e sending it to inactive state.
	 */

	@Override
	public String deleteById(int itemId) {
		// Find the Item by Id in cart
		Optional<Cart> item = cartRepository.findById(itemId);
		// making the status to Inactive
		item.get().setCartStatus(CartStatus.inactive);
		// On changing the status,Item is updated
		cartRepository.save(item.get());
		return "Deleted Successfully";
	}

	/**
	 * In this method based On product Id quantity gets updated ,for Selected user
	 * Logged In
	 */
	@Override
	public String updateQuantity(int selectedQuantity, int itemId) {
		// Find the item in Cart by item Id
		Optional<Cart> item = cartRepository.findById(itemId);
		// checking If There is Sufficient Stock in Inventory compared to
		// selectedAuantity by user.If not available exception is thrown
		if (item.get().getQuantityInventory() - selectedQuantity > 0) {
			item.get().setSelectedQuantity(selectedQuantity);
			cartRepository.save(item.get());

		} else {
			throw new QuantityCannotBeNegativeException("Selected Quantity Exceeds the Stock");
		}

		return "Selected Quantity Updated" + selectedQuantity;

	}

	/** In this Method Updating Inventory Quantity based on Cart Item Id */
	@Override
	public String updateInventory(int cartId) {
		// Finding productItem In Cart by cartItem Id
		Optional<Cart> cartItem = cartRepository.findById(cartId);
		Cart productItemOpt = cartItem.get();
		// Check Stock Available compared to Quantity selected By user
		if (productItemOpt.getQuantityInventory() - productItemOpt.getSelectedQuantity() > 0) {
			productItemOpt
					.setQuantityInventory(productItemOpt.getQuantityInventory() - productItemOpt.getSelectedQuantity());

		} // If limit Exceeds Exception is thrown
		else {
			throw new QuantityCannotBeNegativeException("Selected Quantity Exceeds the Stock");
		}
		// On Updation saving the cartitem
		cartRepository.save(productItemOpt);

		return "Inventory Got Updated";
	}

	/** In this Method getting the price Details Of Cart items present in Cart */
	@Override
	public List<BigDecimal> getPriceDetails(String email) {
		// TODO Auto-generated method stub
		List<BigDecimal> priceList = new ArrayList<>();
		// Initializing the price displayed
		BigDecimal originalPrice = BigDecimal.ZERO, OfferPrice = BigDecimal.ZERO;
		// getting the details of user by email
		User user = userRepository.findByEmailId(email);
		// On Getting user Details ;Calculating offerPrice,originalPrice for selected
		// item in cart
		if (user.getCartItems().size() != 0) {
			for (Cart item : user.getCartItems()) {
				if (item.getCartStatus().equals(CartStatus.active)) {
					// All the items in active state ;calculation of original Price and Offer Price
					// based on selected Quantity
					originalPrice = originalPrice.add(
							item.getProductOriginalPrice().multiply(BigDecimal.valueOf(item.getSelectedQuantity())));
					OfferPrice = OfferPrice
							.add(item.getProductFinalPrice().multiply(BigDecimal.valueOf(item.getSelectedQuantity())));

				}

			}
			// Adding Offer price,originalPrice,Discount to list
			priceList.add(OfferPrice);
			priceList.add(originalPrice);
			priceList.add(originalPrice.subtract(OfferPrice));

		}
		return priceList;
	}

}
