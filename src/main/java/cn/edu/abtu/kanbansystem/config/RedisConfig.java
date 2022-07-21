package cn.edu.abtu.kanbansystem.config;

import cn.edu.abtu.kanbansystem.handler.FastJsonRedisSerializer;
import com.alibaba.fastjson.parser.ParserConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Richard
 * @date 2022/2/8 16:50
 */
@Configuration
public class RedisConfig {


    @Bean
    @Primary
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(valueSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    private RedisSerializer<Object> valueSerializer() {
        // 对值的对象解析器的一些具体配置
        FastJsonRedisSerializer<Object> serializer = new FastJsonRedisSerializer<>(Object.class);
        ParserConfig.getGlobalInstance().addAccept("cn.edu.abtu.kanbansystem.bean.system.Message");
        ParserConfig.getGlobalInstance().addAccept("cn.edu.abtu.kanbansystem.bean.security.AuthUser");
        ParserConfig.getGlobalInstance().addAccept("org.springframework.security.core.authority.SimpleGrantedAuthority");
        return serializer;
    }

}
