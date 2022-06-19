package com.sportyshoes.models;

import java.util.Arrays;

public class Admin {
	private String adminId;
	private char[] password;
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
	public Admin(String adminId, char[] password) {
		super();
		this.adminId = adminId;
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [adminId=" + adminId + ", password=" + Arrays.toString(password) + "]";
	}
	
}
