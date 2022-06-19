package com.sportyshoes.repositories;

import java.sql.Connection;
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

import com.sportyshoes.dao.ProductDao;
import com.sportyshoes.models.Product;
import com.sportyshoes.util.DBConnection;

@Repository
public class ProductRepository implements ProductDao {

	private Connection connection;

	private static Logger log = LoggerFactory.getLogger(ProductRepository.class);
	@Autowired
	public ProductRepository(DBConnection dbc) {
		this.connection = dbc.getConnection();
	}

	@Override
	public int addProduct(Product product) {
		String sql = "INSERT INTO product values(?,?,?,?,?)";
		int rows = 0;
		try {
			PreparedStatement psmt = connection.prepareStatement(sql);
			psmt.setInt(1, product.getProductId());
			psmt.setString(2, product.getProductName());
			psmt.setInt(3, product.getMSRP());
			psmt.setInt(4, product.getQuantityInStock());
			psmt.setString(5, product.getProductVendor());
			rows = psmt.executeUpdate();
		} catch (SQLException e) {
			log.error(e.getLocalizedMessage());
		}
		log.info("Number of rows updated by adding Product:{}",rows);
		return rows;
	}

	@Override
	public Product getProduct(int productId) {
		Product p = null;
		String sql = "SELECT * FROM product where productId=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setInt(1, productId);
			ResultSet rs = s.executeQuery();
			//p = new Product();
			while (rs.next()) {
				/*
				p.setProductId(rs.getInt("productId"));
				p.setProductName(rs.getString("productName"));
				p.setMSRP(rs.getInt("MSRP"));
				p.setQuantityInStock(rs.getInt("quantityInStock"));
				p.setProductVendor(rs.getString("productVendor"));
				*/
				p = new Product(rs.getInt("productId"),rs.getString("productName"),rs.getInt("MSRP")
						,rs.getInt("quantityInStock"),rs.getString("productVendor"));
			}
		} catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		return p;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> productList = new ArrayList<Product>();;
		String sql="SELECT * FROM product";
		try {
			Statement s = connection.createStatement();
			Product p;
			ResultSet rs = s.executeQuery(sql);
			while(rs.next()) {
				p = new Product();
				p.setProductId(rs.getInt("productId"));
				p.setProductName(rs.getString("productName"));
				p.setMSRP(rs.getInt("MSRP"));
				p.setQuantityInStock(rs.getInt("quantityInStock"));
				p.setProductVendor(rs.getString("productVendor"));
				productList.add(p);
			}
		}
		catch(SQLException se) {
			productList=null;
			log.error(se.getLocalizedMessage());
		}
		catch(Exception se) {
			productList=null;
			log.error(se.getLocalizedMessage());
		}
		log.info("Number of products retrieved(All Products): {}",productList.size());
		return productList;
	}

	@Override
	public int updateProduct(int productId, Product product) {
		int rows = 0;
		String sql = "UPDATE product SET productName=?,MSRP=?,quantityInStock=?,productVendor=? WHERE productId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, product.getProductName());
			ps.setInt(2, product.getMSRP());
			ps.setInt(3, product.getQuantityInStock());
			ps.setString(4, product.getProductVendor());
			ps.setInt(5, productId);
			rows = ps.executeUpdate();
		}
		catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of Rows updated by Updating product details:{}",rows);
		return rows;
	}

	@Override
	public int deleteProduct(Product product) {
		int rows=0;
		String sql = "DELETE FROM product WHERE productId=? AND productName=? AND MSRP=? AND quantityInstock=? AND productVendor=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, product.getProductId());
			ps.setString(2, product.getProductName());
			ps.setInt(3, product.getMSRP());
			ps.setInt(4, product.getQuantityInStock());
			ps.setString(5, product.getProductVendor());
			rows = ps.executeUpdate();
		}
		catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of Product rows deleted:{}",rows);
		return rows;
	}

	@Override
	public List<Product> getProductOnName(String productName) {
		List<Product> productList = new ArrayList<Product>();
		String sql="SELECT * FROM product where productName like ?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1, "%"+productName.substring(1,productName.length()-1)+"%");
			System.out.println(s);
			Product p;
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				p = new Product();
				p.setProductId(rs.getInt("productId"));
				p.setProductName(rs.getString("productName"));
				p.setMSRP(rs.getInt("MSRP"));
				p.setQuantityInStock(rs.getInt("quantityInStock"));
				p.setProductVendor(rs.getString("productVendor"));
				productList.add(p);
			}
		}
		catch(SQLException se) {
			productList=null;
			log.error(se.getLocalizedMessage());
		}
		catch(Exception se) {
			productList=null;
			log.error(se.getLocalizedMessage());
		}
		log.info("Number of Products with name %s retrieved:%d",productName,productList.size());
		return productList;
	}

}
