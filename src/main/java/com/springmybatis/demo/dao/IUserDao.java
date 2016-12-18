package com.springmybatis.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.UpdateProvider;

import com.springmybatis.demo.bean.User;
import com.springmybatis.demo.dao.provider.UserDynaSqlProvider;

@Mapper
public interface IUserDao {
  @Insert("insert into tb_user(user_id, user_name, user_password,user_age) values "
      + "(#{userId}, #{userName}, #{userPassword}, #{userAge})")
  int insert(User user);

  @InsertProvider(type = UserDynaSqlProvider.class, method = "insertSelective")
  int insertSelective(User user);

  @Select("select * from tb_user where user_id=#{userId}")
  User selectByPrimaryKey(@Param("userId") String userId);

  @UpdateProvider(type = UserDynaSqlProvider.class, method = "updateUser")
  int updateByPrimaryKey(User user);

  @Delete("delete from tb_user where user_id=#{userId}")
  int deleteByPrimaryKey(String userId);
}
