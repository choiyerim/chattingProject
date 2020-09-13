package com.doyun.doyun;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LocationController {
	
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
//	@RequestMapping("/")
	public String hello(HttpServletRequest request) {
		logger.info("User Visit!");
		return "index/hello";
	}
	@RequestMapping("/")
	public String prepared() {
		logger.info("User Visit!");
		return "index/index";
		
	}
	
	@RequestMapping("/prepared")
	public String prepareds() {
		return "index/prepared";
	}
	@RequestMapping("/user/signIn")
	public String signInPage() {
		
		return "user/signInPage";
	}
}
