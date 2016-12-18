package com.springmybatis.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.springmybatis.demo.bean.User;

public class RedisUserDao {
  @Autowired
  private RedisTemplate<String, User> jedisTemplate;

  public User getUser(String id) {
    return jedisTemplate.opsForValue().get(id);
  }

  public void setUser(User user) {
    jedisTemplate.opsForValue().set(user.getUserId(), user);
  }

}
