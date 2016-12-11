package com.springmybatis.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.springmybatis.demo.bean.User;

@Mapper
public interface IUserDao {
//  int deleteByPrimaryKey(String userId);
//
  @Insert("insert into tb_user(user_id, user_name, user_password,user_age) values (#{id}, #{name}, #{password}, #{age})")
  int insert(User user);
//
//  int insertSelective(User user);

  @Select("select * from tb_user where user_id = #{userId}")
  User selectByPrimaryKey(@Param("userId") String userId);

//  int updateByPrimaryKeySelective(User user);
//
//  int updateByPrimaryKey(User user);
}
