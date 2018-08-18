package com.sinocontact.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sinocontact.app.common.ConstCommon;

public class SecurityInterceptor extends HandlerInterceptorAdapter {

	@Override
    /**
     * 进行拦截前处理
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	
    	//得到session，并判断是否存在session_key，如果存在，则进行后续处理
        HttpSession session = request.getSession();
        if (session.getAttribute(ConstCommon.SESSION_KEY) != null){
            return true;
        }
                
        String sPath = request.getContextPath();
        
        // session_key不存在，跳转登录
        String url = sPath+"/user/login";
        response.sendRedirect(url);
        return false;            
    }
}
