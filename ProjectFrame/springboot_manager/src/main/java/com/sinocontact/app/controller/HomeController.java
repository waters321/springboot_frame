package com.sinocontact.app.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinocontact.app.common.ConstCommon;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	
	@RequestMapping("/")
	/**
	 * 默认首页
	 * @return String 
	 * @author sam
	 * @since 2017年10月18日
	 */
	String home(){
		//putObject("username",getSession().getAttribute(ConstCommon.SESSION_KEY));		
		return "views/index";
	}
	
	@RequestMapping("/home/console")
	String console(){
		
		return "views/home/console";
	}
}
