package com.galaxe.service.serviceimplementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.galaxe.entities.Cart;
import com.galaxe.entities.Product;
import com.galaxe.enums.ProductType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.galaxe.exceptions.MissingInputFieldException;
import com.galaxe.exceptions.PriceCannotBeNegativeException;
import com.galaxe.exceptions.ProductNotFoundException;
import com.galaxe.exceptions.ProductsNotFoundException;
import com.galaxe.exceptions.QuantityCannotBeNegativeException;
import com.galaxe.repository.CartRepository;
import com.galaxe.repository.ProductRepository;
import com.galaxe.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

	public ProductServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartRepository cartRepository;

	/**
	 * In this Method We Check For Existence Of Individual Attributes If the
	 * Attributes Exists ;then Save the Product Else throw Missing Input Field
	 * Exception.If Price is Negative Throw Exception
	 */
	@Override
	public String saveProduct(Product product, String productType) {
		// Checking whether Mandatory Parameter are Empty ;If Any One is Empty then
		// Throw Exception
		if (productType.equals("clothing")) {
			product.setProductType(ProductType.clothing);
		} else if (productType.equals("babybath")) {
			product.setProductType(ProductType.babybath);
		} else if (productType.equals("footwear")) {
			product.setProductType(ProductType.footwear);
		}

		if (product.getBrand() == null || product.getImageUrl() == null || product.getMaterial() == null
				|| product.getProductColor() == null) {
			throw new MissingInputFieldException("Input Fields Are Empty");
		}
		if (product.getProductOriginalPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new PriceCannotBeNegativeException("Price Cannot be Negative");
		}
		// User Provides Discount and Original Price;then following logic calculates
		// discount Amount
		BigDecimal discountAmt = (product.getProductDiscount().multiply(product.getProductOriginalPrice()))
				.divide(BigDecimal.valueOf(100.0));
		// Calculating Final Price after Deducting Discount Amount

		product.setProductFinalPrice(product.getProductOriginalPrice().subtract(discountAmt));
		// If Final Price Negative then Throw Exception
		if (product.getProductFinalPrice().compareTo(BigDecimal.ZERO) < 0) {
			throw new PriceCannotBeNegativeException("Price Cannot Be Negative");
		}
		productRepository.save(product);
		return "You Have Successfully Added The Product";
	}

	/**
	 * In this Method,produce List of All Products In Active Status.If There Are No
	 * Products then throws ProductsNotFoundException
	 */
	@Override
	public List<Product> getAllProducts() {
		List<Product> products = productRepository.findAll();
		// Checking Whether Products List is empty;If Empty throw Exception
		if (products.size() == 0) {
			throw new ProductsNotFoundException("No Products Available");
		}
		return products;

	}

	/**
	 * In this Method We get List Of Products Matching With Corresponding
	 * ProductName .If there are No Products throw the Exception Of Not Found
	 */

	@Override
	public List<Product> getByProductName(String productName) {
		// TODO Auto-generated method stub
		List<Product> products = productRepository.findByProductName(productName);
		// Checking Whether ProductList Is Empty
		if (products.size() == 0) {
			throw new ProductsNotFoundException("No List Of Products Available with " + productName);
		}
		return products;
	}

	/**
	 * In this Method we get List of Products corresponding to productId.If the
	 * Product is Not Found throw the Exception
	 */
	@Override
	public Optional<Product> getByProductId(int productId) {
		Optional<Product> product = productRepository.findById(productId);
		// Checking Whether Product Is Available with Product Id
		if (product.isEmpty()) {
			throw new ProductNotFoundException("The Product Is Not Available");
		}
		return product;
	}

	/**
	 * In This Method Get List Of All Products in Sorted Order Using Price Criteria
	 * Low To High .If Products Are Not Available throw Exception
	 * ProductsNotFoundException
	 */
	@Override
	public List<Product> getPriceSorted() {
		List<Product> productList = new ArrayList<Product>();
		productList = getAllProducts();
		// Checking whether Product Is Empty,If Empty Throw Exception
		if (productList.size() == 0) {
			throw new ProductsNotFoundException("Product List is Empty");
		}
		// Sorting the Product Using LabmbdaExpression ;Sorting in Ascending Order
		productList.sort(
				(Product s1, Product s2) -> s1.getProductFinalPrice().compareTo(s2.getProductFinalPrice()) == -1 ? -1
						: s1.getProductFinalPrice().compareTo(s2.getProductFinalPrice()) == 1 ? 1 : 0);
		return productList;
	}

	/**
	 * In this Method Getting List Of Products Using Discount As Criteria in
	 * Descending Order.If ProductList is Empty Throw Exception Of ProductList is
	 * Empty
	 */
	@Override
	public List<Product> getDiscountSorted() {
		List<Product> productList = new ArrayList<Product>();
		productList = getAllProducts();
		// Checking the Whether Product List Is Available
		if (productList.size() == 0) {
			throw new ProductsNotFoundException("Product List is Empty");
		}
		// Sorting ProductList Using Discount Attribute in Descending Order
		productList
				.sort((Product s1, Product s2) -> s1.getProductDiscount().compareTo(s2.getProductDiscount()) == -1 ? 11
						: s1.getProductDiscount().compareTo(s2.getProductDiscount()) == 1 ? -1 : 0);
		return productList;
	}

	/**
	 * This Method Produce List Of Products In Name Sorted Order In Alphabetical
	 * Order.If ProductList is Empty then throw the Exception indicating products
	 * Not Found
	 */
	@Override
	public List<Product> getNameSorted() {
		List<Product> productList = new ArrayList<Product>();
		productList = getAllProducts();
		if (productList.size() == 0) {
			throw new ProductsNotFoundException("Product List is Empty");
		}
		productList.sort((Product s1, Product s2) -> s1.getProductName().compareTo(s2.getProductName()));
		return productList;
	}

	/**
	 * In this Method getting the Products releated to Baby Bath.If there are no
	 * items then exception is thrown
	 */
	@Override
	public List<Product> getBabyBathBroducts() {
		// Getting the List Of Products Which are related to caategory babybath
		List<Product> productList = productRepository.findByProductType(ProductType.babybath);
		if (productList.isEmpty()) {
			throw new ProductsNotFoundException(" Baby Products Are Not Available");
		}
		return productList;

	}

	/** In this Method Updating the item In ProductItem Inventory */
	@Override
	public String updateInventoryProduct(int cartId) {
		// TODO Auto-generated method stub
		Optional<Cart> item = cartRepository.findById(cartId);
		// Finding ProductId by Using CartItem Id
		// Inventory is Updated Only If selected Quantity Items are Available
		if (item.get().getQuantityInventory() > item.get().getSelectedQuantity()) {
			Optional<Product> productItemOpt = productRepository.findById(item.get().getProductId());
			productItemOpt.get().setQuantityInventory(
					productItemOpt.get().getQuantityInventory() - item.get().getSelectedQuantity());
			productRepository.save(productItemOpt.get());
		}
		// If the items count are insufficient exception is thrown
		else {
			throw new QuantityCannotBeNegativeException("Selected Quantity Exceeds the Stock");
		}

		return "Updated Inventory In Product";
	}

}
