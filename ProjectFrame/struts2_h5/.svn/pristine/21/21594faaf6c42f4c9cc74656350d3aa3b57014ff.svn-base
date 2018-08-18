package com.sinocontact.core.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.foxinmy.weixin4j.mp.model.User;
import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseAction;
import com.sinocontact.core.service.WxAuthService;

/**
 * 对微信进行授权的Action
 * @author sam
 * @since 2018年5月18日
 */
public class WxAuthAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(WxAuthAction.class);
	
	//微信认证的真正的url地址
	private String wxAuthUrl ;
	//微信授权成功后，再返回到进入微信授权之前的url
	private String wxRedirectUrl ;	
	
	public String getWxAuthUrl() {
		return wxAuthUrl;
	}
	public void setWxAuthUrl(String wxAuthUrl) {
		this.wxAuthUrl = wxAuthUrl;
	}
	public String getWxRedirectUrl() {
		return wxRedirectUrl;
	}
	public void setWxRedirectUrl(String wxRedirectUrl) {
		this.wxRedirectUrl = wxRedirectUrl;
	}
	
	
	
	/**
	 * 微信授权url入口
	 * @return String 
	 * @author sam
	 * @since 2018年5月24日
	 */
	public String wechatAuth(){
			
		//从cookie中提取重定向url
		String redirectUrl = this.getCookieValue("redirectUrl");
		
		//生成真正的微信回调url
    	String realCallBackUrl =WxAuthService.createWechatCallbackUrl(this.getRequest(), redirectUrl); 
    	
    	//根据业务需要添加回调时需要返回的参数及值
    	//...realCallBackUrl = addCallBackUrlParam(realCallBackUrl,urlParamName,urlParamValue);
    	
    	//生成认证url
    	String realWxAuthUrl = WxAuthService.createWxAuthUrl(realCallBackUrl);
    	
    	//设置需要跳转的认证url
    	this.setWxAuthUrl(realWxAuthUrl);    	
		
		return SUCCESS;
	}
	
	/**
	 * 给realCallBackUrl添加参数
	 * @param name  url中的参数名
	 * @param value url中的参数值
	 * @return String 添加好参数后的url
	 * @author sam
	 * @since 2018年5月25日
	 */
	private String addCallBackUrlParam(String realCallBackUrl,String name,String value){
		String url = String.format("%s&%s=%s", realCallBackUrl,name,value);
		return url;
	}
	 /**
     * 微信授权回调响应函数   
     * @author sam
     * @since 2018年5月21日
     */
	public String wechatCallback(){
		
		//获取微信返回的参数
		String code = this.getParam("code");
		String state= this.getParam("state");
		
		//此处的重定向url是用户第一次在未授权时访问的页面的url，如果在微信授权后，回到其他指定页面或url，可以
		//根据业务逻辑调整或重新设置此redirectUrl的值。
		String redirectUrl = this.getParam("redirectUrl");
		
		try {
        	//得到授权的微信用户信息(openid,nick,...)
            User user = WxAuthService.getWeixinUser(code);
            String userJsonText =JSON.toJSONString(user); 
            logger.info(userJsonText);

            //注意，因为是H5活动，一般访问量比较大（便于负载均衡部署），所以用户信息保存到cookie中；失效时间为24小时,把用户的open_id                    
            this.setCookieValue(ConstCommon.OPEN_ID, user.getOpenId(), ConstCommon.COOKIE_EXPIRY);
            
            //微信用户信息是否保存，由业务需求决定
            //...this.setCookieValue(ConstCommon.SESSION_KEY, userJsonText, ConstCommon.COOKIE_EXPIRY);  
            
            //设置要返回的页面url (如果不想返回原来的url，可以在此指定需要到达的页面url)
            this.setWxRedirectUrl(redirectUrl);
        }catch (Exception e){
            logger.error("", e);
        }
		
		return SUCCESS;
	}
	/**
	 * 用户在微信里面分享了某个页面后，分享的结果反馈回调响应函数。本函数由微信回调。
	 *  void 
	 * @author sam
	 * @since 2018年5月30日
	 */
	public void onShareCallback(){
		String wxShareResultJson = this.getParam("wxShareResultJson");
		String shareTypeMark = this.getParam("share_type_mark");
		logger.info(shareTypeMark);
		logger.info(wxShareResultJson);
	}
}
