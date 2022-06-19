package com.sportyshoes.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.models.Order;
import com.sportyshoes.models.Product;
import com.sportyshoes.services.OrderService;
import com.sportyshoes.services.ProductService;

@RestController
@RequestMapping("/api/product")
public class ProductController {

	@Autowired
	private ProductService ps;
	
	@Autowired
	private OrderService os;
	
	private static final Logger log = LoggerFactory.getLogger(ProductController.class);
	@GetMapping("/all")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = ps.listAllInventoryProducts();
		log.info("Result set of all products:{}",products);
		if (products.isEmpty() || products == null) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(products, HttpStatus.OK);
		}
	}

	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchProduct(@RequestParam(required = true) String name) {
		List<Product> products = ps.searchNamedProduct(name);
		log.info("Result List of all products with the name : {}\n{}", name, products);
		if (products == null ||products.isEmpty()) {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(products, HttpStatus.OK);
		}
	}

	@GetMapping("/{productId}")
	public Product getProduct(@PathVariable("productId") int productId) {
		return ps.getProductDetails(productId);
	}

	@PostMapping("/add")
	public String addProduct(@RequestBody Product product) {
		return ps.addProduct(product);
	}

	@PatchMapping("/{productId}/update/name")
	public String updateProductName(@PathVariable("productId") int pid,
			@RequestParam(required = true) String name) {
		return ps.updateProductName(pid, name);
	}
	
	@PatchMapping("/{productId}/update/msrp")
	public String updateProductMSRP(@PathVariable("productId") int pid,
			@RequestParam(required = true) int msrp) {
		return ps.updateProductMSRP(pid, msrp);
	}
	
	@PatchMapping("/{productId}/update/quantityInStock")
	public String updateProductQuantityInStock(@PathVariable("productId") int pid,
			@RequestParam(required = true) int q) {
		return ps.updateProductQuantityInStock(pid, q);
	}
	
	@PatchMapping("/{productId}/update/productVendor")
	public String updateProductVendor(@PathVariable("productId") int pid,
			@RequestParam(required = true) String vendor) {
		return ps.updateProductVendor(pid, vendor);
	}
	
	@DeleteMapping("/{productId}/delete")
	public String deleteProduct(@PathVariable("productId") int pid) {
		return ps.deleteProduct(pid);
	}
	
	@GetMapping("/{productId}/order/all")
	public List<Order> getProductOrders(@PathVariable("productId") int pid){
		return os.getOrderWithProduct(pid);
	}
	
}
