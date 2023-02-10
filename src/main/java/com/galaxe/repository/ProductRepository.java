package com.galaxe.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.galaxe.entities.Product;
import com.galaxe.enums.ProductType;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	public List<Product> findByProductName(String productName);

	public List<Product> findByProductType(ProductType productType);

}
