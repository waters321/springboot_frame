package com.sinocontact.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 处理cookie的工具类
 * @author sam
 * @since 2018年5月17日
 */
public class CookieUtils {

	private static final Logger logger = Logger.getLogger(CookieUtils.class);
	
	/**
     * 向cookie中写入一条记录
     * @param name
     * @param value void 
     * @author sam
     * @since 2018年4月11日
     */
    public static  void setCookieValue(HttpServletResponse response,String name,String value){
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
    public  static void  setCookieValue(HttpServletResponse response,String name,String value,int expiry){
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
    public  static void deleteCookie(HttpServletResponse response,String name){
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
    public   static String getCookieValue(HttpServletRequest request,String name){
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
