package com.sportyshoes.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DBConnection {
	Connection connection = null;

	public DBConnection(@Value("${db.url}") String url, @Value("${db.username}") String username,
			@Value("${db.password}") String password) {
			try {
				this.connection = DriverManager.getConnection(url, username, password);
			}
			catch(SQLException se) {
				
				System.out.println(se.getMessage());
			}
			catch(Exception ex) {
				System.out.println(ex.getLocalizedMessage());
			}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public void closeConnection() {
		if(connection!=null) {
			try {
				connection.close();
			} catch (SQLException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}
}
