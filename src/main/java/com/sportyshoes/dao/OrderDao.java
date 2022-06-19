package com.sportyshoes.dao;

import java.util.List;

import com.sportyshoes.models.Order;
import com.sportyshoes.models.Product;
import com.sportyshoes.models.User;

public interface OrderDao {
	//add Order upon a purchase
	public int addOrder(Order o);
	
	//Retrieve Order based on Order Id
	public Order getOrderwithId(int orderId);
	
	//Retrieve Orders of a particular user
	public List<Order> getOrdersOfUser(User user);
	
	//Retrieve Orders of a particular product
	public List<Order> getOrdersOfProduct(Product p);
	
	//Retrieve Orders of a particular user and particular product
	public List<Order> getOrdersUp(User user, Product p);
	
	//Retrieve All Orders
	public List<Order> getAllOrders();
	
	//Update Order User
	public int updateOrderUser(Order o,User u);
	
	//Update Order Product
	public int updateOrderProduct(Order o, Product p);
	
	//Delete Order based on order Id
	public int deleteOrderwithId(int orderId);
	
	//Delete Orders based on UserId
public int deleteOrderwithUserId(String userId);
	
	//Delete Orders based on productId
	public int deleteOrderswithProduct(Product p);
}
