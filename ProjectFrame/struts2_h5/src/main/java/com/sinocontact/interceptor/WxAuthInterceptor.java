package com.sinocontact.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.foxinmy.weixin4j.util.DigestUtil;
import com.foxinmy.weixin4j.util.RandomUtil;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseAction;
import com.sinocontact.core.service.WxAuthService;
import com.sinocontact.utils.CookieUtils;

/**
 * 进行登录认证的拦截器
 * @author sam
 * @since 2018年5月22日
 */
public class WxAuthInterceptor extends MethodFilterInterceptor {
	private static final Logger logger = Logger.getLogger(WxAuthInterceptor.class);
	
		
	@Override
	public String doIntercept(ActionInvocation invocation) throws Exception {
		
		BaseAction baseAction = (BaseAction) invocation.getAction();	
		
		//如果cookie中存在openid，则说明已经正常授权
    	if(StringUtils.isEmpty(baseAction.getCookieValue(ConstCommon.OPEN_ID)) == false){
    		
    		
    		//设置每个页面都可能需要的用到的参数信息
    		baseAction.setWxBaseMessage();
    		
    		return  invocation.invoke();
    	}
    	
    	//跳转到授权页(把要跳转的页面保存在cookie中30秒)    	
        String redirectUrl = baseAction.getRequest().getRequestURL().toString();        
        baseAction.setCookieValue("redirectUrl", redirectUrl,30);             
        return ConstCommon.ACTION_WECHATAUTH;		
	}
	
	


	

}
