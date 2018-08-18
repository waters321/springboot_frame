package com.sinocontact.app.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sinocontact.app.dao.mapper.PersonMapper;
import com.sinocontact.app.entity.Person;

@Service
public class PersonService {

	@Autowired
	private PersonMapper personMapper ;
	
	@Transactional
	public void add(String name,Float fee){
		Person user = new Person();
		user.setName(name);
		user.setFee(fee);
		personMapper.insert(user);
		
		user.setUserId(2005);
		personMapper.update(user);
	}
}
