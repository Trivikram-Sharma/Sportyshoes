package com.sportyshoes.dao;

import com.sportyshoes.models.Admin;

public interface AdminDao {
	//add Admin
	public int addAdmin(Admin a);
	
	//Retrieve Admin details based on adminId
	public Admin getAdmin(String adminId);
	
	//Update Admin based on adminId
	public int updateAdmin(String adminId, Admin a);
	
	//Delete Admin based on adminId
	public int deleteAdmin(Admin a);
}
