package com.galaxe;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.galaxe.entities.Cart;
import com.galaxe.entities.Product;
import com.galaxe.entities.User;
import com.galaxe.enums.AccountStatus;
import com.galaxe.enums.ProductType;
import com.galaxe.enums.Role;
import com.galaxe.exceptions.EmailAlreadyExistsException;
import com.galaxe.exceptions.InputFieldsCannotBeEmptyException;
import com.galaxe.exceptions.MissingInputFieldException;
import com.galaxe.exceptions.PriceCannotBeNegativeException;
import com.galaxe.exceptions.ProductNotFoundException;
import com.galaxe.exceptions.ProductsNotFoundException;
import com.galaxe.exceptions.UsersListEmptyException;
import com.galaxe.repository.ProductRepository;
import com.galaxe.repository.UserRepository;
import com.galaxe.service.serviceimplementation.ProductServiceImpl;
import com.galaxe.service.serviceimplementation.UserServiceImpl;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class FirsrtCryApplicationTests {

	@InjectMocks
	private UserServiceImpl userService;
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private ProductServiceImpl productService;
	@Mock
	private ProductRepository productRepository;

	/** In this Method getting All Users Testing ---Positive Test */
	@Test
	@Order(1)
	public void getUsersTest() {
		List<Cart> items = new ArrayList<>();
		List<User> users = new ArrayList();

		User userOne = new User(1, Role.admin, userService.generateCode(), "raju", "9676313326", "raju123@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, items);
		User userTwo = new User(2, Role.user, userService.generateCode(), "raghu", "9676313326", "ragu12345@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, items);
		users.add(userOne);
		users.add(userTwo);
		when(userRepository.findAll()).thenReturn(users);
		List<User> users1 = userService.getUser();
		assertEquals(2, users1.size());
	}

	/** Getting The User Details by Mail ---Positive Test */
	@Test
	@Order(2)
	public void getUserByEmailTest() {
		List<Cart> items = new ArrayList<>();
		User userOne = new User(1, Role.admin, userService.generateCode(), "raju", "9676313326", "raju123@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, items);
		when(userRepository.findByEmailId("raju123@gmail.com")).thenReturn(userOne);
		assertEquals(userOne, userService.getUserByEmail("raju123@gmail.com"));
	}

	@Test
	@Order(3)
	public void saveUserTest() {
		List<Cart> items = new ArrayList<>();
		User userOne = new User(1, Role.admin, userService.generateCode(), "raju", "9676313326", "raju123@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, items);
		when(userRepository.save(userOne)).thenReturn(userOne);
		assertEquals("Registration Success", userService.saveUser(userOne));
	}

	/** Trying to Save Details When Fields are Empty --Negative Test */
	@Order(4)
	@Test
	public void saveUserTesEmptyFieldstNegative() {
		List<Cart> items = new ArrayList<>();
		User userOne = new User();
		when(userRepository.save(userOne)).thenReturn(userOne);
		assertThrows(InputFieldsCannotBeEmptyException.class, () -> userService.saveUser(userOne));
	}

	@Order(5)
	@Test
	public void UserEmailAlreadyExists() {
		User userOne = new User(1, Role.admin, userService.generateCode(), "raju", "9676313326", "raju123@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, null);
		when(userRepository.findByEmailId("raju123@gmail.com")).thenReturn(userOne);
		assertEquals(true, userService.emailAlreadyExists("raju123@gmail.com"));
//		assertThrows(EmailAlreadyExistsException.class, ()->userService.emailAlreadyExists("raju123@gmail.com")
//		);
//		assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(userTwo));
//		assertThatThrownBy(() -> userService.saveUser(userTwo)).isInstanceOf(EmailAlreadyExistsException.class)
//				.hasMessage("Already Exists");
	}

	/** Saving the Produt Details Test Method */
	@Test
	@Order(6)
	public void saveProductItem() {
		Product productItem = new Product(1, ProductType.clothing, "DNMX Shirt", "comfortable",
				BigDecimal.valueOf(500.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y",
				"imageurl", 30, "Shirt", "DNMX", "cotton", "casual");
		when(productRepository.save(productItem)).thenReturn(productItem);
		assertEquals("You Have Successfully Added The Product", productService.saveProduct(productItem, "clothing"));

	}

	@Test
	@Order(7)
	/** Trying to get User Details If the List is empty */
	public void getUserEmptyList() {
		when(userRepository.findAll()).thenThrow(UsersListEmptyException.class);
		assertThrows(UsersListEmptyException.class, () -> userService.getUser());

	}

	@Test
	@Order(8)
	public void getAllProductsPositive() {
		List<Product> products = new ArrayList();
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(0)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = new Product(1, ProductType.clothing, "DNMX Shirt", "comfortable",
				BigDecimal.valueOf(500.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y",
				"imageurl", 30, "Shirt", "DNMX", "cotton", "casual");
		products.add(productItem);
		products.add(productOne);
		when(productRepository.findAll()).thenReturn(products);
		assertEquals(2, productService.getAllProducts().size());
	}

	/** Saving product with Empty Fields---Negative Test */
	@Test
	@Order(9)
	public void saveProductEmptyfieldNegative() {
		Product product = new Product();
		when(productRepository.save(product)).thenReturn(product);
		assertThrows(MissingInputFieldException.class, () -> productService.saveProduct(product, "clothing"));
	}

	/** Saving Product with Negative Price ---Negative Test */
	@Test
	@Order(10)
	public void saveProductPriceNegative() {
		Product productItem = new Product(1, ProductType.clothing, "DNMX Shirt", "comfortable",
				BigDecimal.valueOf(-500.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y",
				"imageurl", 30, "Shirt", "DNMX", "cotton", "casual");
		when(productRepository.save(productItem)).thenReturn(productItem);
		assertThrows(PriceCannotBeNegativeException.class, () -> productService.saveProduct(productItem, "clothing"));

	}

	@Test
	@Order(11)
	public void getAllProductsNegative() {
		List<Product> products = new ArrayList<>();
		assertThrows(ProductsNotFoundException.class, () -> productService.getAllProducts());
	}

	@Test
	@Order(12)
	public void getProductByNamePositive() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(0)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = new Product(1, ProductType.clothing, "Hoodie", "comfortable", BigDecimal.valueOf(500.00),
				BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y", "imageurl", 30, "Shirt", "DNMX",
				"cotton", "casual");
		List<Product> products = new ArrayList<>();
		products.add(productItem);
		products.add(productOne);
		when(productRepository.findByProductName("Hoodie")).thenReturn(products);
		assertEquals(2, productService.getByProductName("Hoodie").size());

	}

	@Test
	@Order(13)
	public void getProductByNameNegative() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(0)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = new Product(2, ProductType.clothing, "Hoodie", "comfortable", BigDecimal.valueOf(500.00),
				BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y", "imageurl", 30, "Shirt", "DNMX",
				"cotton", "casual");
		List<Product> products = new ArrayList<>();
		products.add(productItem);
		products.add(productOne);
		when(productRepository.findByProductName("Hoodie")).thenReturn(products);
		assertThrows(ProductsNotFoundException.class, () -> productService.getByProductName("Shirt"));

	}

	@Test
	@Order(14)
	public void getByProductIdPositive() {
		Optional<Product> productItem = Optional.of(new Product(1, ProductType.clothing, "Hoodie", "comfortable",
				BigDecimal.valueOf(500.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y",
				"imageurl", 30, "Shirt", "DNMX", "cotton", "casual"));
		when(productRepository.findById(1)).thenReturn(productItem);
		assertEquals(productItem, productService.getByProductId(1));

	}

	@Test
	@Order(15)
	public void getByProductIdNegative() {
		Optional<Product> productItem = Optional.of(new Product(1, ProductType.clothing, "Hoodie", "comfortable",
				BigDecimal.valueOf(500.00), BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00), "blue", "4-5 Y",
				"imageurl", 30, "Shirt", "DNMX", "cotton", "casual"));
		when(productRepository.findById(1)).thenReturn(productItem);
		assertThrows(ProductNotFoundException.class, () -> productService.getByProductId(2));

	}

	@Test
	@Order(16)
	public void getProductsPriceSortedPositive() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = new Product(2, ProductType.clothing, "Hoodie", "comfortable", BigDecimal.valueOf(500.00),
				BigDecimal.valueOf(50.00), BigDecimal.valueOf(250.00), "blue", "4-5 Y", "imageurl", 30, "Shirt", "DNMX",
				"cotton", "casual");
		List<Product> products = new ArrayList();
		products.add(productItem);
		products.add(productOne);
		when(productRepository.findAll()).thenReturn(products);
		for (int i = 0; i < products.size(); i++) {
			assertEquals(products.get(i).getProductFinalPrice(),
					productService.getPriceSorted().get(i).getProductFinalPrice());
		}

	}

	@Order(17)
	@Test
	public void saveUserEmailAlreadyExists() {
		User userOne = new User(1, Role.admin, userService.generateCode(), "raju", "9676313326", "raju123@gmail.com",
				"Suhel@123", AccountStatus.active, 123456, null);
		// when(userRepository.save(userOne)).thenReturn(userOne);
		when(userRepository.findByEmailId("raju123@gmail.com")).thenReturn(userOne);
//		assertEquals("Registration Success", userService.saveUser(userOne));

		assertThrows(EmailAlreadyExistsException.class, () -> userService.saveUser(userOne));
	}

	@Order(18)
	@Test
	public void getProductsDiscountSorted() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(70))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Top Wear")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		List<Product> productsCurrent = new ArrayList();
		productsCurrent.add(productItem);
		productsCurrent.add(productOne);
		when(productRepository.findAll()).thenReturn(productsCurrent);
		for (int i = 0; i < productsCurrent.size(); i++) {

			assertEquals(productsCurrent.get(i), productService.getDiscountSorted().get(i));
		}

	}

	@Order(19)
	@Test
	public void getProductsSortedByName() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Hoodie")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		Product productItem = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(70))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Top Wear")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		List<Product> productsCurrent = new ArrayList();
		productsCurrent.add(productOne);
		productsCurrent.add(productItem);

		when(productRepository.findAll()).thenReturn(productsCurrent);
		for (int i = 0; i <productsCurrent.size(); i++) {
			assertEquals(productsCurrent.get(i).getProductName(),
					productService.getNameSorted().get(i).getProductName());
		}

	}

	@Order(20)
	@Test
	public void getBabyCreamProductsPositive() {
		Product productOne = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(50))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Himalaya Lotion")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.babybath)
				.quantityInventory(30).specType("BeforeBath").build();
		Product productItem = Product.builder().productId(1).productColor("green").brand("peterengland")
				.imageUrl("https://cdn.fcglcdn.com/brainbees/images/products/438x531/12699321a.webp").material("cotton")
				.description("Comfortable and Flexible").occasion("casualwear").productDiscount(BigDecimal.valueOf(70))
				.productFinalPrice(BigDecimal.valueOf(500)).productName("Top Wear")
				.productOriginalPrice(BigDecimal.valueOf(1000)).productSize("12-13 Y").productType(ProductType.clothing)
				.quantityInventory(30).specType("TopWear").build();
		List<Product> products = new ArrayList<>();

		products.add(productOne);
		when(productRepository.findByProductType(ProductType.babybath)).thenReturn(products);
		assertEquals(productOne, productService.getBabyBathBroducts().get(0));
	}

	@Order(21)
	@Test
	public void getBabyProductsNegative() {
		List<Product> products = new ArrayList<>();
		when(productRepository.findByProductType(ProductType.babybath)).thenReturn(products);
		assertThrows(ProductsNotFoundException.class, () -> productService.getBabyBathBroducts());
	}
	
}
