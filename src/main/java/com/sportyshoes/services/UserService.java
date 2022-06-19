package com.sportyshoes.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.models.User;
import com.sportyshoes.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository ur;
	
	
	public UserService() {
		super();
	}
	
	public String signUp(String userId,String name,String password) {
		String res = "";
		User euser;
		User user = ur.getUser(userId);
		if(user!=null) {
			res ="The profile is already Registered! Kindly check your credentials.";
		}
		else {
			euser = new User(userId,name,password.toCharArray());
			int status = ur.addUser(euser);
			res = (status==1)? "Registration Successful!":"Registration unsuccessful! Please check credentials!";
		}
		return res;
	}
	
	public String signIn(String userId, String password) {
		String res = "";
		User user = ur.getUser(userId);
		String opwd = String.valueOf(user.getPassword());
		if(user==null) {
			res = "Sign In Failed! Please Check credentials and register if not done.";
		}
		else if(!String.valueOf(password).contentEquals(opwd)) {
			//System.out.println(user.getPassword());
			//System.out.println(password);
			System.out.println("From Database");
			System.out.println("JSON Password:");
			System.out.println(password);
			System.out.println("DB Password: "+opwd);
			
			System.out.println("From Database:");
			System.out.println(user);
			res = "Incorrect Password! Please check the credentials and try again!\n";
		}
		else {
			user.setSignedIn(true);
			ur.updateUser(userId, user);
			System.out.println(user);
			res = "Welcome "+user.getName()+"! Your sign In with"+user.getUserId()+" is successful!";
		}
		return res;
	}
	
	public String signOut(String userId) {
		String res="";
		User user = ur.getUser(userId);
		if(user.isSignedIn()) {
			user.setSignedIn(false);
			int status = ur.updateUser(userId, user);
			res = (status==1)? "SignedOut Successfully!":"SignOut Failed!";
		}
		else {
			res = "Not signed In. Thus, not signed out!";
		}
		return res;
	}
	
	public String viewUserDetails(String userId) {
		String res="";
		User user = ur.getUser(userId);
		if(user==null) {
			res="User details Not Found! Kindly check the credentials and if the account is registered!";
		}
		else {
			res = user.toString();
		}
		return res;
	}
	
	public String editUserPassword(String userId,String password) {
		String res="";
		User user = ur.getUser(userId);
		if(user==null) {
			res="User details Not Found! Kindly check the credentials and if the account is registered!";
		}
		else {
			user.setPassword(password.toCharArray());
			int status = ur.updateUser(userId, user);
			res = (status==1)?"Password updated Successfully!":"Password Not Updated! Please check credentials and try again!";
		}
		return res;
	}
	
	public String editUserName(String userId,String name) {
		String res="";
		User user = ur.getUser(userId);
		if(user==null) {
			res="User details Not Found! Kindly check the credentials and if the account is registered!";
		}
		else {
			user.setName(name);
			int status = ur.updateUser(userId, user);
			res = (status==1)?"User Name updated Successfully!":"User Name Not Updated! Please check credentials and try again!";
		}
		return res;
	}
}
