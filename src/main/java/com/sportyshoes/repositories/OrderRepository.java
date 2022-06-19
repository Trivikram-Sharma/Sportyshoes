package com.sportyshoes.repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sportyshoes.dao.OrderDao;
import com.sportyshoes.models.Order;
import com.sportyshoes.models.Product;
import com.sportyshoes.models.User;
import com.sportyshoes.util.DBConnection;
import com.sportyshoes.util.DateTimeUtilities;

@Repository
public class OrderRepository implements OrderDao {
	private Connection connection;

	private UserRepository ur;

	private ProductRepository pr;
	
	@Autowired
	DateTimeUtilities dte;
	
	private static Logger log = LoggerFactory.getLogger(OrderRepository.class);
	@Autowired
	public OrderRepository(DBConnection dbc, UserRepository ur, ProductRepository pr) {
		this.connection = dbc.getConnection();
		this.ur = ur;
		this.pr = pr;
	}

	@Override
	public int addOrder(Order o) {
		int rows = 0;
		String sql = "INSERT INTO sportyshoes.order VALUES(?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, o.getOrderId());
			ps.setString(2, String.valueOf(o.getOrderUser().getUserId()));
			ps.setInt(3, o.getOrderProduct().getProductId());
			ps.setDate(4, dte.getSQLDate(o.getOrderDate()));
			
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.info(sql);
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Added Order rows={}",rows);
		return rows;
	}

	@Override
	public Order getOrderwithId(int orderId) {
		String sql = "SELECT * FROM sportyshoes.order WHERE orderId=?";
		Order o = null;
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1,orderId);
			o = new Order();
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate(rs.getDate("orderDate"));
			}
		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		return o;
	}

	@Override
	public List<Order> getOrdersOfUser(User user) {
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order WHERE orderUser=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1,user.getUserId());
			Order o;
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of Orders of userId:%s retrieved, %d",user.getUserId(),orderList.size());
		return orderList;
	}

	@Override
	public List<Order> getOrdersOfProduct(Product p) {
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order WHERE orderProduct=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1,p.getProductId());
			Order o;
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Orders of product:{} retrieved:%d",p,orderList.size());
		return orderList;
	}

	@Override
	public List<Order> getOrdersUp(User user, Product p) {
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order WHERE orderUser=? AND orderProduct=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1, user.getUserId());
			s.setInt(2, p.getProductId());
			Order o;
			ResultSet rs = s.executeQuery();
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Orders of userId:%s and product:{} retrieved : %d", user.getUserId(),p,orderList.size());
		return orderList;
	}

	@Override
	public List<Order> getAllOrders() {
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order";
		try {
			Statement s = connection.createStatement();
			Order o;
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of Orders Retrieved(All ORDERS):%d",orderList.size());
		return orderList;
	}

	@Override
	public int updateOrderUser(Order o, User u) {
		int rows = 0;
		String sql = "UPDATE sportyshoes.order SET orderUser=? WHERE orderId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, u.getUserId());
			ps.setInt(2, o.getOrderId());

			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of order users rows updated:%d",rows);
		return rows;
	}

	@Override
	public int updateOrderProduct(Order o, Product p) {
		int rows = 0;
		String sql = "UPDATE sportyshoes.order SET orderProduct=? WHERE orderId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, p.getProductId());
			ps.setInt(2, o.getOrderId());

			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of order product rows updated:%d",rows);
		return rows;
	}

	@Override
	public int deleteOrderwithId(int orderId) {
		int rows = 0;
		String sql = "DELETE FROM sportyshoes.order WHERE orderId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, orderId);
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of orders deleted:%d",rows);
		return rows;
	}

	@Override
	public int deleteOrderwithUserId(String userId) {
		int rows = 0;
		String sql = "DELETE FROM sportyshoes.order WHERE orderUser=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, userId);
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of order users rows deleted:%d",rows);
		return rows;
	}

	@Override
	public int deleteOrderswithProduct(Product p) {
		int rows = 0;
		String sql = "DELETE FROM sportyshoes.order WHERE orderProduct=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, p.getProductId());
			rows = ps.executeUpdate();

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of order product rows deleted:%d",rows);
		return rows;
	}
	
	public int getMaxOrderId() {
		int orderId=0;
		String sql = "SELECT MAX(orderId) AS max_Id FROM sportyshoes.order";
		try {
			Statement s = connection.createStatement();
			
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				orderId = rs.getInt("max_Id");
			}

		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Latest order Id Retrieved:%d",orderId);
		return orderId;
		
	}
	
	public List<Order> getOrdersByDateAsc(){
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order ORDER BY orderDate asc";
		try {
			Statement s = connection.createStatement();
			Order o;
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of orders retrieved by date in Ascending order:%d",orderList.size());
		return orderList;
	}
	public List<Order> getOrdersByDateDesc(){
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order ORDER BY orderDate desc";
		try {
			Statement s = connection.createStatement();
			Order o;
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of orders retrieved by date in Descending order:%d",orderList.size());
		return orderList;
	}
	public List<Order> getOrdersByIdAsc(){
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order ORDER BY orderId asc";
		try {
			Statement s = connection.createStatement();
			Order o;
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of orders retrieved by OrderId in Ascending order:%d",orderList.size());
		return orderList;
	}
	
	public List<Order> getOrdersByIdDesc(){
		List<Order> orderList = new ArrayList<Order>();
		String sql = "SELECT * FROM sportyshoes.order ORDER BY orderId desc";
		try {
			Statement s = connection.createStatement();
			Order o;
			ResultSet rs = s.executeQuery(sql);
			while (rs.next()) {
				o = new Order();
				o.setOrderId(rs.getInt("orderId"));
				o.setOrderUser(ur.getUser(rs.getString("orderUser")));
				o.setOrderProduct(pr.getProduct(rs.getInt("orderProduct")));
				o.setOrderDate((java.util.Date)rs.getDate("orderDate"));
				orderList.add(o);
			}
		} catch (SQLException e) {
			orderList = null;
			log.error(e.getLocalizedMessage());
		} catch (Exception ex) {
			orderList = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of orders retrieved by orderId in Descending order:%d",orderList.size());
		return orderList;
	}

}
