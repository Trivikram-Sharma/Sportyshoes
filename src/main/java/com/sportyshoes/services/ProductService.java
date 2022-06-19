package com.sportyshoes.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.models.Product;
import com.sportyshoes.repositories.ProductRepository;
@Service
public class ProductService {
	
	@Autowired
	private ProductRepository pr;
	
	public ProductService() {
		super();
	}
	
	public List<Product> listAllInventoryProducts(){
		return pr.getAllProducts();
	}
	
	public List<Product> searchNamedProduct(String name) {
		return pr.getProductOnName(name); 
	}
	
	public Product getProductDetails(int productId) {
		return pr.getProduct(productId);
	}
	
	public String addProduct(Product p) {
		String res = "";
		Product pe= pr.getProduct(p.getProductId());
		if(pe!=null) {
			res = "Product already Exists! Please Check the details and try again!";
		}
		else {
			System.out.println(p);
			int status = pr.addProduct(p);
			res = (status==1)? "Product Added Successfully!":"Product Addition Unsuccessful! Please check details and try again!";
		}
		return res;
	}
	public String updateProductName(int productId,String productName) {
		String res = "";
		Product p = pr.getProduct(productId);
		if(p==null) {
			res = "Product Does not exist with ID:"+productId+"! Please check the details and try again!";
		}
		else {
			p.setProductName(productName);
			int status = pr.updateProduct(productId, p);
			res = (status==1)? "Product Updated Successfully!":"Product update Unsuccessful! Please check the details and try again!";
		}
		return res;
	}
	
	public String updateProductMSRP(int productId, int MSRP) {
		String res="";
		Product p = pr.getProduct(productId);
		if(p==null) {
			res = "Product Does not exist with ID:"+productId+"! Please check the details and try again!";
		}
		else {
			p.setMSRP(MSRP);
			int status = pr.updateProduct(productId, p);
			res = (status==1)? "Product Updated Successfully!":"Product update Unsuccessful! Please check the details and try again!";
		}
		return res;
	}
	
	public String updateProductQuantityInStock(int productId, int q) {
		String res="";
		Product p = pr.getProduct(productId);
		if(p==null) {
			res = "Product Does not exist with ID:"+productId+"! Please check the details and try again!";
		}
		else {
			p.setQuantityInStock(q);
			int status = pr.updateProduct(productId, p);
			res = (status==1)? "Product Updated Successfully!":"Product update Unsuccessful! Please check the details and try again!";
		}
		return res;
	}
	
	public String updateProductVendor(int productId, String vendor) {
		String res="";
		Product p = pr.getProduct(productId);
		if(p==null) {
			res = "Product Does not exist with ID:"+productId+"! Please check the details and try again!";
		}
		else {
			p.setProductVendor(vendor);
			int status = pr.updateProduct(productId, p);
			res = (status==1)? "Product Updated Successfully!":"Product update Unsuccessful! Please check the details and try again!";
		}
		return res;
	}
	
	public String deleteProduct(int productId) {
		String res = "";
		int status = pr.deleteProduct(pr.getProduct(productId));
		res = (status==1)?"product Deleted Successfully!":"Product deletion Unsuccessful!Please check the details and try again!";
		return res;
	}
	
	
}
