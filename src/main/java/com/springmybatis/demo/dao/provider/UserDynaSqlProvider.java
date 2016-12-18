package com.springmybatis.demo.dao.provider;

import org.apache.ibatis.jdbc.SQL;

import com.springmybatis.demo.bean.User;

public class UserDynaSqlProvider {

  public String insertSelective(User user) {
    return new SQL() {
      {
        INSERT_INTO("tb_user");
        if (null != user.getUserId()) {
          VALUES("user_id", "#{userId}");
        }
        if (null != user.getUserName()) {
          VALUES("user_name", "#{userName}");
        }
        if (null != user.getUserPassword()) {
          VALUES("user_password", "#{userPassword}");
        }
        VALUES("user_age", "#{userAge}");
      }
    }.toString();
  }

  public String updateUser(final User user) {
    return new SQL() {
      {
        UPDATE("tb_user");
        if (null != user.getUserName()) {
          SET("user_name=#{userName}");
        }
        if (null != user.getUserPassword()) {
          SET("user_password=#{userPassword}");
        }
        SET("user_age=#{userAge}");
        WHERE("user_id=#{userId}");
      }
    }.toString();
  }
}
