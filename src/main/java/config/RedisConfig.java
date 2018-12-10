package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import javax.annotation.Resource;
//
@Configuration
@ComponentScan(basePackages = {"session","util","cache"})
public class RedisConfig {

    @Bean
    public JedisPool jedisPool(){
        return new JedisPool(jedisPoolConfig(),"127.0.0.1");
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig(){
        return new JedisPoolConfig();
    }



}
