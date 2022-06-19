package com.sportyshoes.models;

import java.util.Date;

public class Order {
	private int orderId;
	private User orderUser;
	private Product orderProduct;
	private Date orderDate;
	public Order(int orderId, User orderUser, Product orderProduct) {
		super();
		this.orderId = orderId;
		this.orderUser = orderUser;
		this.orderProduct = orderProduct;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Order() {
		super();
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public User getOrderUser() {
		return orderUser;
	}
	public void setOrderUser(User orderUser) {
		this.orderUser = orderUser;
	}
	public Product getOrderProduct() {
		return orderProduct;
	}
	public void setOrderProduct(Product orderProduct) {
		this.orderProduct = orderProduct;
	}
	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderUser=" + orderUser + ", orderProduct=" + orderProduct + ", orderDate= "+orderDate+"]";
	}
	
	
}
