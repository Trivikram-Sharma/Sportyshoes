package com.sportyshoes.controllers;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@GetMapping("/")
	public String visitMessage() {
		return "Hello, Visior! visiting Time :"+new Date();
	}
}
