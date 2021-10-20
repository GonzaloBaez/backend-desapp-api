package ar.edu.unq.desapp.grupoG.backenddesappapi.cache

import org.springframework.boot.autoconfigure.cache.RedisCacheManagerBuilderCustomizer
import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager.RedisCacheManagerBuilder
import java.time.Duration


@Configuration
@EnableCaching
class RedisCacheConfig : CachingConfigurerSupport(), CachingConfigurer {

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? {
        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
            val configurationMap: MutableMap<String, RedisCacheConfiguration> =
                HashMap()
            configurationMap["cacheDollar"] = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))


            configurationMap["cacheCrypto"] = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
            builder.withInitialCacheConfigurations(configurationMap)
        }
    }
    override fun errorHandler(): CacheErrorHandler? {
        return RedisCacheErrorHandler()
    }
}