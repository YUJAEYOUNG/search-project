package global.search.pretask.app.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
@EnableRedisRepositories(basePackages = ["global.search.pretask.persistence.redis"])
class RedisConfig(
    @Value("\${spring.cache.redis.host}")
    private var host: String,

    @Value("\${spring.cache.redis.port}")
    private var port: String
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        return LettuceConnectionFactory(host, port.toInt())
    }

    @Bean
    fun redisTemplate(): RedisTemplate<*, *>? {
        val redisTemplate = RedisTemplate<ByteArray, ByteArray>()
        redisTemplate.connectionFactory = redisConnectionFactory()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = StringRedisSerializer()
        return redisTemplate
    }

    @Bean("searchKeywordRedisTemplate")
    fun stringRedisTemplate(): StringRedisTemplate? {
        val stringRedisTemplate = StringRedisTemplate()
        stringRedisTemplate.keySerializer = StringRedisSerializer()
        stringRedisTemplate.valueSerializer = StringRedisSerializer()
        stringRedisTemplate.connectionFactory = redisConnectionFactory()
        return stringRedisTemplate
    }
}
