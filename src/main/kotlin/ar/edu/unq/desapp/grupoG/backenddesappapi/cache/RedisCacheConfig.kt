package ar.edu.unq.desapp.grupoG.backenddesappapi.cache

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.RedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class RedisCacheConfig : CachingConfigurerSupport(), CachingConfigurer {

    @Bean
    fun redisCacheManager(lettuceConnectionFactory: LettuceConnectionFactory?): RedisCacheManager? {
        val redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(1))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java()))
        redisCacheConfiguration.usePrefix()
        val redisCacheManager =
            RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(lettuceConnectionFactory!!)
                .cacheDefaults(redisCacheConfiguration).build()
        redisCacheManager.isTransactionAware = true
        return redisCacheManager
    }
    override fun errorHandler(): CacheErrorHandler? {
        return RedisCacheErrorHandler()
    }
}