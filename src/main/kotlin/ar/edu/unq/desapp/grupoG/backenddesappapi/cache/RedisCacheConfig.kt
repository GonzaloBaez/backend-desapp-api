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
import java.time.LocalDateTime
import java.time.LocalTime


@Configuration
@EnableCaching
class RedisCacheConfig : CachingConfigurerSupport(), CachingConfigurer {

    @Bean
    fun redisCacheManagerBuilderCustomizer(): RedisCacheManagerBuilderCustomizer? {
        return RedisCacheManagerBuilderCustomizer { builder: RedisCacheManagerBuilder ->
            val configurationMap: MutableMap<String, RedisCacheConfiguration> =
                HashMap()
            configurationMap["cacheDollar"] = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(timeLeftToMidnight())


            configurationMap["cacheCrypto"] = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
            builder.withInitialCacheConfigurations(configurationMap)
        }
    }
    override fun errorHandler(): CacheErrorHandler? {
        return RedisCacheErrorHandler()
    }

    fun timeLeftToMidnight():Duration{
        val hoursLeft = 23 - LocalTime.now().hour
        val minutesLeft = 59 - LocalDateTime.now().minute

        return Duration.ofHours(hoursLeft.toLong()) + Duration.ofMinutes(minutesLeft.toLong())
    }
}