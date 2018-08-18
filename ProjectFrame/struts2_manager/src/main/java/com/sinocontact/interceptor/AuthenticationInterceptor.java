package com.sinocontact.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseAction;

/**
 * 进行登录认证的拦截器
 * @author sam
 * @since 2018年5月22日
 */
public class AuthenticationInterceptor extends MethodFilterInterceptor {
	private static final Logger logger = Logger.getLogger(AuthenticationInterceptor.class);
	
	
	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
			
		
		// 说明当前进来的action不是UserAction,是需要拦截处理的action
		BaseAction baseAction = (BaseAction) invocation.getAction();
		if (baseAction.getSessionAttribute(ConstCommon.SESSION_KEY) == null) {// 说明还没登录
			// 跳转到登录页面
			return ConstCommon.JSP_LOGIN;
		}

		return invocation.invoke();
	}


	

}
