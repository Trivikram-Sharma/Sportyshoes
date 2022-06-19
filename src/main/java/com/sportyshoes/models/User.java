package com.sportyshoes.models;

import java.util.Arrays;

public class User {
	private String userId;
	private String name;
	private char[] password;
	private boolean signedIn;
	public User(String userId, String name, char[] password) {
		super();
		this.userId = userId;
		this.name = name;
		this.password = password;
		this.signedIn = false;
	}
	public boolean isSignedIn() {
		return signedIn;
	}
	public void setSignedIn(boolean signedIn) {
		this.signedIn = signedIn;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", password=" + Arrays.toString(password) + ", signedIn="+signedIn +"]";
	}
	
}
