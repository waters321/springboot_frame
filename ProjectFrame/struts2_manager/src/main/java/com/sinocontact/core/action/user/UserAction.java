package com.sinocontact.core.action.user;


import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.alibaba.fastjson.JSONObject;
import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseAction;
import com.sinocontact.core.service.user.UserService;
import com.sinocontact.entity.ResultData;
import com.sinocontact.utils.VerifyCodeUtil;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Random;


/**
 * 用户访问action
 * @author sam
 * @since 2018年5月22日
 */
public class UserAction extends BaseAction {

	private static final Logger logger = Logger.getLogger(UserAction.class);
		
	/**
	 * 响应login提交后进行json数据格式返回
	 * @throws IOException void 
	 * @author sam
	 * @since 2018年5月22日
	 */
    public void login(){
    	
    	//得到用户提交的登录信息
    	String username = this.getParam("username");
    	String password = this.getParam("password");
    	String vercode = this.getParam("vercode");
    	
    	
    	ResultData resultData = null;
    	
    	//检测验证码是否一致
    	if(vercode.equals(this.getSessionAttribute(ConstCommon.VERCODE_NAME)) == false){    		
        	resultData = new ResultData(-1,"验证码不匹配",null);
    	}else{
    		//检测用户信息   		
    		UserService userService = new UserService();
    		if(userService.checkUser(username, password) == false){    			
            	resultData = new ResultData(-2,"无效的用户名或密码",null);
    		}else{
    			resultData = new ResultData(0,"登录成功","1111111111");    			
    			this.setSessionAttribute(ConstCommon.SESSION_KEY, "1111111111");
    		}
    	}   	
    	
    	//错误提示或检测通过的json数据直接返回给浏览器    	
    	this.renderJSON(resultData);
    }

    
    /**
     * 对退出进行响应，返回json数据格式
     * @throws IOException void 
     * @author sam
     * @since 2018年5月22日
     */
    public void logout(){
        this.getSession().invalidate();        
        ResultData resultData = new ResultData(0,"退出成功",null);   	
        this.renderJSON(resultData);
    }



    //图片验证码用的输入流
    private ByteArrayInputStream inputStream;
    public ByteArrayInputStream getInputStream(){
        return inputStream;
    }
    public void setInputStream(ByteArrayInputStream inputStream){
        this.inputStream = inputStream;
    }

   

    /**
     * 生成验证码的响应
     * @return String 
     * @author sam
     * @since 2018年5月21日
     */
    public String generalVerify(){
    	/*
    	HttpServletResponse response = this.getResponse();
    	// 设置浏览器不要缓存此图片
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");        
        response.setDateHeader("Expires", 0);
        */
    	
    	String verifyCode = VerifyCodeUtil.getRandomVerifyCode();
    	ByteArrayInputStream verifyCodeInputStream = VerifyCodeUtil.getImageAsInputStream(verifyCode);
    	setInputStream(verifyCodeInputStream);
    	this.setSessionAttribute(ConstCommon.VERCODE_NAME, verifyCode);
    	return SUCCESS;
    	
    }

    


}
