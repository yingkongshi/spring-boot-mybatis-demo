package com.springmybatis.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class RedisPubDao {
  @Autowired
  private RedisTemplate<String, String> jedisTemplate;
  @Autowired
  @Qualifier("userChannelTopic")
  private ChannelTopic topic;

  public void publish(Object message) {
    jedisTemplate.convertAndSend(topic.getTopic(), message);
  }

  public RedisTemplate<String, String> getJedisTemplate() {
    return jedisTemplate;
  }

  public void setJedisTemplate(RedisTemplate<String, String> jedisTemplate) {
    this.jedisTemplate = jedisTemplate;
  }

  public ChannelTopic getTopic() {
    return topic;
  }

  public void setTopic(ChannelTopic topic) {
    this.topic = topic;
  }

}
