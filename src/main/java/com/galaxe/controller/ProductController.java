package com.galaxe.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.galaxe.entities.Product;
import com.galaxe.exceptions.MissingInputFieldException;
import com.galaxe.exceptions.ProductsNotFoundException;
import com.galaxe.exceptions.QuantityCannotBeNegativeException;
import com.galaxe.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin("*")
public class ProductController {

	public ProductController() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	ProductService productService;

	/**
	 * In this Method Saving All Product Details ,Taking Data From Request Body;If
	 * the Fields Are Empty ,then Exception is Thrown
	 */
	@PostMapping("saveProduct/{productType}")
	public ResponseEntity<String> saveProduct(@RequestBody Product product,
			@PathVariable("productType") String productType) {
		// If the Fields Are Properly Passed then Product is saves
		try {
			return new ResponseEntity<String>(productService.saveProduct(product, productType), HttpStatus.OK);

		}
		// If product fields are Empty exception is thrown in service layer,And it is
		// handled in catch block
		catch (MissingInputFieldException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.OK);
		}

	}

	/**
	 * In this Method,the details Of All products are Obtained With Out Considering
	 * any category
	 */
	@GetMapping("getAllProducts")
	public ResponseEntity<?> getAllProducts() {

		// Able to Get All Products List If there is no exception
		try {
			return new ResponseEntity<List<Product>>(productService.getAllProducts(), HttpStatus.OK);
		}
		// If the List of products is empty exception thrown in service layer is handled
		// in this block
		catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

	/**
	 * In this Method We Can Able to get All products Matching with the
	 * ProductName;If the Name is Not Available Exception thrown in service layer is
	 * handle here
	 */
	@GetMapping("getByProductName/{productName}")
	public ResponseEntity<?> getByProductName(@PathVariable("productName") String productName) {

		// In try Block we are hitting service layer and finding products by Name
		try {
			return new ResponseEntity<List<Product>>(productService.getByProductName(productName), HttpStatus.OK);
		} // If there are any issue like No list of products,then exception thrown in
			// service layer is handled here
		catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * In this Method List Of Products is Obtained By Sorting with Price In
	 * Ascending Order.If there are List Of Products sorted Order is obtained else
	 * exception is thrown in Service layer ,we call service method in controller
	 * and exception thrown in service layer is handled in catch block of this
	 * method
	 */
	@GetMapping("sortByPrice")
	public ResponseEntity<?> getPriceSorted() {
		try {
			return new ResponseEntity<List<Product>>(productService.getPriceSorted(), HttpStatus.OK);
		} // If there are any issue like No list of products,then exception thrown in
			// service layer is handled here
		catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * In this Method List Of Products is Obtained By Sorting with Discount In
	 * Descending Order.If there are List Of Products sorted Order is obtained else
	 * exception is thrown in Service layer ,we call service method in controller
	 * and exception thrown in service layer is handled in catch block of this
	 * method
	 */
	@GetMapping("sortByDiscount")
	public ResponseEntity<?> getDiscountSorted() {
		// With the call of Service layer Method list of products Sorted in descending
		// order ,considering as discount criteria
		try {
			return new ResponseEntity<List<Product>>(productService.getDiscountSorted(), HttpStatus.OK);
		} // If there are any issue like No list of products,then exception thrown in
			// service layer is handled here
		catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * In this Method we are Able to sort the List Of Items Considering Name As
	 * criteria In Alphabetical Order
	 */
	@GetMapping("sortByName")
	public ResponseEntity<?> getNameSorted() {
		// With the call of Service layer Method list of products Sorted in Alphabetical
		// order ,considering as Name criteria
		try {
			return new ResponseEntity<List<Product>>(productService.getNameSorted(), HttpStatus.OK);
		} // If there are any issue like No list of products,then exception thrown in
			// service layer is handled here
		catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/** In this method Generates List Of Products Based On Category Type */
	@GetMapping("getBabyBathProducts")
	public ResponseEntity<?> getBabyProducts() {
		try {
			return new ResponseEntity<List<Product>>(productService.getBabyBathBroducts(), HttpStatus.OK);
		} catch (ProductsNotFoundException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

	/** In this Method Updating the Stock Based On Placing the Order */
	@PutMapping("updateInventoryQuantityProduct/{cartId}")
	public ResponseEntity<String> updateInventoryProductQuantity(@PathVariable("cartId") int cartId) {
		try {
			String response = productService.updateInventoryProduct(cartId);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (QuantityCannotBeNegativeException e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);

		}
	}

}
