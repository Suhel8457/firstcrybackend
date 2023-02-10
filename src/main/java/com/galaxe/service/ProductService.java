package com.galaxe.service;

import java.util.List;
import java.util.Optional;
import com.galaxe.entities.Product;

/** Declaration Of Unimplemented Methods In productService */
public interface ProductService {
	// Saving the Details of product By product Type
	public String saveProduct(Product product, String productType);

	// Getting the All Categories Of Products
	public List<Product> getAllProducts();

	// Getting the Products By Product Name
	public List<Product> getByProductName(String productName);

	// Getting the Peroduct buy passing product Id
	public Optional<Product> getByProductId(int productId);

	// Getting the list of products in sorted Order by price as Criteria in Low To
	// High Order
	public List<Product> getPriceSorted();

	// Getting the list of products in sorted Order by Discount as Criteria in
	// High To Low
	public List<Product> getDiscountSorted();

	// Getting the list of Products by Aplphabetical Order
	public List<Product> getNameSorted();

	// Getting the Products related to babybath category
	public List<Product> getBabyBathBroducts();

	// On placing order Quantity Of stock item gets updated
	public String updateInventoryProduct(int cartId);

}
