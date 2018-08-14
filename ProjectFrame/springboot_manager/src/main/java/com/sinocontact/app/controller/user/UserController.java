package com.sinocontact.app.controller.user;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.sinocontact.app.common.ConstCommon;
import com.sinocontact.app.config.AppWebConfig;
import com.sinocontact.app.controller.BaseController;
import com.sinocontact.app.service.user.UserService;



/**
 * 登录action
 * @author sam
 * @since 2018年5月15日
 */
@Controller
public class UserController extends BaseController{
		
	private static final Logger logger = Logger.getLogger(UserController.class);
			
	
	
	@RequestMapping("/user/login")
	/**
	 * 登录页面
	 * @return String 
	 * @author sam
	 * @since 2017年10月18日
	 */
	String login(){
		
		return "views/user/login";
	}
	
	
	@RequestMapping(value="/user/logout",produces="application/json;charset=UTF-8")
	@ResponseBody
	/**
	 * 
	 * @return String 
	 * @author sam
	 * @since 2018年5月15日
	 */	
	String logout(){
		//删除缓存
		getSession().removeAttribute(ConstCommon.SESSION_KEY);
		JSONObject job = new JSONObject();
		 job.put("code", 0);
		job.put("msg", "登出成功");
	JSONObject data = new JSONObject();
		data.put("access_token", "");
		job.put("data",data );
	String jsonText = job.toJSONString();
		logger.info(jsonText);
		return jsonText;
}
	
	@RequestMapping(value="/user/loginPost",produces="application/json;charset=UTF-8")
	@ResponseBody
	/**
	 * 响应登录提交	 
	 * @param username
	 * @param password
	 * @param session
	 * @return String 
	 * @author sam
	 * @since 2017年10月18日
	 */
	String loginPost(@RequestParam(value = "username", required = true) String username,@RequestParam(value = "password", required = true) String password){
		
		JSONObject job = new JSONObject();			
		UserService ls = new UserService(); 
		if(ls.checkUser(username, password)==false){					
			job.put("code", -1);
			job.put("msg", "用户名或密码不存在");		
			JSONObject data = new JSONObject();
			data.put("access_token", "");		
			job.put("data",data );
			
		}else{
			// 设置session
	        this.getSession().setAttribute(ConstCommon.SESSION_KEY, username);
	        job.put("code", 0);
			job.put("msg", "登录成功");		
			JSONObject data = new JSONObject();
			data.put("access_token", username);		
			job.put("data",data );
		}
		
		String jsonText = job.toJSONString();
		logger.info(jsonText);		
		return jsonText;		
	}
}
