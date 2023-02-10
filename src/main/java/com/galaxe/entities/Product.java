package com.galaxe.entities;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.galaxe.enums.ProductType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Table
@Entity
@Builder
@AllArgsConstructor
/** Entity class of Product item */
public class Product {

	public Product() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	// Enum Type Describing the productType
	@Enumerated(EnumType.STRING)
	private ProductType productType;
	// ProductName
	private String productName;
	// Product Description
	private String description;
	// Original Price Of product
	private BigDecimal productOriginalPrice;
	// Discount Amount
	private BigDecimal productDiscount;
	// Offer Price
	private BigDecimal productFinalPrice;
	// Product Color
	private String productColor;
	// Size of the Product in Dimensions
	private String productSize;
	// URL Of product Image
	private String imageUrl;
	// No Of Products
	private int quantityInventory;
	// Tells about product Type like Top wear,Footwear
	private String specType;
	// Brand Name Of Product
	private String brand;
	// Material Used Primarily
	private String material;
	// Tells About the time Of usage
	private String occasion;

}
