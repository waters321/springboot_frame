package com.sinocontact.interceptor;

import com.alibaba.fastjson.JSON;

import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseController;
import com.sinocontact.utils.CookieUtils;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 对微信进行授权的拦截器
 * @author jack
 * @since 2018/2/9
 */
public class WxAuthInterceptor implements HandlerInterceptor {

	private static final Logger logger = Logger.getLogger(WxAuthInterceptor.class);
	 /**
     * controller 执行之前调用 进行拦截前处理
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    	//如果cookie中存在openid，则说明已经正常授权
    	if(StringUtils.isEmpty(CookieUtils.getCookieValue(request, ConstCommon.OPEN_ID)) == false){
    		return true;
    	}
    	
    	//跳转到授权页
        String redirectUrl = request.getRequestURL().toString();       
        response.sendRedirect(request.getContextPath()+"/wechatAuth?redirectUrl=" + redirectUrl);
        return false;    	
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    	
    	//微信分享参数设置
    	BaseController baseController = new BaseController();
    	baseController.setRequestAndResponse(request, response);
    	baseController.setWxBaseMessage();    	
    	baseController = null;    	
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
