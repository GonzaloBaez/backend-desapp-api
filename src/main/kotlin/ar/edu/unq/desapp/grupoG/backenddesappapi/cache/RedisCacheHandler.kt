package ar.edu.unq.desapp.grupoG.backenddesappapi.cache

import io.lettuce.core.RedisCommandTimeoutException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.cache.Cache
import org.springframework.cache.interceptor.CacheErrorHandler
import org.springframework.data.redis.RedisConnectionFailureException
import java.net.ConnectException
import kotlin.RuntimeException


class RedisCacheErrorHandler : CacheErrorHandler {

    var log : Logger = LoggerFactory.getLogger(RedisCacheErrorHandler::class.java)

    override fun handleCacheGetError(exception: RuntimeException, cache: Cache, key: Any) {
        handleTimeOutException(exception);
        log.info("Unable to get from cache " + cache.name + " : " + exception.message);
    }

    override fun handleCachePutError(exception: RuntimeException, cache: Cache, key: Any, value: Any?) {
        handleTimeOutException(exception);
        log.info("Unable to put into cache " + cache.name + " : " + exception.message);
    }

    override fun handleCacheEvictError(exception: RuntimeException, cache: Cache, key: Any) {
        handleTimeOutException(exception);
        log.info("Unable to evict from cache " + cache.name + " : " + exception.message);
    }

    override fun handleCacheClearError(exception: RuntimeException, cache: Cache) {
        handleTimeOutException(exception);
        log.info("Unable to clean cache " + cache.name + " : " + exception.message);
    }

    private fun handleTimeOutException(error : RuntimeException){
        if(error is RedisCommandTimeoutException || error is RedisConnectionFailureException){
            return
        }
    }


}