package com.springmybatis.demo.util;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class JedisClusterConnFactory extends JedisConnectionFactory {

  /**
   * Constructs a new {@link JedisConnectionFactory} instance using the given
   * {@link RedisClusterConfiguration} applied to create a {@link JedisCluster}.
   *
   * @param clusterConfig RedisClusterConfiguration
   * @since 1.7
   */
  public JedisClusterConnFactory(RedisClusterConfiguration clusterConfig) {
    super(clusterConfig);
  }

  /**
   * Creates {@link JedisCluster} for given {@link RedisClusterConfiguration} and
   * {@link GenericObjectPoolConfig}.
   *
   * @param clusterConfig must not be {@literal null}.
   * @param poolConfig can be {@literal null}.
   * @return JedisCluster
   * @since 1.7
   */
  @Override
  protected JedisCluster createCluster(RedisClusterConfiguration clusterConfig,
      GenericObjectPoolConfig poolConfig) {

    Assert.notNull("Cluster configuration must not be null!");

    Set<HostAndPort> hostAndPort = new HashSet<HostAndPort>();
    for (RedisNode node : clusterConfig.getClusterNodes()) {
      hostAndPort.add(new HostAndPort(node.getHost(), node.getPort()));
    }

    int redirects =
        clusterConfig.getMaxRedirects() != null ? clusterConfig.getMaxRedirects().intValue() : 5;

    // 当使用jedis cluster访问redis集群，而又需要设置密码时使用，需要jedis2.9
    // return StringUtils.hasText(getPassword())
    // ? new JedisCluster(hostAndPort, getTimeout(),getTimeout(), redirects, getPassword(),
    // poolConfig)
    // : new JedisCluster(hostAndPort, getTimeout(), redirects, poolConfig);
    return null;
  }
}
