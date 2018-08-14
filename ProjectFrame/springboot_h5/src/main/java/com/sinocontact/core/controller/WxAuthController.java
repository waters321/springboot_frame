package com.sinocontact.core.controller;

import com.alibaba.fastjson.JSON;

import com.foxinmy.weixin4j.cache.FileCacheStorager;
import com.foxinmy.weixin4j.exception.WeixinException;
import com.foxinmy.weixin4j.model.Token;
import com.foxinmy.weixin4j.model.WeixinAccount;
import com.foxinmy.weixin4j.mp.WeixinProxy;
import com.foxinmy.weixin4j.mp.model.OauthToken;
import com.foxinmy.weixin4j.mp.model.User;
import com.sinocontact.common.ConstCommon;
import com.sinocontact.core.BaseController;
import com.sinocontact.core.service.WxAuthService;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;



/**
 * 对微信进行授权的Controller
 * @author sam
 * @since 2018年5月18日
 */
@Controller
@RequestMapping("/")
public class WxAuthController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(WxAuthController.class);
	
	
	
	/**
     * 微信授权url入口
     * @param redirectUrl
     * @return ModelAndView 
     * @author sam
     * @since 2018年5月17日
     */
    @RequestMapping("/wechatAuth")
    public ModelAndView wechatAuth(@RequestParam String redirectUrl){
    			

		// 生成真正的微信回调url
		String realCallBackUrl = WxAuthService.createWechatCallbackUrl(this.getRequest(), redirectUrl);

		// 根据业务需要添加回调时需要返回的参数及值(注意，如果此处添加了参数，则在wechatCallback函数中需要添加此参数的传入)
		// ...realCallBackUrl =
		// addCallBackUrlParam(realCallBackUrl,urlParamName,urlParamValue);

		// 生成认证url
		String realWxAuthUrl = WxAuthService.createWxAuthUrl(realCallBackUrl);
		
		//跳转到微信授权url
		return new ModelAndView(new RedirectView(realWxAuthUrl));    	
    }


    /**
     * 微信授权回调
     * @param code
     * @param state
     * @param redirectUrl
     * @return ModelAndView 
     * @author sam
     * @since 2018年5月21日
     */
    @RequestMapping("/wechatCallback")
    public ModelAndView wechatCallback( String code, String state, String redirectUrl){	

		try {
			// 得到授权的微信用户信息(openid,nick,...)
			User user = WxAuthService.getWeixinUser(code);
			String userJsonText = JSON.toJSONString(user);
			logger.info(userJsonText);

			// 注意，因为是H5活动，一般访问量比较大（便于负载均衡部署），所以用户信息保存到cookie中；失效时间为24小时,把用户的open_id
			this.setCookieValue(ConstCommon.OPEN_ID, user.getOpenId(), ConstCommon.COOKIE_EXPIRY);

			// 微信用户信息是否保存，由业务需求决定
			// ...this.setCookieValue(ConstCommon.SESSION_KEY, userJsonText,
			// ConstCommon.COOKIE_EXPIRY);

			
		} catch (Exception e) {
			logger.error("", e);
		}	
		//重定向到指定url(默认的重定向url都是跳转回之前访问的url，如果想要跳转到指定url，只要修改redirectUrl即可)
        return new ModelAndView("redirect:" + redirectUrl);
    	
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
	 * 用户在微信里面分享了某个页面后，分享的结果反馈回调响应函数。本函数由微信回调。
	 *  void 
	 * @author sam
	 * @since 2018年5月30日
	 */
	@RequestMapping("/onShareCallback")
	public void onShareCallback(String wxShareResultJson){
		
		String shareTypeMark = this.getParam("share_type_mark");
		logger.info(shareTypeMark);
		logger.info(wxShareResultJson);
		
	}
    
}
