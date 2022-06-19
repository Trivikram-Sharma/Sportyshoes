package com.sportyshoes.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.models.Order;
import com.sportyshoes.models.User;
import com.sportyshoes.services.AdminService;
import com.sportyshoes.services.OrderService;
import com.sportyshoes.services.ProductService;
import com.sportyshoes.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	private UserService us;
	@Autowired
	private ProductService ps;
	@Autowired
	private OrderService os;

	@Autowired
	private AdminService as;

	private static Logger log = LoggerFactory.getLogger(UserController.class);
	@PostMapping("/signUp")
	public String signUp(@RequestBody User user) {
		log.info("User Object with the request of Sign Up:\n{}",user);
		return us.signUp(user.getUserId(), user.getName(), String.valueOf(user.getPassword()));
	}

	@PostMapping("/signIn")
	public String signIn(@RequestBody User user) {
		log.info("User Object with the request of Sign In:\n{}",user);
		return us.signIn(user.getUserId(), String.valueOf(user.getPassword()));
	}

	@PostMapping("/signOut")
	public String signOut(@RequestBody User user) {
		return us.signOut(user.getUserId());
	}

	@GetMapping("/{userId}")
	public String viewAccountDetails(@PathVariable("userId") String userId) {
		return us.viewUserDetails(userId);
	}

	@PostMapping("/{userId}/product/{productId}/order")
	public String purchaseProduct(@PathVariable("userId") String userId, @PathVariable("productId") int productId) {
		return os.purchaseProduct(productId, userId);
	}

	@GetMapping("/{userId}/order/all")
	public ResponseEntity<List<Order>> getUserDetails(@PathVariable("userId") String userId) {
		List<Order> orders = os.purchaseHistory(userId);
		log.info("Orders retrieved of the user {}: \n{}",userId,orders);
		return (orders == null || orders.isEmpty()) ? new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR)
				: new ResponseEntity<>(orders, HttpStatus.OK);
	}

	@PatchMapping("/{userId}/update/password")
	public String editAccount(@PathVariable("userId") String userId, @RequestBody User user) {
		return us.editUserPassword(userId, String.valueOf(user.getPassword()));
	}

	@PatchMapping("/{userId}/update/name")
	public String editAccountName(@PathVariable("userId") String userId, @RequestBody User user) {
		return us.editUserName(userId, user.getName());
	}

	@GetMapping("/all")
	public ResponseEntity<List<User>> getSignedUpUsers() {
		List<User> users = new ArrayList<User>();
		log.info("Signed Up Users Retrieved:\n{}",users);
		users = as.viewSignedUpUsers();
		return (users.isEmpty() || users == null) ? new ResponseEntity<>(null, HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> getUsersWithName(@RequestParam(required=true) String name){
		List<User> users = as.getUserswithName(name);
		log.info("Users With Name {} Retrieved:\n{}",name,users);
		if(users.isEmpty() || users==null) {
			return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(users,HttpStatus.OK);
		}
	}
	
	

}
