package com.sportyshoes.models;

public class Product {
	private int productId;
	private String productName;
	private int msrp;
	private int quantityInStock;
	private String productVendor;
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getMSRP() {
		return msrp;
	}
	public void setMSRP(int mSRP) {
		msrp = mSRP;
	}
	public int getQuantityInStock() {
		return quantityInStock;
	}
	public void setQuantityInStock(int quantityInStock) {
		this.quantityInStock = quantityInStock;
	}
	public String getProductVendor() {
		return productVendor;
	}
	public void setProductVendor(String productVendor) {
		this.productVendor = productVendor;
	}
	public Product(int productId, String productName, int mSRP, int quantityInStock, String productVendor) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.msrp = mSRP;
		this.quantityInStock = quantityInStock;
		this.productVendor = productVendor;
	}
	
	public Product() {
		super();
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productName=" + productName + ", MSRP=" + msrp
				+ ", quantityInStock=" + quantityInStock + ", productVendor=" + productVendor + "]";
	}
	
	
}
