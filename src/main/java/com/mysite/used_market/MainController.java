package com.mysite.used_market;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	
	@RequestMapping("/used_market")
	@ResponseBody
	public String index() {
		System.out.println("index");
			return "hello boot";
	}
	
	
}
