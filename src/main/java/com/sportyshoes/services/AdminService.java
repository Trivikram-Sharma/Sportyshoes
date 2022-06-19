package com.sportyshoes.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sportyshoes.models.Admin;
import com.sportyshoes.models.User;
import com.sportyshoes.repositories.AdminRepository;
import com.sportyshoes.repositories.UserRepository;
@Service
public class AdminService {
	
	@Autowired
	private AdminRepository ar;
	@Autowired
	private UserRepository ur;

	private static Logger log = LoggerFactory.getLogger(AdminService.class);
	public AdminService() {
		super();
	}
	
	public String viewAdminPage(Admin a) throws Exception {
		StringBuilder sb = new StringBuilder("");
		String response = "";
		//Check if the argument admin object and the persisted object(if exists) are having same password
		//Thus, verifying if the admin is registered at backend
		Admin rec = ar.getAdmin(a.getAdminId());
		if(rec==null) {
			log.error("Admin Record Not Found!\n{}",rec);
			throw new Exception("Credentials Not Found for the Admin! Kindly check the credentials.");
		}
		else if(!(rec.getPassword().equals(rec.getPassword()))) {
			log.error(response);
			throw new Exception("Incorrect Password. Kindly check the credentials.");
		}
		else {
			sb.append("Welcome "+a.getAdminId()+"!\n");
			sb.append("<html>");
			sb.append("<body>");
			sb.append("<a>CHANGE PASSWORD</a><br>");
			sb.append("<a>SINGNED UP USERS</a><br>");
			sb.append("<a>SEARCH USERS</a><br>");
			sb.append("<a>VIEW USER DETAILS</a><br>");
			sb.append("<a>LIST PRODUCTS</a><br>");
			sb.append("<a>ADD PRODUCT</a><br>");
			sb.append("<a>VIEW PRODUCT DETAILS</a><br>");
			sb.append("<a>UPDATE PRODUCT</a><br>");
			sb.append("<a>DELETE PRODUCT</a><br>");
			sb.append("<a>VIEW/LIST ORDERS</a><br>");
			sb.append("<a>VIEW PRODUCT ORDERS</a><br>");
			sb.append("<a>VIEW USER ORDERS</a><br>");
			sb.append("<a></a><br>");
			sb.append("</body>");
			sb.append("</html>");
			response = sb.toString();
		}
		
		return response;
	}
	
	public String editPassword(String adminId,String password) {
		Admin a = ar.getAdmin(adminId);
		String res = "";
		if(a==null) {
			res = "Admin Not Found! Kindly check the credentials.";
		}
		else {
			if(a.getPassword().toString().equals(password)) {
				res = "Same Password Entered. Please enter different password!";
			}
			else {
				a.setPassword(password.toCharArray());
				int rows = ar.updateAdmin(adminId, a);
				res = (rows==1)? ("Password Updated Successfully!") : ("Password NOT Updated! Please check the credentials and try again.");
			}
		}
		return res;
	}
	
	public List<User> viewSignedUpUsers(){
		List<User> users = ur.getSingnedInUsers();
		return users;
	}
	
	public Admin signIn(String adminId,String password) {
		String res = "";
		Admin a =ar.getAdmin(adminId);
		if(a==null) {
			res = "Sign In Failed! Please Check credentials!";
		}
		else if(!a.getPassword().toString().equals(password)) {
			res = "Incorrect Password! Please check the credentials and try again!";
		}
		else {
			//a.setSignedIn(true);
			res = "Welcome "+a.getAdminId()+"! Your sign In with"+a.getAdminId()+" is successful!";
		}
		System.out.println(res);
		return a;
	}
	
	public List<User> getUserswithName(String name){
		return ur.getUserswithName(name);
	}
	
}
