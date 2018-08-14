package com.sinocontact.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinocontact.core.BaseController;


@Controller
@RequestMapping("/")
public class DemoController extends BaseController {

	@RequestMapping("/index")
	public String index() {
		this.putObject("user", "hello,it works");
		return "index";
	}
	 
}
