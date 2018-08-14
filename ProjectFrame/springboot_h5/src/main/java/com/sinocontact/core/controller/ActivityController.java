package com.sinocontact.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinocontact.core.BaseController;



/**
 * @author jack
 * @since 2018/2/9
 */
@Controller
@RequestMapping("activity")
public class ActivityController extends BaseController{


    @RequestMapping("/index")
    public String index(){
    	
    	this.addWxShareCallbackParam("share_type_mark", "sam");
    	this.setWxShareUrl("http://curry.local.tb21.cn/springboot_h5/activity/demo");
    	this.setWxShareTitle("随时来了");
    	this.setWxShareDesc("太好玩了");
    	this.putObject("user", "hello,it works");
        return "index";
    }
    
    @RequestMapping("/demo")
    public String demo(){
    	
    	this.putObject("demo", "we are demo");
    	return "demo";
    }
}
