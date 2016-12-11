package com.springmybatis.demo.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springmybatis.demo.dao.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserServiceImplTest {
  
  @Autowired
  private IUserDao userDao;

  @Test
  public void test() {
    userDao.selectByPrimaryKey("123456");
  }

}
