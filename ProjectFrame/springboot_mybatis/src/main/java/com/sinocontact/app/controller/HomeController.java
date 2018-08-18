package com.sinocontact.app.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sinocontact.app.common.ConstCommon;
import com.sinocontact.app.dao.mapper.PersonDao;
import com.sinocontact.app.dao.mapper.PersonMapper;
import com.sinocontact.app.entity.Person;
import com.sinocontact.app.entity.PersonNormal;
import com.sinocontact.app.service.user.PersonService;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {
	private static final Logger logger = Logger.getLogger(HomeController.class);
	
	@Autowired
	private PersonMapper personMapper ;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private PersonService personService;
	
	
	@RequestMapping("/")
	/**
	 * 默认首页
	 * @return String 
	 * @author sam
	 * @since 2017年10月18日
	 */
	String home(){
		
		personService.add("80808", 80808f);
		/*
		Person user = new Person();
		user.setFee(90909f);
		user.setName("90909");
		//personMapper.insert(user);
		
		user.setUserId(2005);
		user.setName("update");
		//personMapper.update(user);
		
		personMapper.delete(13116l);
		*/
		
		/*
		PersonNormal p = personDao.findById(2006);
		logger.info("userid="+p.getUser_id());
		logger.info("username="+p.getName());
		logger.info("createtime="+p.getCreate_time());
		logger.info("fee="+p.getFee());
		*/
		/*
		List<Person> plist = personMapper.getAll(10);
		for(Person p:plist){
		 //= personMapper.findById(2006);
			logger.info("userid="+p.getUserId());
			logger.info("username="+p.getName());
			logger.info("createtime="+p.getCreateTime());
			logger.info("fee="+p.getFee());
		}
		*/
		//putObject("username",getSession().getAttribute(ConstCommon.SESSION_KEY));		
		return "views/index";
	}
	
	@RequestMapping("/home/console")
	String console(){
		
		return "views/home/console";
	}
}
