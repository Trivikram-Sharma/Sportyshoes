package com.sportyshoes.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sportyshoes.models.Order;
import com.sportyshoes.services.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@Autowired
	private OrderService os;
	
	@GetMapping("/all/sorted")
	public List<Order> getOrders(@RequestParam(required = true) String key,
			@RequestParam(required = true) String ordering) {
		return os.getOrders(key, ordering);
	}
}
