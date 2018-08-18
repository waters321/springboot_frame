package com.sinocontact.app.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 所有controller的基类
 * @author sam
 * @since 2018年5月15日
 */
@Controller  
public class BaseController {

	private static final Logger logger = Logger.getLogger(BaseController.class);
	
	
	//公用
	private HttpServletRequest request ;
	private HttpServletResponse response;	
	private Model model;
	private HttpSession session;
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public Model getModel() {
		return model;
	}
	public HttpSession getSession() {
		return session;
	}	
	
	@ModelAttribute
	public void setModel(HttpServletRequest request,HttpServletResponse response,Model model,HttpSession session){
		this.model = model;
		this.request = request;
		this.response = response;
		this.session = session;
	}
	
	
	
	/**
	 * 添加页面变量属性
	 * @param name
	 * @param obj void 
	 * @author sam
	 * @since 2018年5月15日
	 */
	public void putObject(String name,Object obj){
		model.addAttribute(name, obj);
	}
	/**
     * 向cookie中写入一条记录
     * @param name
     * @param value void 
     * @author sam
     * @since 2018年4月11日
     */
    public void setCookieValue(String name,String value){
    	try{
    		Cookie cookie = new Cookie(name,value);		
    		cookie.setPath("/");
    		response.addCookie(cookie);
    	}catch(Exception e){
    		logger.error("向cookie中写入name="+name+",value="+value+"出现异常：",e);
    	}
    }
    /**
     * 
     * @param name
     * @param value
     * @param expiry 失效时间（秒） 
     * @author sam
     * @since 2018年5月15日
     */
    public void  setCookieValue(String name,String value,int expiry){
    	try{
    		Cookie cookie = new Cookie(name,value);		
    		cookie.setMaxAge(expiry);
    		cookie.setPath("/");
    		response.addCookie(cookie);
    	}catch(Exception e){
    		logger.error("向cookie中写入name="+name+",value="+value+"出现异常：",e);
    	}
    }
    /**
     * 删除指定的cookie
     * @param name void 
     * @author sam
     * @since 2018年5月15日
     */
    public void deleteCookie(String name){
    	try{
    		Cookie cookie = new Cookie(name,"");		
    		cookie.setMaxAge(0);
    		cookie.setPath("/");
    		response.addCookie(cookie);
    	}catch(Exception e){
    		logger.error("删除cookie"+name+"出现异常：",e);
    	}
    }
    
    /**
     * 从cookie中获取key为name的值
     * @param name
     * @return String 
     * @author sam
     * @since 2018年4月11日
     */
    public  String getCookieValue(String name){
		String value = null;
		try{
			Cookie[] cookies = request.getCookies();
			if(cookies == null)
				return value;
			
			//遍历方式查找Cookies中是否存在 name
			for(Cookie c : cookies){				
				if(c.getName().equals(name) == true){//在cookies中找到name的cookie
					value = c.getValue();
					break;
				}
			}
		}catch(Exception e){
			value = null;
			logger.error("获取cookie中name="+name+"的数据时出现异常：",e);
		}
		return value;
	}
    
}
