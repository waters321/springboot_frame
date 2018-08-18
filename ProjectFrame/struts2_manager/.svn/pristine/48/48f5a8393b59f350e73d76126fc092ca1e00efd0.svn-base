package com.sinocontact.core.service.user;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;


import com.sinocontact.core.BaseService;
import com.sinocontact.core.dao.user.UserDao;

/**
 * 用户业务类，处理用户的所有业务操作
 * @author sam
 * @since 2018年5月22日
 */
public class UserService extends BaseService {

private static final Logger logger = Logger.getLogger(UserService.class);
	
	private UserDao userDao = new UserDao();
	
	/**
	 * 检测用户是否有效
	 * @param username
	 * @param password
	 * @return boolean 
	 * @author sam
	 * @since 2018年5月14日
	 */
	public boolean checkUser(String username,String password){
		if(StringUtils.isEmpty(username)==true){
			return false;
		}
		if(StringUtils.isEmpty(password)==true){
			return false;
		}
		
		return userDao.isValidUser(username, password);
	}
}
