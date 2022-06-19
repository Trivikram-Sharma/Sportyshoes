package com.sportyshoes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.models.Admin;
import com.sportyshoes.services.AdminService;
import com.sportyshoes.services.OrderService;
import com.sportyshoes.services.ProductService;
import com.sportyshoes.services.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	@Autowired
	private AdminService as;

	private UserService us;

	private ProductService ps;

	private OrderService os;

	@PostMapping("/signIn")
	public Admin signIn(@RequestBody Admin a) {
		return as.signIn(a.getAdminId(), String.valueOf(a.getPassword()));
	}

	@PatchMapping("/{adminId}/update/password")
	public String changePassword(@PathVariable("adminId") String adminId, @RequestBody Admin a) {
		return as.editPassword(adminId, a.getPassword().toString());
	}

}
