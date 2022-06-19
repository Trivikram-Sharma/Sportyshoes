package com.sportyshoes.dao;

import java.util.List;

import com.sportyshoes.models.User;

public interface UserDao {
	//add User
	public int addUser(User user);
	
	//Retrieve user based on UserId
	public User getUser(String userId);
	
	//Retrieve all users
	public List<User> getAllUsers();
	
	//Retrieve signed In Users
	public List<User> getSingnedInUsers();
	
	//Get users with a username
	public List<User> getUserswithName(String name);
	
	//Update User based on UserId
	public int updateUser(String userId,User user);
	
	//Delete user
	public int deleteUser(User user);
}
