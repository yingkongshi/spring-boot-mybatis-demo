package com.springmybatis.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.springmybatis.demo.bean.User;
import com.springmybatis.demo.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

  @Autowired
  private IUserService userService;

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<User> create(@RequestParam String id, @RequestParam String name,
      @RequestParam String password, @RequestParam(required = false, defaultValue = "0") int age) {
    User user = userService.addUser(id, name, password, age);
    return new ResponseEntity<>(user, HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT)
  public ResponseEntity<User> update(@RequestParam String id, @RequestParam String name,
      @RequestParam String password, @RequestParam(required = false, defaultValue = "0") int age) {
    if (userService.updateUser(id, name, password, age)) {
      return new ResponseEntity<>(null, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(method = RequestMethod.DELETE)
  public ResponseEntity<User> delete(@RequestParam String id) {
    if (userService.deleteUserById(id)) {
      return new ResponseEntity<>(null, HttpStatus.OK);
    }

    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
