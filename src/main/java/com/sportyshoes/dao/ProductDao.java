package com.sportyshoes.dao;

import java.util.List;

import com.sportyshoes.models.Product;

public interface ProductDao {
	//Add Product
	public int addProduct(Product product);
	
	//Retrieve a product based on productId
	public Product getProduct(int productId);
	
	//Retrieve all product details
	public List<Product> getAllProducts();
	
	//Retrieve Product details based on Product Name
	public List<Product> getProductOnName(String productName);
	
	//Update a product based on productId
	public int updateProduct(int productId,Product product);
	
	//Delete a product
	public int deleteProduct(Product product);
	
}
