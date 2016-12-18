package com.springmybatis.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.springmybatis.demo.bean.User;

@Component("userRedisMsgListener")
public class UserRedisMsgListener implements MessageListener {
  @Autowired
  private RedisTemplate<String, User> jedisTemplate;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    byte[] channel = message.getChannel();
    byte[] body = message.getBody();
    String topic = (String) jedisTemplate.getStringSerializer().deserialize(channel);
    String itemValue = (String) jedisTemplate.getValueSerializer().deserialize(body);
    System.out
        .println("get message from redis, channel [" + topic + "], message [" + itemValue + "]");
  }

}
