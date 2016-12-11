package com.springmybatis.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmybatis.demo.bean.User;
import com.springmybatis.demo.dao.IUserDao;
import com.springmybatis.demo.service.IUserService;

@Service("userService")
public class UserServiceImpl implements IUserService {

  @Autowired
  private IUserDao userDao;//注入dao

  public User getUserById(String userId) {
    return userDao.selectByPrimaryKey(userId);
  }
  
  @Override
  public User addUser(String id, String name, String password, int age) {
    User user = new User(id, name, password, age);
    if (userDao.insert(user) > 0) {
      return user;
    }
    return null;
  }

//  @Override
//  public User getUserById(String userId) {
//    return userDao.selectByPrimaryKey(userId);
//  }

//  @Override
//  public boolean deleteUserById(String userId) {
//    return userDao.deleteByPrimaryKey(userId) > 0;
//  }
//
//  @Override
//  public boolean updateUser(User user) {
//    return userDao.updateByPrimaryKey(user) > 0;
//  }

}
