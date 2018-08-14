package com.sinocontact.core.dao;

import org.apache.log4j.Logger;

import com.sinocontact.core.BaseDao;
import com.sinocontact.entity.AccessLog;

/**
 * 访问日志操作的dao
 * @author sam
 * @since 2018年5月17日
 */
public class AccessLogDao extends BaseDao {
	private static final Logger logger = Logger.getLogger(AccessLogDao.class);
	
	/**
	 * 添加一条访问日志记录到数据库中
	 * @param accessLog void 
	 * @author sam
	 * @since 2018年5月17日
	 */
	public  void addAccessLog(AccessLog accessLog){
		try{
			/*
			String sql = "insert into access_log(ip_address,user_agent,media_no,openid,share_openid,access_url,create_time) values(?,?,?,?,?,?,now())";
			this.getMainDbOperator().insert(sql, accessLog.getIp_address(),accessLog.getUser_agent(),accessLog.getMedia_no(),accessLog.getOpenid(),accessLog.getShare_openid(),accessLog.getAccess_url());
			*/
		}catch(Exception e){
			logger.error("",e);
		}
	}
}
