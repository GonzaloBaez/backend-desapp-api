package ar.edu.unq.desapp.grupoG.backenddesappapi.cache

import org.springframework.cache.annotation.CachingConfigurer
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class RedisCacheConfig : CachingConfigurerSupport(), CachingConfigurer {
    override fun errorHandler(): CacheErrorHandler? {
        return RedisCacheErrorHandler()
    }
}