package com.sinocontact.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * action的基类，所有的action都需要继承此基类
 * @author sam
 * @since 2018年5月21日
 */
public class BaseAction extends ActionSupport {

	private static final Logger logger = Logger.getLogger(BaseAction.class);
	
	/**
	 * 得到当前action对应的request
	 * @return HttpServletRequest 
	 * @author sam
	 * @since 2018年5月21日
	 */
    public HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    /**
     * 得到当前action对应的response
     * @return HttpServletResponse 
     * @author sam
     * @since 2018年5月21日
     */
    public HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }

    /**
     * 得到当前action对应的session对象
     * @return HttpSession 
     * @author sam
     * @since 2018年5月21日
     */
    public HttpSession getSession(){
    	return getRequest().getSession();
    }
    
    /**
     * 得到get/post上来的request中的参数名对应的值
     * @param key 参数名称
     * @return String  参数值
     * @author sam
     * @since 2018年5月21日
     */
    public String getParam(String key){
		String param = getRequest().getParameter(key);
		return param;
		
	}
    
    /**
     * 设置request参数
     * @param name  参数名
     * @param value 参数值
     * @author sam
     * @since 2018年5月21日
     */
    public void setRequestAttribute(String name, Object value) {
    	getRequest().setAttribute(name, value);
    }

    /**
     * 设置session参数
     * @param name 参数名
     * @param value 参数值 
     * @author sam
     * @since 2018年5月21日
     */
    public void setSessionAttribute(String name, Object value) {
    	getSession().setAttribute(name, value);
    }

    
    /**
     * 获取session参数
     * @param key 参数名
     * @return Object 参数值
     * @author sam
     * @since 2018年5月21日
     */
    public Object getSessionAttribute(String key) {
        return getSession().getAttribute(key);
    }

   
    /**
     * 获取request 参数
     * @param key 参数名
     * @return Object  参数值
     * @author sam
     * @since 2018年5月21日
     */
    public Object getResquestAttribute(String key){
        return getRequest().getAttribute(key);
    }
    
    /**
     * 把json对应直接返回给浏览器
     * @param jo
     * @throws IOException void 
     * @author sam
     * @since 2018年5月22日
     */
    public void renderJSON(JSONObject jsonObject){
    	try{
	    	HttpServletResponse response=getResponse();  
	        //设定返回的内容格式和编码
	        response.setContentType("text/html");
	        response.setCharacterEncoding("utf-8");
	        PrintWriter out = response.getWriter();
	        out.println(jsonObject.toString());  
	        out.flush();  
	        out.close();
    	}catch(Exception e){
    		logger.error("",e);
    	}
    }
    /**
     * 把java对象转换为json (java对象必须是javabean)
     * @param javaObject void 
     * @author sam
     * @since 2018年5月22日
     */
    public void renderJSON(Object javaObject){
    	try{
	    	HttpServletResponse response=getResponse();  
	    	//设定返回的内容格式和编码  
	        response.setContentType("text/html");
	        response.setCharacterEncoding("utf-8");
	        PrintWriter out = response.getWriter();	   	        
	        out.println(JSON.toJSON(javaObject).toString());  
	        out.flush();  
	        out.close();
    	}catch(Exception e){
    		logger.error("",e);
    	}
    }
}
