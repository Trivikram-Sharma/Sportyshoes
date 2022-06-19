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

import com.sportyshoes.dao.UserDao;
import com.sportyshoes.models.User;
import com.sportyshoes.util.DBConnection;

@Repository
public class UserRepository implements UserDao {

	private Connection connection;

	private static Logger log = LoggerFactory.getLogger(UserRepository.class);
	@Autowired
	public UserRepository(DBConnection dbc) {
		this.connection = dbc.getConnection();
	}

	@Override
	public int addUser(User user) {
		int rows = 0;
		String sql = "INSERT INTO user values(?,?,?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getName());
			ps.setString(3, String.valueOf(user.getPassword()));
			ps.setInt(4,user.isSignedIn() ? 1:0);
			rows = ps.executeUpdate();
		} catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("Rows updated By Adding User:{}",rows);
		return rows;
	}

	@Override
	public User getUser(String userId) {
		User user = null;
		String sql = "SELECT * FROM user where userId=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1, userId);
			ResultSet rs = s.executeQuery();
			
			while(rs.next()) {
				user = new User(rs.getString("userId"),rs.getString("user_name"),rs.getString("user_password").toCharArray());
				user.setSignedIn(rs.getInt("signedIn")==1);
			}
		} catch (SQLException se) {
			user = null;
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			user = null;
			log.error(ex.getMessage());
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM user";
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			User user;
			while(rs.next()) {
				user = new User(rs.getString("userId"),rs.getString("user_name"),rs.getString("user_password").toCharArray());
				user.setSignedIn(rs.getInt("signedIn")==1);
				users.add(user);
			}
		} catch (SQLException se) {
			users = null;
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			users = null;
			log.error(ex.getMessage());
		}
		log.info("Total Number of Users Retrieved(All Users):{}",users.size());
		return users;
	}

	@Override
	public int updateUser(String userId,User user) {
		int rows = 0;
		String sql = "UPDATE user SET user_name=?,user_password=?,signedIn=? WHERE userId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, String.valueOf(user.getName()));
			ps.setString(2, String.valueOf(user.getPassword()));
			ps.setInt(3, user.isSignedIn()?1:0);
			ps.setString(4, userId);
			rows = ps.executeUpdate();
		} catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("Number of Users Rows updated: {}",rows);
		return rows;
	}

	@Override
	public int deleteUser(User user) {
		int rows = 0;
		String sql = "DELETE * FROM user WHERE userId=? AND user_name=? AND user_password=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUserId());
			ps.setString(2, user.getName());
			ps.setString(3, String.valueOf(user.getPassword()));
			rows = ps.executeUpdate();
		} catch (SQLException se) {
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		log.info("Number of users rows deleted: {}",rows);
		return rows;
	}

	@Override
	public List<User> getSingnedInUsers() {
		List<User> users = new ArrayList<User>();
		String sql = "SELECT * FROM user where signedIn=1";
		try {
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(sql);
			User user;
			while(rs.next()) {
				user = new User(rs.getString("userId"),rs.getString("user_name"),rs.getString("user_password").toCharArray());
				user.setSignedIn(rs.getInt("signedIn")==1);
				users.add(user);
			}
		} catch (SQLException se) {
			users = null;
			log.error(se.getLocalizedMessage());
		} catch (Exception ex) {
			users = null;
			log.error(ex.getMessage());
		}
		log.info("Number of Signed In Users Retrieved:{}",users.size());
		return users;
	}

	@Override
	public List<User> getUserswithName(String name) {
		List<User> users = new ArrayList<User>();
		String sql="SELECT * FROM user where user_name like ?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1, "%"+name.substring(1,name.length()-1)+"%");
			ResultSet rs = s.executeQuery();
			User user;
			while(rs.next()) {
				user = new User(rs.getString("userId"),rs.getString("user_name"),rs.getString("user_password").toCharArray());
				user.setSignedIn(rs.getInt("signedIn")==1);
				users.add(user);
			}
		}
		catch(SQLException se) {
			users = null;
			log.error(se.getLocalizedMessage());
		}catch(Exception ex) {
			users = null;
			log.error(ex.getLocalizedMessage());
		}
		log.info("Number of users with name %s retrieved: {}",name,users.size());
		return users;
	}

}
