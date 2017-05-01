package org.bob.learn.zk.config;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;

import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.spi.CachingProvider;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;

@Order(1)
@EnableCaching
@Configuration
public class CacheConfig {
	
	
	@Bean  
    public JCacheCacheManager jcacheCacheManager(){  
     
        CachingProvider provider = Caching.getCachingProvider();  
        javax.cache.CacheManager cacheManager = provider.getCacheManager();  
          
        MutableConfiguration<String, Object> mutableConfiguration = new MutableConfiguration<String, Object>();
    	mutableConfiguration.setStoreByValue(false);  // otherwise value has to be Serializable
    	cacheManager.createCache("zserver", mutableConfiguration);
    	

    	JCacheCacheManager jCacheCacheManager = new JCacheCacheManager(cacheManager);
    	return jCacheCacheManager;   
    }  
	
	@Bean
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object target, Method method, Object... params) {
				StringBuilder sb = new StringBuilder();
				sb.append(target.getClass().getName());
				sb.append(method.getName());
				for (Object obj : params) {
					sb.append(obj.toString());
				}
				return sb.toString();
			}
		};
	}

	/*@Bean
	public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory) {
		StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(factory);
		stringRedisTemplate.setEnableTransactionSupport(true);
		stringRedisTemplate.afterPropertiesSet();
		return stringRedisTemplate;
	}

	@Bean
	public RedisTemplate<String, Object> objectRedisTemplate(RedisConnectionFactory factory,
			ObjectMapper objectMapper) {
		RedisTemplate<String, Object> objectRedisTemplate = new RedisTemplate<>();
		objectRedisTemplate.setConnectionFactory(factory);
		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(
				Object.class);
		jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
		objectRedisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
		objectRedisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		objectRedisTemplate.setEnableTransactionSupport(true);
		objectRedisTemplate.afterPropertiesSet();
		return objectRedisTemplate;
	}

	@Bean
	public RedisCacheManager redisCacheManager(RedisTemplate<String, Object> objectRedisTemplate) {
		return new RedisCacheManager(objectRedisTemplate);
	}*/

	@Bean  
	@Primary  
	public CacheManager cacheManager(JCacheCacheManager jcacheCacheManager) {  
	    MixCacheManager cacheManager = new MixCacheManager();  
	    cacheManager.setRedisCacheManager(null);  
	    cacheManager.setMemCacheManager(jcacheCacheManager);  
	    return cacheManager;  
	}  
	
	
	public class MixCacheManager implements CacheManager {

		 private CacheManager redisCacheManager;  
		    private CacheManager memCacheManager;  
		      
		  
		    private String redisPrefix = "redis-";   
		   
		   public Cache getCache(String arg0) {  
		        if (arg0.startsWith(redisPrefix))  
		            return redisCacheManager.getCache(arg0);  
		        else  
		           return memCacheManager.getCache(arg0);  
		   }  
		  
		    public Collection<String> getCacheNames() {  
		        Collection<String> cacheNames = new ArrayList<String>();          
		        if (redisCacheManager != null) {  
		            cacheNames.addAll(redisCacheManager.getCacheNames());  
		        }  
		        if (memCacheManager != null) {  
		            cacheNames.addAll(memCacheManager.getCacheNames());  
		        }         
		        return cacheNames;  
		    }

			public CacheManager getRedisCacheManager() {
				return redisCacheManager;
			}

			public void setRedisCacheManager(CacheManager redisCacheManager) {
				this.redisCacheManager = redisCacheManager;
			}

			public CacheManager getMemCacheManager() {
				return memCacheManager;
			}

			public void setMemCacheManager(CacheManager memCacheManager) {
				this.memCacheManager = memCacheManager;
			}  
		  

	}

}
