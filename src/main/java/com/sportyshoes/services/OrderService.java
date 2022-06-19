package com.sportyshoes.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.models.Order;
import com.sportyshoes.models.Product;
import com.sportyshoes.repositories.OrderRepository;
import com.sportyshoes.repositories.ProductRepository;
import com.sportyshoes.repositories.UserRepository;
import com.sportyshoes.util.DateTimeUtilities;
@Service
public class OrderService {

	@Autowired
	private OrderRepository or;
	@Autowired
	private DateTimeUtilities dte;
	@Autowired
	private ProductRepository pr;
	@Autowired
	private UserRepository ur;
	@Autowired
	public OrderService() {
		super();
	}
	
	public String purchaseProduct(int productId,String userId) {
		String res = "";
		Product p = pr.getProduct(productId);
		System.out.println(p);
		Order o = new Order();
		System.out.println("Max order Id :"+or.getMaxOrderId());
		o.setOrderId(or.getMaxOrderId()+1);
		o.setOrderProduct(pr.getProduct(productId));
		o.setOrderUser(ur.getUser(userId));
		Date k = new Date();
		System.out.println(dte.getSQLDate(k));
		o.setOrderDate(k);
		int status1 = or.addOrder(o);
		p.setQuantityInStock(p.getQuantityInStock()-1);
		int status2 = pr.updateProduct(productId, p);
		res = (status1==1 && status2==1)? ("Product Ordered Successfully!"):("Product Order Unsuccessful!Please check details and try again!");
		return res;
	}
	
	public List<Order> purchaseHistory(String userId){
		return or.getOrdersOfUser(ur.getUser(userId));
	}
	
	public List<Order> getOrderWithProduct(int productId){
		List<Order> orders;
		Product p = pr.getProduct(productId);
		if(p==null) {
			orders = null;
		}
		else {
			orders = or.getOrdersOfProduct(p);
		}
		return orders;
	}
	
	public List<Order> getOrders(String key, String order) {
		List<Order> orders;
		key = key.substring(1, key.length()-1);
		order= order.substring(1,order.length()-1);
		System.out.println(key);
		System.out.println(order);
		if(String.valueOf(key).contentEquals("orderDate")) {
			if(String.valueOf(order).equalsIgnoreCase("ASC")) {
				orders = or.getOrdersByDateAsc();
			}
			else if(String.valueOf(order).equalsIgnoreCase("DESC")) {
				orders = or.getOrdersByDateDesc();
			}
			else {
				System.out.println("Order Date: Ordering Not specified");
				orders = null;
			}
			return orders;
		}
		else if(String.valueOf(key).equals("orderId")) {
			if(String.valueOf(order).equalsIgnoreCase("ASC")) {
				orders = or.getOrdersByIdAsc();
			}
			else if(String.valueOf(order).equalsIgnoreCase("DESC")) {
				orders = or.getOrdersByIdDesc();
			}
			else {
				System.out.println("Order ID: ordering not specified.");
				orders = null;
			}
			return orders;
		}
		else {
			System.out.println("Key Not specified");
			return null;
		}
	}
}
