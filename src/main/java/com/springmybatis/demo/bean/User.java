package com.springmybatis.demo.bean;

import java.io.Serializable;

public class User implements Serializable {

  private static final long serialVersionUID = -2260898357725772714L;

  private String userId;
  private String userName;
  private String userPassword;
  private int userAge;


  public User() {}

  public User(String userId, String userName, String userPassword, int userAge) {
    super();
    this.userId = userId;
    this.userName = userName;
    this.userPassword = userPassword;
    this.userAge = userAge;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserPassword() {
    return userPassword;
  }

  public void setUserPassword(String userPassword) {
    this.userPassword = userPassword;
  }

  public int getUserAge() {
    return userAge;
  }

  public void setUserAge(int userAge) {
    this.userAge = userAge;
  }
}
