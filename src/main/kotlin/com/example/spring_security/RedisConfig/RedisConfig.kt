package com.example.spring_security.RedisConfig


import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration.JedisPoolingClientConfigurationBuilder
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.GenericToStringSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Primary
@Configuration
@EnableRedisRepositories
class RedisConfig {

    //redis connection config
    @Value("\${spring.redis.host}") val HOST: String? = "localhost"
    @Value("\${spring.redis.port}") val PORT: Int? = 6379
    @Value("\${spring.redis.database}") val DATABASE: Int? = 4
    @Value("\${spring.redis.password}") val PASSWORD: String? = null

    //pool config
    @Value("\${spring.redis.jedis.pool.max-active}") val maxTotal: Int? = 16
    @Value("\${spring.redis.jedis.pool.max-idle}") val maxIdle: Int? = 8
    @Value("\${spring.redis.jedis.pool.min-idle}") val minIdle: Int? = 4


    @Bean
    fun redisPoolConfig(): JedisClientConfiguration {
        val JedisPoolingClientConfigurationBuilder = JedisClientConfiguration
                .builder() as JedisPoolingClientConfigurationBuilder
        val GenericObjectPoolConfig: GenericObjectPoolConfig<*> = GenericObjectPoolConfig<Any?>()
        GenericObjectPoolConfig.maxTotal = maxTotal!!
        GenericObjectPoolConfig.maxIdle = maxIdle!!
        GenericObjectPoolConfig.minIdle = minIdle!!
        return JedisPoolingClientConfigurationBuilder.poolConfig(GenericObjectPoolConfig).build()
    }


    @Bean
    fun connectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = HOST!!
        config.port = PORT!!
        config.database = DATABASE!!
        config.password = RedisPassword.of(PASSWORD)
        return JedisConnectionFactory(config)
    }


/*    @Bean
    fun redisTemplate(): RedisTemplate<String, Student> {
        val template = RedisTemplate<String, Student>()
        template.setConnectionFactory(connectionFactory())
        template.setEnableTransactionSupport(true)
        template.afterPropertiesSet()

        template.keySerializer = GenericToStringSerializer(Long::class.java)
        template.keySerializer = StringRedisSerializer()
        template.hashKeySerializer = GenericJackson2JsonRedisSerializer()
        template.valueSerializer = GenericJackson2JsonRedisSerializer()
        template.setDefaultSerializer(GenericJackson2JsonRedisSerializer())
        return template

*//*        val redisTemplate = RedisTemplate<String, Student>()
        redisTemplate.setConnectionFactory(connectionFactory())
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.hashKeySerializer = StringRedisSerializer()
        redisTemplate.hashValueSerializer = StringRedisSerializer()
        return redisTemplate*//*

    }*/

    @Bean
    fun redisTemplates(): RedisTemplate<String, Any> {
        val redisTemplate = RedisTemplate<String,Any>()
        redisTemplate.keySerializer = StringRedisSerializer()
        redisTemplate.hashKeySerializer = GenericToStringSerializer(String::class.java)
        redisTemplate.hashValueSerializer = JdkSerializationRedisSerializer()
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer()


        val jackson2JsonRedisSerializer: Jackson2JsonRedisSerializer<*> = Jackson2JsonRedisSerializer(Any::class.java)
        val om = ObjectMapper()
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL)
        jackson2JsonRedisSerializer.setObjectMapper(om)
        //redisTemplate.valueSerializer = jackson2JsonRedisSerializer

        connectionFactory().afterPropertiesSet()
        redisTemplate.setConnectionFactory(connectionFactory())

        return redisTemplate
    }
}