package com.springmybatis.demo.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.springmybatis.demo.bean.User;
import com.springmybatis.demo.dao.IUserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class UserServiceImplTest {
  
  @Autowired
  private IUserDao userDao;

  @Test
  public void testSelect() {
    userDao.selectByPrimaryKey("1");
  }

  @Test
  public void testInsert() {
	User user = new User("2", "wang", "abc", 18);
    userDao.insert(user);
    assertNotNull(userDao.selectByPrimaryKey("2"));
  }
  
  @Test
  public void testUpdate() {
    User user = userDao.selectByPrimaryKey("1");
    user.setUserAge(15);
    userDao.updateByPrimaryKey(user);
    assertEquals(15, userDao.selectByPrimaryKey("1").getUserAge());
  }
  
  @Test
  public void testDelete() {
    userDao.deleteByPrimaryKey("1");
    assertNull(userDao.selectByPrimaryKey("1"));
  }
}
