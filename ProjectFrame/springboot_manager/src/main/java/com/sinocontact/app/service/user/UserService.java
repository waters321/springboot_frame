package com.sinocontact.app.service.user;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sinocontact.app.dao.LoginDao;
import com.sinocontact.app.service.BaseService;

/**
 * 登录处理业务
 * @author sam
 * @since 2018年5月14日
 */
@Service
public class UserService extends BaseService {
	private static final Logger logger = Logger.getLogger(UserService.class);
	
	private LoginDao loginDao = new LoginDao();
	
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
		
		return loginDao.isValidUser(username, password);
	}
}
