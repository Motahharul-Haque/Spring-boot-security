package com.spring.security.cors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

	@GetMapping("/myApp")
	public String home()
	{
		return "home.html";
	}
	
	@PostMapping("/accessData")
	@ResponseBody
	@CrossOrigin("http://localhost:8083")
	public String resource()
	{
		//System.out.println("I'm calling");
		return "hello resource";
	}
}
