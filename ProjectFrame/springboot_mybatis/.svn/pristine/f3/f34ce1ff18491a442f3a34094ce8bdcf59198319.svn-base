package com.sinocontact.app.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sinocontact.app.entity.Person;
import com.sinocontact.app.entity.PersonNormal;

/**
 * 等同于dao
 * @author sam
 * @since 2018年8月15日
 */
@Mapper
public interface PersonDao {

	@Select("SELECT * FROM mk limit #{maxcount}")
    @Results({
        @Result(property = "userId",  column = "user_id"),
        @Result(property = "name", column = "name"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "fee", column = "fee")
    })
    List<Person> getAll(Integer maxcount);
	
	
	@Select("SELECT * FROM mk WHERE user_id = #{id}")
	PersonNormal findById(@Param("id") Integer id);
	
	
	@Insert("INSERT INTO mk(name,fee,create_time) VALUES(#{name}, #{fee},now())")
    void insert(Person user);

    @Update("UPDATE mk SET name=#{name},fee=#{fee} WHERE user_id =#{userId}")
    void update(Person user);

    @Delete("DELETE FROM mk WHERE user_id =#{id}")
    void delete(Long id);
}

