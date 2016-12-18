package com.springmybatis.demo;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.alibaba.druid.pool.DruidDataSource;
import com.springmybatis.demo.bean.User;
import com.springmybatis.demo.util.RedisObjectSerializer;

@Configuration
@MapperScan("com.springmybatis.demo")
public class DemoConfig {

  @Value("${spring.datasource.driver-class-name}")
  private String driverName;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String userName;
  @Value("${spring.datasource.password}")
  private String password;

  /**
   * get datasource
   * As define our own DataSource bean(DruidDataSource), auto-configuration will not occur.
   * @return
   */
  @Bean
  public DataSource dataSource() {
    DruidDataSource dataSource = new DruidDataSource();
    dataSource.setDriverClassName(driverName);
    dataSource.setUrl(url);
    dataSource.setUsername(userName);
    dataSource.setPassword(password);

    return dataSource;
  }

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    org.apache.ibatis.session.Configuration conf = new org.apache.ibatis.session.Configuration();
    conf.setMapUnderscoreToCamelCase(true);
    sessionFactory.setConfiguration(conf);
    return sessionFactory.getObject();
  }


  /**
   * redis config
   */

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {

    // 使用jedis cluster访问redis集群时使用
    // JedisClusterConnFactory jedisConnectionFactory =
    // new JedisClusterConnFactory(new RedisClusterConfiguration(redisNodes));
    // edisConnectionFactory.setPassword(password);
    // return jedisConnectionFactory;

    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, User> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, User> template = new RedisTemplate<String, User>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new RedisObjectSerializer());
    return template;
  }

  /**
   * construct expire Application Topic.
   * 
   * @param topic topic string
   * @return ChannelTopic
   */
  @Bean
  @Qualifier("userChannelTopic")
  public ChannelTopic getExpireAppTopic(@Value("${spring.redis.channel.user}") String topic) {
    return new ChannelTopic(topic);
  }

  /**
   * construct RedisMessageListenerContainer.
   * 
   * @param connectionFactory RedisConnectionFactory
   * @param redisMessageListener RedisMessageListener
   * @param userTopic topic
   * @return RedisMessageListenerContainer
   */
  @Bean
  @Qualifier("topicContainer")
  public RedisMessageListenerContainer getTopicContainer(
      @Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory,
      @Qualifier("userRedisMsgListener") MessageListener messageListener,
      @Qualifier("userChannelTopic") ChannelTopic userTopic) {
    RedisMessageListenerContainer topicContainer = new RedisMessageListenerContainer();
    topicContainer.setConnectionFactory(connectionFactory);

    ThreadPoolTaskScheduler threadPool = new ThreadPoolTaskScheduler();
    threadPool.setPoolSize(3);
    threadPool.initialize();
    topicContainer.setTaskExecutor(threadPool);

    Set<ChannelTopic> set = new HashSet<>();
    set.add(userTopic);
    Map<MessageListener, Collection<? extends Topic>> map = new HashMap<>();
    map.put(messageListener, set);
    topicContainer.setMessageListeners(map);

    return topicContainer;
  }
}
