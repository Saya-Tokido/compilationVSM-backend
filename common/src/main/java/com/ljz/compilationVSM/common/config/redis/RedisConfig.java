package com.ljz.compilationVSM.common.config.redis;


import com.ljz.compilationVSM.common.config.redis.protobuf.serializer.LexerTestCaseProtobufRedisSerializer;
import com.ljz.compilationVSM.common.config.redis.protobuf.serializer.StringProtobufRedisSerializer;
import com.ljz.compilationVSM.common.config.redis.protobuf.serializer.LoginProtobufRedisSerializer;
import com.ljz.compilationVSM.common.dto.LexerTestCaseDTO;
import com.ljz.compilationVSM.common.dto.LoginUserDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis Bean 配置类
 *
 * @author ljz
 * @since 2024-12-25
 */
@Configuration
public class RedisConfig {

    /**
     * 登录信息 redis模板Bean
     *
     * @param connectionFactory redis连接工厂
     * @return 登录信息redis模板
     */
    @Bean(name="loginRedisTemplate")
    public RedisTemplate<String, LoginUserDTO> loginRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, LoginUserDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        LoginProtobufRedisSerializer serializer=new LoginProtobufRedisSerializer();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    /**
     * 默认 redis模板Bean
     *
     * @param connectionFactory redis连接工厂
     * @return 默认 redis模板
     */
    @Bean(name="defaultRedisTemplate")
    public RedisTemplate<Object, Object> defaultRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<>(Object.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    /**
     * 字符串 redis模板Bean
     *
     * @param connectionFactory redis连接工厂
     * @return 字符串 redis模板
     */
    @Bean(name="stringMessageRedisTemplate")
    public RedisTemplate<String, String> stringMessageRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        StringProtobufRedisSerializer serializer=new StringProtobufRedisSerializer();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }

    /**
     * 词法分析器用例 redis模板Bean
     *
     * @param connectionFactory 链接工厂
     * @return redis模板Bean
     */
    @Bean(name="LexerRedisTemplate")
    public RedisTemplate<String, LexerTestCaseDTO> lexerRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, LexerTestCaseDTO> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        LexerTestCaseProtobufRedisSerializer serializer=new LexerTestCaseProtobufRedisSerializer();

        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);

        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);

        return template;
    }
}
