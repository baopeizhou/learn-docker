package org.bob.learn.zk.common.util;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.springframework.data.redis.core.RedisTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * 字符串、简单数据对象及简单对象集合操作，复杂操作使用相应的RedisTemplate进行操作
 */
@SuppressWarnings("unchecked")
public class RedisUtils {

	private static RedisTemplate<String, String> stringRedisTemplate =
		SpringContextUtils.getBean("stringRedisTemplate", RedisTemplate.class);

	private static RedisTemplate<String, Object> objectRedisTemplate =
		SpringContextUtils.getBean("objectRedisTemplate", RedisTemplate.class);

	public static RedisTemplate<String, String> stringRedisTemplate() {
		return stringRedisTemplate;
	}

	public static RedisTemplate<String, Object> objectRedisTemplate() {
		return objectRedisTemplate;
	}

	public static boolean exists(String key) {
		return objectRedisTemplate.hasKey(key);
	}

	public static void putString(String key, String value) {
		stringRedisTemplate.opsForValue().set(key, value);
	}

	public static void putString(String key, String value, long offset) {
		stringRedisTemplate.opsForValue().set(key, value, offset);
	}

	public static void putString(String key, String value, long timeout, TimeUnit unit) {
		stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public static String getString(String key) {
		return stringRedisTemplate.opsForValue().get(key);
	}

	public static <T> T getObject(String key, Class<T> clazz) {
		return (T) objectRedisTemplate.opsForValue().get(key);
	}

	public static <T> void putObject(String key, T value) {
		objectRedisTemplate.opsForValue().set(key, value);
	}

	public static <T> void putObject(String key, T value, long offset) {
		objectRedisTemplate.opsForValue().set(key, value, offset);
	}

	public static <T> void putObject(String key, T value, long timeout, TimeUnit unit) {
		objectRedisTemplate.opsForValue().set(key, value, timeout, unit);
	}

	public static <T> List<T> getListObject(String key, Class<T> clazz) {
		 return ((List<Object>)objectRedisTemplate.opsForValue().get(key)).stream().map(o -> {
				return JacksonUtils.fromJson(JacksonUtils.toJson(o), clazz);
			}).collect(Collectors.toList());
		
	}

	public static <T> void addSetObject(String key, T value) {
		objectRedisTemplate.opsForSet().add(key, value);
	}

	public static <T> Set<T> getSet(String key, Class<T> clazz) {
		
		return objectRedisTemplate.opsForSet().members(key).stream().map(
			o -> {
				return JacksonUtils.fromJson(JacksonUtils.toJson(o), clazz);
			}
		).collect(Collectors.toSet());

	}

	public static <T> Set<T> getZSet(String key, Class<T> clazz, long start, long end) {
		return (Set<T>) objectRedisTemplate.opsForZSet().range(key, start, end);
	}

	public static <T> void addZSetObject(String key, T value, double score) {
		objectRedisTemplate.opsForZSet().add(key, value, score);
	}

}
